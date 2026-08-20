// imports java library elements used by the class
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.IOException;

import javax.swing.JTextPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;


public class ImportFile {

    public File open(JTextPane text) {

        JFileChooser fileChooser = new JFileChooser();

        // Allows the selection of only one file
        fileChooser.setMultiSelectionEnabled(false);

        // Filters the file chooser so RTF files are displayed
        FileNameExtensionFilter fileFilter =
                new FileNameExtensionFilter(
                        "RICH TEXT FORMAT",
                        "rtf"
                );

        fileChooser.setFileFilter(fileFilter);

        // Displays the Open dialog box
        int option = fileChooser.showOpenDialog(null);

        
        // Attempts to access the selected file if user clicks Open.
        
        if (option == JFileChooser.APPROVE_OPTION) {

            File selectedFile = fileChooser.getSelectedFile();

            RTFEditorKit editorKit = new RTFEditorKit();

            /*
             * Creates a new empty document.
             * The loaded file will be read into this
             * rather than being inserted into the
             * document already displayed.
             */
            StyledDocument document =
                    (StyledDocument) editorKit.createDefaultDocument();

           
            //Creates input stream to load the selected file.
           
            try (
                BufferedInputStream in =
                        new BufferedInputStream(
                                new FileInputStream(selectedFile)
                        )
            ) {

                // Reads the selected RTF file from the beginning
                editorKit.read(in, document, 0);

                // Displays the loaded document in the JTextPane
                text.setDocument(document);

                JOptionPane.showMessageDialog(
                        null,
                        "File loaded successfully.",
                        "Load Successful",
                        JOptionPane.INFORMATION_MESSAGE
                );
                
                //returns file successfully loaded
                return selectedFile;

            } catch (IOException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "The file could not be loaded.",
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE
                );
                
                return null;
           
            } catch (BadLocationException e) {

                JOptionPane.showMessageDialog(
                        null,
                        "The file contents could not be displayed.",
                        "Load Error",
                        JOptionPane.ERROR_MESSAGE
                );
                
                return null;
            }

        } else {

            JOptionPane.showMessageDialog(
                    null,
                    "Text open cancelled.",
                    "Load Cancelled",
                    JOptionPane.INFORMATION_MESSAGE
            );
            
            return null;
        }
    }
}

