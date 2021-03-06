/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.sudplan.wupp.featurerenderer;

import Sirius.server.middleware.types.MetaObject;

import org.apache.log4j.Logger;

import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;

import de.cismet.cids.custom.sudplan.wupp.BreakingEdgeEditorPanel;

import de.cismet.cids.featurerenderer.CustomCidsFeatureRenderer;

import de.cismet.cismap.commons.gui.piccolo.OldFixedWidthStroke;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class GeocpmBreakingEdgeFeatureRenderer extends CustomCidsFeatureRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final Logger LOG = Logger.getLogger(GeocpmBreakingEdgeFeatureRenderer.class);

    //~ Instance fields --------------------------------------------------------

    private final transient OldFixedWidthStroke stroke;
    private volatile BreakingEdgeEditorPanel bkEditorPanel = null;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GeocpmBreakingEdgeFeatureRenderer.
     */
    public GeocpmBreakingEdgeFeatureRenderer() {
        this.stroke = new OldFixedWidthStroke();

        initComponents();

        if (bkEditorPanel == null) {
            synchronized (GeocpmBreakingEdgeFeatureRenderer.class) {
                if (bkEditorPanel == null) {
                    bkEditorPanel = new BreakingEdgeEditorPanel();
                    this.add(bkEditorPanel);
                }
            }
        }
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        setLayout(new java.awt.BorderLayout());
    } // </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void assign() {
        // TODO: init
    }

    @Override
    public Paint getLinePaint() {
        // TODO: height-depending colors final int height = ((BigDecimal)cidsBean.getProperty("height")).multiply(new
        // BigDecimal(100)).intValue(); // NOI18N
        final int type = (Integer)cidsBean.getProperty("type"); // NOI18N

        final float r = 0;
//        final float y = 1/6f;
//        final float g = 1/3f;
        final float b = 2 / 3f;

        final float color;
        if (type == 0) {
            // boardwalk
            color = r;
        } else {
            // building
            color = b;
        }

        return Color.getHSBColor(color, 1f, 1f);
    }

    @Override
    public Stroke getLineStyle() {
        stroke.setMultiplyer(15);

        return stroke;
    }

    @Override
    public void setMetaObject(final MetaObject metaObject) {
        try {
            super.setMetaObject(metaObject);
            bkEditorPanel.init(metaObject.getBean(), true);
        } catch (Exception e) {
            LOG.error("Cannot initialise editor panel", e);
        }
    }

    @Override
    public float getTransparency() {
        return 1f;
    }
}
