/**
 * Handles loading RTF files into the word processor.
 *
 * Opens a file chooser, reads the selected RTF file into a new
 * StyledDocument, and displays the loaded document in the JTextPane.
 *
 * @author seand
 */

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class ImportFile {

    /**
     * Opens an RTF file and loads its contents into the text editor.
     *
     * @param text the JTextPane that will display the loaded document
     * @return the successfully loaded file, or null if loading is cancelled
     *         or fails
     */
    public File open(JTextPane text) {

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);

        // Restricts the file chooser to RTF files.
        FileNameExtensionFilter fileFilter =
                new FileNameExtensionFilter(
                        "Rich Text Format (*.rtf)",
                        "rtf"
                );

        fileChooser.setFileFilter(fileFilter);

        // Displays the Open dialog.
        int option = fileChooser.showOpenDialog(null);

        if (option != JFileChooser.APPROVE_OPTION) {

            JOptionPane.showMessageDialog(
                    null,
                    "File opening cancelled.",
                    "Open Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return null;
        }

        File selectedFile = fileChooser.getSelectedFile();

        RTFEditorKit editorKit = new RTFEditorKit();

        // Creates new document so the loaded file replaces current contents.
        StyledDocument document =
                (StyledDocument) editorKit.createDefaultDocument();

        /*
         * Opens the selected file, reads RTF contents into the
         * new document, auto closes the input stream.
         */
        try (
            BufferedInputStream in =
                    new BufferedInputStream(
                            new FileInputStream(selectedFile)
                    )
        ) {

            editorKit.read(in, document, 0);

            // Displays the loaded document in the editor.
            text.setDocument(document);

            JOptionPane.showMessageDialog(
                    null,
                    "File loaded successfully.",
                    "Load Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return selectedFile;

        } catch (IOException | BadLocationException e) {

            JOptionPane.showMessageDialog(
                    null,
                    "The file could not be loaded.",
                    "Load Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return null;
        }
    }
}