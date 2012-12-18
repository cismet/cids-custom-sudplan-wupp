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
import Sirius.navigator.ui.ComponentRegistry;

import Sirius.server.newuser.User;

import org.apache.log4j.Logger;

import org.openide.DialogDisplayer;
import org.openide.WizardDescriptor;
import org.openide.util.Cancellable;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import java.text.MessageFormat;

import javax.swing.Action;
import javax.swing.JComponent;

import de.cismet.cids.navigator.utils.CidsClientToolbarItem;

import de.cismet.cids.utils.abstracts.AbstractCidsBeanAction;

/**
 * DOCUMENT ME!
 *
 * @author   martin.scholl@cismet.de
 * @version  $Revision$, $Date$
 */
@org.openide.util.lookup.ServiceProvider(service = CidsClientToolbarItem.class)
public final class ImportGeoCPMWizardAction extends AbstractCidsBeanAction implements CidsClientToolbarItem {

    //~ Static fields/initializers ---------------------------------------------

    private static final transient Logger LOG = Logger.getLogger(ImportGeoCPMWizardAction.class);

    //~ Instance fields --------------------------------------------------------

    private transient WizardDescriptor.Panel[] panels;

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new ImportGeoCPMWizardAction object.
     */
    public ImportGeoCPMWizardAction() {
        super("", ImageUtilities.loadImageIcon("de/cismet/cids/custom/sudplan/wupp/geocpm_import.png", false)); // NOI18N

        putValue(
            Action.SHORT_DESCRIPTION,
            NbBundle.getMessage(ImportGeoCPMWizardAction.class, "ImportGeoCPMWizardAction.shortDescription")); // NOI18N
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private WizardDescriptor.Panel[] getPanels() {
        assert EventQueue.isDispatchThread() : "can only be called from EDT"; // NOI18N

        if (panels == null) {
            panels = new WizardDescriptor.Panel[] {
                    new ImportGeoCPMWizardPanelCFGSelect(),
                    new ImportGeoCPMWizardPanelMetadata(),
                    new ImportGeoCPMWizardPanelUpload()
                };

            final String[] steps = new String[panels.length];
            for (int i = 0; i < panels.length; i++) {
                final Component c = panels[i].getComponent();
                // Default step name to component name of panel. Mainly useful
                // for getting the name of the target chooser to appear in the
                // list of steps.
                steps[i] = c.getName();
                if (c instanceof JComponent) {
                    // assume Swing components
                    final JComponent jc = (JComponent)c;
                    // Sets step number of a component
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_SELECTED_INDEX, Integer.valueOf(i));
                    // Sets steps names for a panel
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DATA, steps);
                    // Turn on subtitle creation on each step
                    jc.putClientProperty(WizardDescriptor.PROP_AUTO_WIZARD_STYLE, Boolean.TRUE);
                    // Show steps on the left side with the image on the
                    // background
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_DISPLAYED, Boolean.TRUE);
                    // Turn on numbering of all steps
                    jc.putClientProperty(WizardDescriptor.PROP_CONTENT_NUMBERED, Boolean.TRUE);
                }
            }
        }

        return panels;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  e  DOCUMENT ME!
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        final WizardDescriptor wizard = new WizardDescriptor(getPanels());
        wizard.setTitleFormat(new MessageFormat("{0}"));                                                               // NOI18N
        wizard.setTitle(NbBundle.getMessage(ImportGeoCPMWizardAction.class, "ImportGeoCPMWizardAction.wizard.title")); // NOI18N

        final Dialog dialog = DialogDisplayer.getDefault().createDialog(wizard);
        dialog.pack();
        dialog.setLocationRelativeTo(ComponentRegistry.getRegistry().getMainWindow());
        dialog.setVisible(true);
        dialog.toFront();

        final boolean cancelled = wizard.getValue() != WizardDescriptor.FINISH_OPTION;

        if (cancelled) {
            for (final Object o : getPanels()) {
                if (o instanceof Cancellable) {
                    ((Cancellable)o).cancel();
                }
            }
        }
        // there is no need to do anything, when finished successfully
    }

    @Override
    public String getSorterString() {
        return "Z"; // NOI18N
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        final User user = SessionManager.getSession().getUser();

        // NOTE: won't use a timeout as this is read during startup and startup only (atm). the task doesn't have a
        // chance to finish in time during startup and so we won't use a timeout.
        try {
            return SessionManager.getProxy().hasConfigAttr(user, "sudplan.local.wupp.geocpm.import"); // NOI18N
        } catch (final ConnectionException ex) {
            LOG.warn("cannot check for config attr", ex);                                             // NOI18N

            return false;
        }
    }
}
