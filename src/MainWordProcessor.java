/**
 * Main entry point for the Basic Word Processor application.
 *
 * Creates the main JFrame, attaches the TextDisplay GUI,
 * Prevents the application from closing when unsaved
 * changes are present without first warning the user.
 *
 * @author seand
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWordProcessor {

    public static void main(String args[]) {

        // Creates application window
        JFrame frame = new JFrame("Word Processor");

        // Prevents auto-closing application for checking for unsaved changes.
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Creates the main GUI
        TextDisplay textDisplay = new TextDisplay();
        frame.add(textDisplay);
        
        // Adds custom behaviour for closing the appliction
        addExitConfirmation(frame, textDisplay);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
        
    /**
     * Adds a window listener that warns the user before exiting
     * when the current document contains unsaved changes.
     *
     * @param frame the application's main JFrame
     * @param textDisplay the GUI containing the current document
     */
    private static void addExitConfirmation(
            JFrame frame,
            TextDisplay textDisplay) {

        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                if (!textDisplay.hasUnsavedChanges()) {
                    frame.dispose();
                    return;
                }

                int choice = JOptionPane.showConfirmDialog(
                        frame,
                        "You have unsaved changes.\n"
                        + "Are you sure you want to exit?",
                        "Unsaved Changes",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {
                    frame.dispose();
                }
            }
        });
    }
}