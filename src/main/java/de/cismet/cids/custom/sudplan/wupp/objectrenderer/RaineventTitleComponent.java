/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.sudplan.wupp.objectrenderer;

import org.openide.util.NbBundle;

import java.awt.EventQueue;

import de.cismet.cids.custom.objectactions.sudplan.ActionProviderFactory;
import de.cismet.cids.custom.sudplan.wupp.RunGeoCPMWizardAction;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.utils.interfaces.CidsBeanAction;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public class RaineventTitleComponent extends javax.swing.JPanel {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRunGeoCPM;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form GeoCPMCfgTitleComponent.
     */
    public RaineventTitleComponent() {
        initComponents();
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @param  title  DOCUMENT ME!
     */
    public void setTitle(final String title) {
        if (EventQueue.isDispatchThread()) {
            lblTitle.setText(title);
        } else {
            EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        lblTitle.setText(title);
                    }
                });
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @param  cidsBean  DOCUMENT ME!
     */
    public void setCidsBean(final CidsBean cidsBean) {
        if (btnRunGeoCPM.getAction() instanceof CidsBeanAction) {
            final CidsBeanAction cba = (CidsBeanAction)btnRunGeoCPM.getAction();
            cba.setCidsBean(cidsBean);

            // trigger the action enable
            cba.isEnabled();
        }

        setTitle((String)cidsBean.getProperty("name")); // NOI18N
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        lblTitle = new javax.swing.JLabel();
        btnRunGeoCPM = new javax.swing.JButton();

        setOpaque(false);
        setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));                                                          // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText(NbBundle.getMessage(RaineventTitleComponent.class, "RaineventTitleComponent.lblTitle.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(lblTitle, gridBagConstraints);

        btnRunGeoCPM.setAction(ActionProviderFactory.getCidsBeanAction(RunGeoCPMWizardAction.class));
        btnRunGeoCPM.setText(NbBundle.getMessage(
                RaineventTitleComponent.class,
                "RaineventTitleComponent.btnRunGeoCPM.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(btnRunGeoCPM, gridBagConstraints);
    }                                                          // </editor-fold>//GEN-END:initComponents
}