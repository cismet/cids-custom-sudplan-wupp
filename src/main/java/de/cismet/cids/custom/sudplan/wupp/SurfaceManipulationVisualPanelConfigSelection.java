/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.sudplan.wupp;

import Sirius.navigator.connection.SessionManager;
import Sirius.navigator.exception.ConnectionException;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.openide.util.WeakListeners;

import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.JSeparator;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.cismet.cids.custom.sudplan.SMSUtils;
import de.cismet.cids.custom.sudplan.wupp.tostringconverter.DeltaConfigurationToStringConverter;
import de.cismet.cids.custom.sudplan.wupp.tostringconverter.GeocpmConfigurationToStringConverter;
import de.cismet.cids.custom.sudplan.wupp.tostringconverter.InvestigationAreaToStringConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   jlauter
 * @version  $Revision$, $Date$
 */
public class SurfaceManipulationVisualPanelConfigSelection extends javax.swing.JPanel {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(SurfaceManipulationVisualPanelConfigSelection.class);

    private static final String LIST_SEPERATOR = "__prop_list_seperator__"; // NOI18N

    //~ Instance fields --------------------------------------------------------

    private final transient SurfaceManipulationWizardPanelConfigSelection model;
    private final transient ListSelectionListener selL;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList lstConfigurations;
    private javax.swing.JPanel pnlConfigurations;
    private javax.swing.JScrollPane spConfigurations;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form SurfaceManipulationVisualPanelConfigSelection.
     *
     * @param   model  DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public SurfaceManipulationVisualPanelConfigSelection(final SurfaceManipulationWizardPanelConfigSelection model) {
        this.model = model;
        if (this.model == null) {
            throw new IllegalStateException("model instance must not be null"); // NOI18N
        }
        selL = new ListSelectionListenerImpl();

        this.setName(org.openide.util.NbBundle.getMessage(
                SurfaceManipulationVisualPanelConfigSelection.class,
                "SurfaceManipulationVisualPanelConfigSelection.SurfaceManipulationVisualPanelConfigSelection(SurfaceManipulationWizardPanelConfigSelection).name")); // NOI18N
        initComponents();

        lstConfigurations.setCellRenderer(new ListCellRendererImpl());
        lstConfigurations.addListSelectionListener(WeakListeners.create(
                ListSelectionListener.class,
                selL,
                lstConfigurations));
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    public void init() {
        final CidsBean initialConfig = model.getInitialConfig();
        final CidsBean deltaSurfaceToAdd = model.getDeltaSurfaceToAdd();
        final MetaObject[] overlappingSurfaces = model.getOverlappingSurfaces();

        final DefaultListModel lstModel = new DefaultListModel();

        final DefaultListModel loadModel = new DefaultListModel();
        loadModel.addElement(org.openide.util.NbBundle.getMessage(
                SurfaceManipulationVisualPanelConfigSelection.class,
                "SurfaceManipulationVisualPanelConfigSelection.init().loadModel.element")); // NOI18N
        lstConfigurations.setModel(loadModel);

        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    lstModel.addElement(initialConfig);

                    final MetaClass MC = ClassCacheMultiple.getMetaClass(
                            SMSUtils.DOMAIN_SUDPLAN_WUPP,
                            SMSUtils.TABLENAME_DELTA_CONFIGURATION);

                    if (MC == null) {
                        LOG.error(
                            "cannot get MetaClass from Domain '" // NOI18N
                                    + SMSUtils.DOMAIN_SUDPLAN_WUPP
                                    + "' with Table '"           // NOI18N
                                    + SMSUtils.TABLENAME_DELTA_CONFIGURATION
                                    + "'");                      // NOI18N
                    }

                    boolean firstElement = true;
                    boolean isSelectionValid = false;
                    final CidsBean selectedModel = model.getConfigModel();

                    String excludedConfigs = "";
                    for (final MetaObject mo : overlappingSurfaces) {
                        if (mo != null) {
                            final Integer deltaId = (Integer)mo.getBean().getProperty("delta_configuration.id"); // NOI18N
                            excludedConfigs += String.valueOf(deltaId) + " ";                                    // NOI18N
                        }
                    }
                    excludedConfigs = excludedConfigs.trim().replace(' ', ',');

                    String query;
                    if (deltaSurfaceToAdd == null) {
                        query = "select " + MC.getID() + ", dc." + MC.getPrimaryKey() + " from ";           // NOI18N
                        query += MC.getTableName() + " dc";                                                 // NOI18N
                        query += " WHERE dc.original_object = " + initialConfig.getProperty("id");          // NOI18N
                    } else {
                        query = "select " + MC.getID() + ", dc." + MC.getPrimaryKey() + " from ";           // NOI18N
                        query += MC.getTableName() + " dc, " + SMSUtils.TABLENAME_DELTA_SURFACE + " ds";    // NOI18N
                        query += " WHERE ds.id = " + (Integer)deltaSurfaceToAdd.getProperty("id") + " AND " // NOI18N
                                    + "dc.id != ds.delta_configuration AND "                                // NOI18N
                                    + "dc.original_object = " + initialConfig.getProperty("id");            // NOI18N
                    }
                    query += " AND dc.locked = false";                                                      // NOI18N

                    if (!excludedConfigs.isEmpty()) {
                        query += " AND dc.id not in (" + excludedConfigs + ")"; // NOI18N
                    }

                    MetaObject[] deltaConfigs;
                    try {
                        deltaConfigs = SessionManager.getProxy()
                                    .getMetaObjectByQuery(
                                            SessionManager.getSession().getUser(),
                                            query,
                                            SMSUtils.DOMAIN_SUDPLAN_WUPP);
                    } catch (ConnectionException ex) {
                        LOG.error("cannot connect to " + SMSUtils.DOMAIN_SUDPLAN_WUPP, ex); // NOI18N
                        deltaConfigs = null;
                    }
                    if ((selectedModel != null) && selectedModel.equals(initialConfig)) {
                        isSelectionValid = true;
                    }
                    for (final MetaObject mo : deltaConfigs) {
                        if (firstElement) {
                            lstModel.addElement(LIST_SEPERATOR);
                            firstElement = false;
                        }

                        final CidsBean bean = mo.getBean();
                        lstModel.addElement(bean);

                        if (!isSelectionValid && (selectedModel != null) && selectedModel.equals(bean)) {
                            isSelectionValid = true;
                        }
                    }

                    lstConfigurations.setModel(lstModel);

                    if ((selectedModel != null) && isSelectionValid) {
                        try {
                            lstConfigurations.setSelectedValue(selectedModel, true);
                        } catch (Exception e) {
                            LOG.error("can't select the model in jlist", e); // NOI18N
                        }
                    } else {
                        lstConfigurations.clearSelection();
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("selectedModel is null or deleted/changed in database"); // NOI18N
                        }
                    }
                }
            });
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        pnlConfigurations = new javax.swing.JPanel();
        spConfigurations = new javax.swing.JScrollPane();
        lstConfigurations = new javax.swing.JList();

        setLayout(new java.awt.GridBagLayout());

        pnlConfigurations.setBorder(javax.swing.BorderFactory.createTitledBorder(
                org.openide.util.NbBundle.getMessage(
                    SurfaceManipulationVisualPanelConfigSelection.class,
                    "SurfaceManipulationVisualPanelConfigSelection.pnlConfigurations.border.title"))); // NOI18N
        pnlConfigurations.setLayout(new java.awt.GridBagLayout());

        lstConfigurations.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        spConfigurations.setViewportView(lstConfigurations);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlConfigurations.add(spConfigurations, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(pnlConfigurations, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class ListCellRendererImpl extends DefaultListCellRenderer {

        //~ Instance fields ----------------------------------------------------

        private final transient GeocpmConfigurationToStringConverter geoCPMToString;
        private final transient DeltaConfigurationToStringConverter deltaToString;
        private final transient InvestigationAreaToStringConverter investToString;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new ListModelImpl object.
         */
        public ListCellRendererImpl() {
            geoCPMToString = new GeocpmConfigurationToStringConverter();
            deltaToString = new DeltaConfigurationToStringConverter();
            investToString = new InvestigationAreaToStringConverter();
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value == null) {
                return this;
            }

            if (value instanceof String) {
                final String s = (String)value;
                if (s.equals(LIST_SEPERATOR)) {
                    return new JSeparator(JSeparator.HORIZONTAL);
                }
                return this;
            }

            final StringBuilder sb = new StringBuilder();

            if (value instanceof CidsBean) {
                final CidsBean cidsBean = (CidsBean)value;
                if (cidsBean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase(
                                SMSUtils.TABLENAME_GEOCPM_CONFIGURATION)) {
                    sb.append(geoCPMToString.convert(cidsBean.getMetaObject()));
                    sb.append(" - ");
                    final CidsBean invest = (CidsBean)cidsBean.getProperty("investigation_area"); // NOI18N
                    sb.append(investToString.convert(invest.getMetaObject()));
                } else if (cidsBean.getMetaObject().getMetaClass().getTableName().equalsIgnoreCase(
                                SMSUtils.TABLENAME_DELTA_CONFIGURATION)) {
                    sb.append(deltaToString.convert(cidsBean.getMetaObject()));
                }
                setIcon(new ImageIcon(cidsBean.getMetaObject().getMetaClass().getIconData()));
            }
            setText(sb.toString());
            return this;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private class ListSelectionListenerImpl implements ListSelectionListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void valueChanged(final ListSelectionEvent lse) {
            if (lse.getValueIsAdjusting()) {
                return;
            }
            final Object o = lstConfigurations.getSelectedValue();

            if (o instanceof String) {
                final String s = (String)o;
                if (o.equals(LIST_SEPERATOR)) {
                    model.setConfigModel(null, true);
                }
            } else if (o instanceof CidsBean) {
                final CidsBean cidsBean = (CidsBean)o;
                if ((model.getConfigModel() != null) && cidsBean.equals(model.getConfigModel())) {
                    model.setConfigModel(cidsBean, false);
                } else {
                    model.setConfigModel(cidsBean, true);
                }
            }
        }
    }
}
