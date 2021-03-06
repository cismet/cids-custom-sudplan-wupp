/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.sudplan.wupp;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import org.jdesktop.swingx.decorator.SortOrder;

import org.openide.util.ImageUtilities;
import org.openide.util.WeakListeners;

import java.awt.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import de.cismet.cids.custom.sudplan.SMSUtils;
import de.cismet.cids.custom.sudplan.commons.SudplanConcurrency;
import de.cismet.cids.custom.sudplan.wupp.tostringconverter.DeltaConfigurationToStringConverter;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

/**
 * DOCUMENT ME!
 *
 * @author   jlauter
 * @version  $Revision$, $Date$
 */
public class DeltaConfigurationListWidged extends javax.swing.JPanel implements DeltaConfigurationList {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(DeltaConfigurationListWidged.class);

    private static volatile DeltaConfigurationListWidged instance = null;

    //~ Instance fields --------------------------------------------------------

// private static final org.jdesktop.swingx.JXList LIST_DELTA_CFGS;
    private final ListSelectionListener selL;

    private final List<ListSelectionListener> listeners = new ArrayList<ListSelectionListener>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXList lstDeltaCfgs;
    private javax.swing.JScrollPane spList;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form DeltaConfigurationListWidged.
     */
    private DeltaConfigurationListWidged() {
        selL = new SelectionListener();

        initComponents();

        lstDeltaCfgs.setModel(new DefaultListModel());
        lstDeltaCfgs.setSortOrder(SortOrder.ASCENDING);
        lstDeltaCfgs.setComparator(new ListComparator());
        lstDeltaCfgs.setCellRenderer(new DeltaCfgCellRenderer());
        lstDeltaCfgs.addListSelectionListener(WeakListeners.create(
                ListSelectionListener.class,
                selL,
                lstDeltaCfgs));

        init();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public static DeltaConfigurationListWidged getInstance() {
        if (instance == null) {
            synchronized (DeltaConfigurationListWidged.class) {
                if (instance == null) {
                    instance = new DeltaConfigurationListWidged();
                }
            }
        }
        return instance;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        final java.awt.GridBagConstraints gridBagConstraints;

        spList = new javax.swing.JScrollPane();
        lstDeltaCfgs = new org.jdesktop.swingx.JXList();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        spList.setViewportView(lstDeltaCfgs);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        add(spList, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @throws  IllegalStateException  DOCUMENT ME!
     */
    public final void init() {
        final Object selectedConfig = lstDeltaCfgs.getSelectedValue();

        final SwingWorker<CidsBean[], Void> worker = new SwingWorker<CidsBean[], Void>() {

                @Override
                protected CidsBean[] doInBackground() throws Exception {
                    // FIXME: Do not load all configuration just the configuration from the initial configuration where
                    // the user work with

                    final MetaClass mc = ClassCacheMultiple.getMetaClass("SUDPLAN-WUPP", "delta_configuration");   // NOI18N
                    if (mc == null) {
                        throw new IllegalStateException(
                            "illegal domain for this operation, mc 'delta_configuration@SUDPLAN-WUPP' not found"); // NOI18N
                    }

                    final String query = "select " + mc.getID() + "," + mc.getPrimaryKey() + " from "
                                + mc.getTableName() + " where locked = false";

                    final MetaObject[] deltaCfgObjects = SessionManager.getProxy()
                                .getMetaObjectByQuery(SessionManager.getSession().getUser(),
                                    query,
                                    SMSUtils.DOMAIN_SUDPLAN_WUPP);

                    final CidsBean[] beans = new CidsBean[deltaCfgObjects.length];

                    for (int i = 0; i < deltaCfgObjects.length; ++i) {
                        beans[i] = deltaCfgObjects[i].getBean();
                    }

                    return beans;
                }

                @Override
                protected void done() {
                    if (!isCancelled()) {
                        try {
                            final DefaultListModel listModel = (DefaultListModel)lstDeltaCfgs.getWrappedModel();

                            listModel.clear();
                            final CidsBean[] result = get();

                            for (final CidsBean bean : result) {
                                listModel.addElement(bean);
                            }

                            lstDeltaCfgs.setSelectedValue(selectedConfig, true);
                        } catch (final ExecutionException eex) {
                            LOG.error("error during fetch of the cids beans", eex); // NOI18N
                        } catch (final InterruptedException iex) {
                            LOG.error("List init interrupted", iex);                // NOI18N
                        }
                    }
                }
            };

        SudplanConcurrency.getSudplanGeneralPurposePool().submit(worker);
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void fireConfigsChanged() {
        init();
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public CidsBean getSelectedConfig() {
        final Object o = lstDeltaCfgs.getSelectedValue();
        if (o == null) {
            return null;
        }
        return (CidsBean)o;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  l  DOCUMENT ME!
     */
    @Override
    public void addSelectionListener(final ListSelectionListener l) {
        synchronized (listeners) {
            listeners.add(l);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  l  DOCUMENT ME!
     */
    @Override
    public void removeSelectionListener(final ListSelectionListener l) {
        synchronized (listeners) {
            listeners.remove(l);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class DeltaCfgCellRenderer extends DefaultListCellRenderer {

        //~ Instance fields ----------------------------------------------------

        private final transient DeltaConfigurationToStringConverter toString;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new DeltaCfgCellRenderer object.
         */
        public DeltaCfgCellRenderer() {
            toString = new DeltaConfigurationToStringConverter();
        }

        //~ Methods ------------------------------------------------------------

        @Override
        public Component getListCellRendererComponent(final JList list,
                final Object value,
                final int index,
                final boolean isSelected,
                final boolean cellHasFocus) {
            final Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (c instanceof JLabel) {
                final JLabel l = (JLabel)c;
                l.setText(toString.convert(((CidsBean)value).getMetaObject()));
                if (value instanceof CidsBean) {
                    final CidsBean bean = (CidsBean)value;
                    if ((bean != null) && (bean.getMetaObject() != null)
                                && (bean.getMetaObject().getMetaClass() != null)) {
                        l.setIcon(ImageUtilities.loadImageIcon(
                                "/de/cismet/cids/custom/sudplan/wupp/geocpm_delta_16.png",
                                false));
                    }
                }
            }

            return c;
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class SelectionListener implements ListSelectionListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void valueChanged(final ListSelectionEvent lse) {
            if (lse.getValueIsAdjusting()) {
                return;
            }

            final Iterator<ListSelectionListener> it;
            synchronized (listeners) {
                it = new ArrayList<ListSelectionListener>(listeners).iterator();
            }

            while (it.hasNext()) {
                it.next().valueChanged(lse);
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class ListComparator implements Comparator<CidsBean> {

        //~ Methods ------------------------------------------------------------

        @Override
        public int compare(final CidsBean o1, final CidsBean o2) {
            if ((o1 == null) && (o2 == null)) {
                return 0;
            } else if ((o1 == null) && (o2 != null)) {
                return -1;
            } else if ((o1 != null) && (o2 == null)) {
                return 1;
            } else {
                return ((String)o1.getProperty("name")).compareTo((String)o2.getProperty("name")); // NOI18N
            }
        }
    }
}
