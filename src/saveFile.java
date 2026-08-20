/**
 *
 * @author seand
 */

// imports Java library elements used by the class
import javax.swing.JTextPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.text.BadLocationException;

public class saveFile {

    // Stores the file currently being worked on
    private File currentFile = null;

    public void setCurrentFile(File file){
        currentFile = file;
    }


    public boolean save(JTextPane text) {

        // Checks if the text box is empty
        if (text.getText().length() > 0) {

            /*
             * No current file means this is the first save,
             * ask user to choose a file name/location.
             */
            if (currentFile == null) {

                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(false);

                FileNameExtensionFilter fileFilter =
                        new FileNameExtensionFilter(
                                "RICH TEXT FORMAT",
                                "rtf"
                        );

                fileChooser.setFileFilter(fileFilter);

                int option = fileChooser.showSaveDialog(null);

                if (option == JFileChooser.APPROVE_OPTION) {

                    File selectedFile = fileChooser.getSelectedFile();

                    // Adds .rtf if user didn't enter it
                    if (!selectedFile.getName().toLowerCase().endsWith(".rtf")) {
                        selectedFile = new File(
                                selectedFile.getAbsolutePath() + ".rtf"
                        );
                    }

                    // Checks if file already exists
                    if (selectedFile.exists()) {

                        if (confirmOverwrite(selectedFile)) {

                            currentFile = selectedFile;
                            return writeFile(text);

                        } else {

                            // User chose not to overwrite
                            return false;
                        }

                    } else {

                        currentFile = selectedFile;
                        return writeFile(text);
                    }

                } else {

                    JOptionPane.showMessageDialog(
                            null,
                            "Text save cancelled.",
                            "Save Cancelled",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    return false;
                }

            } else {

                /*
                 * A current file already exists,
                 * saving overwrites previous content.
                 */
                if (confirmOverwrite(currentFile)) {

                    return writeFile(text);

                } else {

                    return false;
                }
            }

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "There is no text to save.",
                    "Nothing to Save",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return false;
        }
    }


    private boolean confirmOverwrite(File file) {

        int choice = JOptionPane.showConfirmDialog(
                null,
                "The file \"" + file.getName() + "\" already exists.\n"
                + "Do you want to overwrite it?",
                "Confirm Overwrite",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        return choice == JOptionPane.YES_OPTION;
    }


    /*
     * Writes the JTextPane contents into the current file.
     * Returns true if the save succeeds.
     */
    private boolean writeFile(JTextPane text) {

        StyledDocument document =
                (StyledDocument) text.getDocument();

        RTFEditorKit editorKit =
                new RTFEditorKit();

        try (
            BufferedOutputStream out =
                    new BufferedOutputStream(
                            new FileOutputStream(currentFile)
                    )
        ) {

            editorKit.write(
                    out,
                    document,
                    document.getStartPosition().getOffset(),
                    document.getLength()
            );

            JOptionPane.showMessageDialog(
                    null,
                    "File saved successfully.",
                    "Save Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return true;

        } catch (IOException | BadLocationException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "The file could not be saved.",
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return false;
        }
    }
}