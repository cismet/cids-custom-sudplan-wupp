/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.sudplan.wupp.objectrenderer;

import Sirius.navigator.ui.RequestsFullSizeComponent;

import com.vividsolutions.jts.geom.Geometry;

import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;

import org.apache.log4j.Logger;

import org.jdesktop.beansbinding.Converter;

import java.awt.EventQueue;

import de.cismet.cids.custom.sudplan.AbstractCidsBeanRenderer;
import de.cismet.cids.custom.sudplan.SMSUtils;
import de.cismet.cids.custom.sudplan.wupp.GeoCPMOptions;
import de.cismet.cids.custom.sudplan.wupp.SurfaceManipulationWizardAction;
import de.cismet.cids.custom.sudplan.wupp.objecteditors.DeltaConfigurationEditor;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cismap.commons.Crs;
import de.cismet.cismap.commons.CrsTransformer;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.interaction.CismapBroker;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.cismap.commons.retrieval.RetrievalEvent;
import de.cismet.cismap.commons.retrieval.RetrievalListener;

import de.cismet.cismap.navigatorplugin.CidsFeature;

/**
 * DOCUMENT ME!
 *
 * @author   jlauter
 * @version  $Revision$, $Date$
 */
public class DeltaSurfaceRenderer extends AbstractCidsBeanRenderer implements RequestsFullSizeComponent {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(DeltaSurfaceRenderer.class);

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private de.cismet.cids.custom.sudplan.wupp.objecteditors.DeltaConfigurationEditor deltaConfigurationEditor;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblMapHeader;
    private javax.swing.JLabel lblMetadataHeader;
    private javax.swing.JLabel lblSurfaceDescription;
    private javax.swing.JLabel lblSurfaceHeight;
    private javax.swing.JLabel lblSurfaceName;
    private javax.swing.JLabel lblSurfaceType;
    private javax.swing.JLabel lblUnit;
    private de.cismet.cismap.commons.gui.MappingComponent map;
    private de.cismet.tools.gui.SemiRoundedPanel pnlConfigurationHeader;
    private de.cismet.tools.gui.RoundedPanel pnlDeltaConfiguration;
    private de.cismet.tools.gui.RoundedPanel pnlDeltaSurface;
    private de.cismet.tools.gui.RoundedPanel pnlMap;
    private de.cismet.tools.gui.SemiRoundedPanel pnlMetadataHeader;
    private de.cismet.tools.gui.SemiRoundedPanel semiRoundedPanel1;
    private javax.swing.JScrollPane spSurfaceDescription;
    private javax.swing.JTextArea txaSurfaceDescription;
    private javax.swing.JTextField txtSurfaceHeight;
    private javax.swing.JTextField txtSurfaceName;
    private javax.swing.JTextField txtSurfaceType;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form DeltaSurfaceRenderer.
     */
    public DeltaSurfaceRenderer() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        pnlDeltaSurface = new de.cismet.tools.gui.RoundedPanel();
        pnlMetadataHeader = new de.cismet.tools.gui.SemiRoundedPanel();
        lblMetadataHeader = new javax.swing.JLabel();
        lblSurfaceName = new javax.swing.JLabel();
        txtSurfaceName = new javax.swing.JTextField();
        lblSurfaceDescription = new javax.swing.JLabel();
        spSurfaceDescription = new javax.swing.JScrollPane();
        txaSurfaceDescription = new javax.swing.JTextArea();
        lblSurfaceHeight = new javax.swing.JLabel();
        txtSurfaceHeight = new javax.swing.JTextField();
        lblSurfaceType = new javax.swing.JLabel();
        txtSurfaceType = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        lblUnit = new javax.swing.JLabel();
        pnlDeltaConfiguration = new de.cismet.tools.gui.RoundedPanel();
        pnlConfigurationHeader = new de.cismet.tools.gui.SemiRoundedPanel();
        jLabel3 = new javax.swing.JLabel();
        deltaConfigurationEditor = new DeltaConfigurationEditor(false);
        pnlMap = new de.cismet.tools.gui.RoundedPanel();
        semiRoundedPanel1 = new de.cismet.tools.gui.SemiRoundedPanel();
        lblMapHeader = new javax.swing.JLabel();
        map = new de.cismet.cismap.commons.gui.MappingComponent();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(986, 800));
        setLayout(new java.awt.GridBagLayout());

        pnlDeltaSurface.setLayout(new java.awt.GridBagLayout());

        pnlMetadataHeader.setBackground(new java.awt.Color(51, 51, 51));
        pnlMetadataHeader.setLayout(new java.awt.FlowLayout());

        lblMetadataHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblMetadataHeader.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblMetadataHeader.text")); // NOI18N
        pnlMetadataHeader.add(lblMetadataHeader);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        pnlDeltaSurface.add(pnlMetadataHeader, gridBagConstraints);

        lblSurfaceName.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblSurfaceName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(lblSurfaceName, gridBagConstraints);

        txtSurfaceName.setEditable(false);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.name}"),
                txtSurfaceName,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(txtSurfaceName, gridBagConstraints);

        lblSurfaceDescription.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblSurfaceDescription.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(lblSurfaceDescription, gridBagConstraints);

        txaSurfaceDescription.setColumns(20);
        txaSurfaceDescription.setEditable(false);
        txaSurfaceDescription.setLineWrap(true);
        txaSurfaceDescription.setRows(5);
        txaSurfaceDescription.setWrapStyleWord(true);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.description}"),
                txaSurfaceDescription,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        spSurfaceDescription.setViewportView(txaSurfaceDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(spSurfaceDescription, gridBagConstraints);

        lblSurfaceHeight.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblSurfaceHeight.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(lblSurfaceHeight, gridBagConstraints);

        txtSurfaceHeight.setEditable(false);
        txtSurfaceHeight.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtSurfaceHeight.setMinimumSize(new java.awt.Dimension(50, 27));
        txtSurfaceHeight.setPreferredSize(new java.awt.Dimension(50, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.height}"),
                txtSurfaceHeight,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(txtSurfaceHeight, gridBagConstraints);

        lblSurfaceType.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSurfaceType.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblSurfaceType.text")); // NOI18N
        lblSurfaceType.setPreferredSize(new java.awt.Dimension(20, 17));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(lblSurfaceType, gridBagConstraints);

        txtSurfaceType.setEditable(false);
        txtSurfaceType.setPreferredSize(new java.awt.Dimension(120, 27));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.sea_type}"),
                txtSurfaceType,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setConverter(new TypePropertyConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(txtSurfaceType, gridBagConstraints);

        jButton1.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    jButton1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(jButton1, gridBagConstraints);

        lblUnit.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblUnit.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaSurface.add(lblUnit, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(pnlDeltaSurface, gridBagConstraints);

        pnlDeltaConfiguration.setLayout(new java.awt.GridBagLayout());

        pnlConfigurationHeader.setBackground(new java.awt.Color(51, 51, 51));
        pnlConfigurationHeader.setLayout(new java.awt.FlowLayout());

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.jLabel3.text")); // NOI18N
        pnlConfigurationHeader.add(jLabel3);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        pnlDeltaConfiguration.add(pnlConfigurationHeader, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlDeltaConfiguration.add(deltaConfigurationEditor, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(pnlDeltaConfiguration, gridBagConstraints);

        pnlMap.setPreferredSize(new java.awt.Dimension(145, 300));
        pnlMap.setLayout(new java.awt.GridBagLayout());

        semiRoundedPanel1.setBackground(new java.awt.Color(51, 51, 51));
        semiRoundedPanel1.setLayout(new java.awt.FlowLayout());

        lblMapHeader.setForeground(new java.awt.Color(255, 255, 255));
        lblMapHeader.setText(org.openide.util.NbBundle.getMessage(
                DeltaSurfaceRenderer.class,
                "DeltaSurfaceRenderer.lblMapHeader.text")); // NOI18N
        semiRoundedPanel1.add(lblMapHeader);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        pnlMap.add(semiRoundedPanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        pnlMap.add(map, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(pnlMap, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void jButton1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_jButton1ActionPerformed
        new SurfaceManipulationWizardAction(cidsBean).actionPerformed(evt);
    }                                                                            //GEN-LAST:event_jButton1ActionPerformed

    @Override
    protected void init() {
        bindingGroup.unbind();
        bindingGroup.bind();

        deltaConfigurationEditor.setCidsBean((CidsBean)cidsBean.getProperty("delta_configuration"));
        initMap();
    }

    /**
     * DOCUMENT ME!
     */
    private void initMap() {
        try {
            final Geometry geom = (Geometry)cidsBean.getProperty("geom.geo_field");
            final Geometry geom31466 = CrsTransformer.transformToGivenCrs(geom.getEnvelope(), SMSUtils.EPSG_WUPP)
                        .buffer(60);
            // geom31466 = geom31466.buffer(50);
            final XBoundingBox bbox = new XBoundingBox(geom31466, SMSUtils.EPSG_WUPP, true);

            final ActiveLayerModel mappingModel = new ActiveLayerModel();
            mappingModel.setSrs(new Crs(SMSUtils.EPSG_WUPP, SMSUtils.EPSG_WUPP, SMSUtils.EPSG_WUPP, true, true));

            mappingModel.addHome(bbox);

            final SimpleWMS ortho = new SimpleWMS(new SimpleWmsGetMapUrl(
                        GeoCPMOptions.getInstance().getProperty("template.getmap.orthophoto").replace(
                            "<cismap:srs>",
                            "EPSG:31466")));

            ortho.setName("Wuppertal Ortophoto"); // NOI18N

            final RetrievalListener rl = new RetrievalListener() {

                    private final transient String text = lblMapHeader.getText();

                    @Override
                    public void retrievalStarted(final RetrievalEvent e) {
                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    lblMapHeader.setText(text + "( Loading... )");
                                }
                            });
                    }

                    @Override
                    public void retrievalProgress(final RetrievalEvent e) {
                    }

                    @Override
                    public void retrievalComplete(final RetrievalEvent e) {
                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    lblMapHeader.setText(text + "( Double click preview to add )");
                                }
                            });
                    }

                    @Override
                    public void retrievalAborted(final RetrievalEvent e) {
                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    lblMapHeader.setText(text + "( Retrieval Aborted )");
                                }
                            });
                    }

                    @Override
                    public void retrievalError(final RetrievalEvent e) {
                        EventQueue.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    lblMapHeader.setText(text + "( Retrieval Error )");
                                }
                            });
                    }
                };

            ortho.addRetrievalListener(rl);

            mappingModel.addLayer(ortho);

            map.setMappingModel(mappingModel);

            map.gotoInitialBoundingBox();

            map.unlock();
            map.setInteractionMode(MappingComponent.ZOOM);
            final CidsFeature feature = new CidsFeature(cidsBean.getMetaObject());
            map.getFeatureCollection().addFeature(feature);
            map.addCustomInputListener("DoubleClick", new PBasicInputEventHandler() {

                    @Override
                    public void mouseClicked(final PInputEvent pie) {
                        if (pie.getClickCount() > 1) {
                            SMSUtils.showMappingComponent();
                            CismapBroker.getInstance().getMappingComponent().gotoBoundingBoxWithHistory(bbox);
                            CismapBroker.getInstance().getMappingComponent().getFeatureCollection().addFeature(feature);
                        }
                    }
                });
            map.setInteractionMode("DoubleClick");
        } catch (Exception e) {
            LOG.error("cannot initialise mapping component", e);
        }
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private static final class TypePropertyConverter extends Converter<Boolean, String> {

        //~ Methods ------------------------------------------------------------

        @Override
        public String convertForward(final Boolean s) {
            if (s == null) {
                return null;
            }
            if (s.booleanValue()) {
                return "Modification to sea level";
            } else {
                return "Modification to adjacent surface";
            }
        }

        @Override
        public Boolean convertReverse(final String t) {
            return null;
        }
    }
}
