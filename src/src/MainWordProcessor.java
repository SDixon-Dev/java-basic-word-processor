/**
 *
 * @author seand
 */

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWordProcessor {

    public static void main(String args[]) {

        JFrame frame = new JFrame("Word Processor");

        // Prevents the application from closing automatically
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // Creates the main GUI panel
        TextDisplay textDisplay = new TextDisplay();

        // Adds the GUI panel to the frame
        frame.getContentPane().add(textDisplay);

        /*
         * Checks for unsaved changes when the user
         * attempts to close the application.
         */
        frame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                if (textDisplay.hasUnsavedChanges()) {

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

                } else {

                    frame.dispose();
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}