/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.sudplan.wupp;

import org.openide.util.NbBundle;
import org.openide.util.WeakListeners;

import java.awt.EventQueue;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * DOCUMENT ME!
 *
 * @author   mscholl
 * @version  $Revision$, $Date$
 */
public final class RunGeoCPMVisualPanelMetadata extends javax.swing.JPanel {

    //~ Instance fields --------------------------------------------------------

    private final transient RunGeoCPMWizardPanelMetadata model;
    private final transient DocumentListener nameL;
    private final transient DocumentListener descL;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private final transient javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
    private final transient javax.swing.JLabel lblDescription = new javax.swing.JLabel();
    private final transient javax.swing.JLabel lblName = new javax.swing.JLabel();
    private final transient javax.swing.JTextArea txaDescription = new javax.swing.JTextArea();
    private final transient javax.swing.JTextField txtName = new javax.swing.JTextField();
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form RainfallDownscalingVisualPanelTargetDate.
     *
     * @param  model  DOCUMENT ME!
     */
    public RunGeoCPMVisualPanelMetadata(final RunGeoCPMWizardPanelMetadata model) {
        this.model = model;
        this.nameL = new NameListenerImpl();
        this.descL = new DescListenerImpl();

        // name of the wizard step
        this.setName(NbBundle.getMessage(
                RunGeoCPMVisualPanelMetadata.class,
                "RunGeoCPMVisualPanelMetadata.this.name")); // NOI18N

        initComponents();

        txtName.getDocument().addDocumentListener(WeakListeners.document(nameL, txtName.getDocument()));
        txaDescription.getDocument().addDocumentListener(WeakListeners.document(descL, txaDescription.getDocument()));
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    void init() {
        final Runnable r = new Runnable() {

                @Override
                public void run() {
                    if (model.getName() == null) {
                        txtName.setText(NbBundle.getMessage(
                                RunGeoCPMVisualPanelMetadata.class,
                                "RunGeoCPMVisualPanelMetadata.txtName.text")); // NOI18N
                    } else {
                        txtName.setText(model.getName());
                    }

                    txtName.setSelectionStart(0);
                    txtName.setSelectionEnd(txtName.getText().length());

                    txaDescription.setText(model.getDescription());

                    txtName.requestFocus();
                }
            };

        if (EventQueue.isDispatchThread()) {
            r.run();
        } else {
            EventQueue.invokeLater(r);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        setLayout(new java.awt.GridBagLayout());

        lblName.setText(NbBundle.getMessage(
                RunGeoCPMVisualPanelMetadata.class,
                "RunGeoCPMVisualPanelMetadata.lblName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(lblName, gridBagConstraints);

        txtName.setText(NbBundle.getMessage(
                RunGeoCPMVisualPanelMetadata.class,
                "RunGeoCPMVisualPanelMetadata.txtName.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(txtName, gridBagConstraints);

        lblDescription.setText(NbBundle.getMessage(
                RunGeoCPMVisualPanelMetadata.class,
                "RunGeoCPMVisualPanelMetadata.lblDescription.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 6);
        add(lblDescription, gridBagConstraints);

        txaDescription.setColumns(20);
        txaDescription.setRows(5);
        jScrollPane1.setViewportView(txaDescription);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jScrollPane1, gridBagConstraints);
    } // </editor-fold>//GEN-END:initComponents

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class NameListenerImpl implements DocumentListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void insertUpdate(final DocumentEvent e) {
            model.setName(txtName.getText());
        }

        @Override
        public void removeUpdate(final DocumentEvent e) {
            model.setName(txtName.getText());
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            model.setName(txtName.getText());
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    private final class DescListenerImpl implements DocumentListener {

        //~ Methods ------------------------------------------------------------

        @Override
        public void insertUpdate(final DocumentEvent e) {
            model.setDescription(txaDescription.getText());
        }

        @Override
        public void removeUpdate(final DocumentEvent e) {
            model.setDescription(txaDescription.getText());
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            model.setDescription(txaDescription.getText());
        }
    }
}
