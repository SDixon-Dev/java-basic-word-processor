/**
 * Handles copying selected text from the word processor
 * to the system clipboard.
 *
 * @author seand
 */

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTextPane;

public class Copy {

    /**
     * Copies the currently selected text to the system clipboard.
     *
     * @param text the JTextPane containing the selected text
     */
    public void copy(JTextPane text) {

        // Gets the selected text.
        String copiedText = text.getSelectedText();

        // Copies when text selected.
        if (copiedText != null && !copiedText.isEmpty()) {

            // Converts the selected text into clipboard-compatible data.
            StringSelection selection =
                    new StringSelection(copiedText);

            // Gets OS clipboard.
            Clipboard clipboard =
                    Toolkit.getDefaultToolkit().getSystemClipboard();

            // Places text onto the clipboard.
            clipboard.setContents(selection, null);
        }
    }
}
