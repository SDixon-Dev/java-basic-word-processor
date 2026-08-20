/**
 * Provides text formatting attributes for the Basic Word Processor.
 *
 * Creates a Simple Attribute Set containing the selected font family,
 * font size, bold, italic and underline settings.
 * These can be applied to text within a JTextPane.
 *
 * @author seand
 */

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class FontControl {

    /**
     * Creates a set of text formatting attributes.
     *
     * @param fontFamily the selected font family
     * @param size the selected font size
     * @param bold whether bold formatting is enabled
     * @param italic whether italic formatting is enabled
     * @param underline whether underline formatting is enabled
     * @return the configured text formatting attributes
     */
    public SimpleAttributeSet createStyle(
            String fontFamily,
            int size,
            boolean bold,
            boolean italic,
            boolean underline) {

        // Creates a new collection of text formatting attributes
        SimpleAttributeSet attributes = new SimpleAttributeSet();

        // Applies the selected font family and size
        StyleConstants.setFontFamily(attributes, fontFamily);
        StyleConstants.setFontSize(attributes, size);

        // Applies the selected text styles
        StyleConstants.setBold(attributes, bold);
        StyleConstants.setItalic(attributes, italic);
        StyleConstants.setUnderline(attributes, underline);

        return attributes;
    }
}