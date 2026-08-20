/**
 *
 * @author seand
 */
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class fontControl {

    public SimpleAttributeSet createStyle(
            String fontFamily,
            int size,
            boolean bold,
            boolean italic,
            boolean underline) {

        SimpleAttributeSet attributes = new SimpleAttributeSet();

        StyleConstants.setFontFamily(attributes, fontFamily);
        StyleConstants.setFontSize(attributes, size);

        StyleConstants.setBold(attributes, bold);
        StyleConstants.setItalic(attributes, italic);
        StyleConstants.setUnderline(attributes, underline);

        return attributes;
    }
}
