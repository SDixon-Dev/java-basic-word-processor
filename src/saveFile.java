/**
 * Handles saving RTF documents for the Basic Word Processor.
 *
 * Manages the currently open file, prompts the user for a save
 * location when necessary, confirms file overwrites, and writes
 * the JTextPane contents to an RTF file.
 *
 * @author seand
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class SaveFile {

    // Stores the file currently associated with the open document.
    private File currentFile;

    /**
     * Sets the file currently being edited.
     *
     * Used when an existing file is opened so subsequent saves
     * update the same file rather than opening a new save dialog.
     *
     * @param file the currently open file
     */
    public void setCurrentFile(File file) {
        currentFile = file;
    }

    /**
     * Saves the current text document.
     *
     * If no file is currently associated with the document, the user
     * is prompted to choose a save location. Existing files require
     * overwrite confirmation before being replaced.
     *
     * @param text the JTextPane containing the document to save
     * @return true if the document was saved successfully
     */
    public boolean save(JTextPane text) {

        // Prevents saving an empty document.
        if (text.getText().isEmpty()) {

            JOptionPane.showMessageDialog(
                    null,
                    "There is no text to save.",
                    "Nothing to Save",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return false;
        }

        /*
         * If no file is currently associated with the document,
         * prompt user to choose a filename and location.
         */
        if (currentFile == null) {
            return chooseSaveLocation(text);
        }

        // Existing files require overwrite confirmation.
        if (!confirmOverwrite(currentFile)) {
            return false;
        }

        return writeFile(text);
    }

    /**
     * Displays a file chooser and selects the location for a new file.
     *
     * @param text the JTextPane containing the document to save
     * @return true if the document was saved successfully
     */
    private boolean chooseSaveLocation(JTextPane text) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);

        FileNameExtensionFilter fileFilter =
                new FileNameExtensionFilter(
                        "Rich Text Format (*.rtf)",
                        "rtf"
                );

        fileChooser.setFileFilter(fileFilter);

        int option = fileChooser.showSaveDialog(null);

        if (option != JFileChooser.APPROVE_OPTION) {

            JOptionPane.showMessageDialog(
                    null,
                    "File save cancelled.",
                    "Save Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return false;
        }

        File selectedFile = fileChooser.getSelectedFile();

        // Automatically adds the .rtf extension.
        if (!selectedFile.getName().toLowerCase().endsWith(".rtf")) {
            selectedFile = new File(
                    selectedFile.getAbsolutePath() + ".rtf"
            );
        }

        // Warns before replacing an existing file.
        if (selectedFile.exists() && !confirmOverwrite(selectedFile)) {
            return false;
        }

        currentFile = selectedFile;

        return writeFile(text);
    }

    /**
     * Asks the user to confirm before overwriting an existing file.
     *
     * @param file the file that would be overwritten
     * @return true when the user approves the overwrite
     */
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

    /**
     * Writes the JTextPane document to the current RTF file.
     *
     * @param text the JTextPane containing the document
     * @return true if the file was written successfully
     */
    private boolean writeFile(JTextPane text) {

        StyledDocument document =
                (StyledDocument) text.getDocument();

        RTFEditorKit editorKit = new RTFEditorKit();

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