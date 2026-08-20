/**
 * Handles paste functionality for the Basic Word Processor.
 *
 * Retrieves text from the system clipboard and inserts it
 * into the JTextPane at the current cursor position or
 * replaces the currently selected text.
 *
 * @author seand
 */

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JTextPane;

public class Paste {

    /**
     * Pastes text from the system clipboard into the text editor.
     *
     * @param text the JTextPane receiving the clipboard text
     */
    public void paste(JTextPane text) {

        // Gets the operating system clipboard.
        Clipboard clipboard =
                Toolkit.getDefaultToolkit().getSystemClipboard();

        // Gets the current contents of the clipboard.
        Transferable clipboardContents =
                clipboard.getContents(null);

        // Checks that the clipboard contains text.
        if (clipboardContents == null
                || !clipboardContents.isDataFlavorSupported(
                        DataFlavor.stringFlavor)) {
            return;
        }

        try {

            // Retrieves text stored in the clipboard.
            String pastedText =
                    (String) clipboardContents.getTransferData(
                            DataFlavor.stringFlavor);

            /*
             * Inserts the clipboard text at the current cursor position.
             * If text is selected, the selected text is replaced.
             */
            text.replaceSelection(pastedText);

        } catch (UnsupportedFlavorException | IOException e) {
            e.printStackTrace();
        }
    }
}