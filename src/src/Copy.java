/**
 *
 * @author seand
 */

//imports java library elements used by the class
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.JTextPane;

public class Copy {
    public void copy(JTextPane text){
        
        String copiedText = text.getSelectedText(); //defines the text from the text area
        StringSelection stringSelection = new StringSelection(copiedText); //declares a string selection variable
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard(); //declares the clipboard area
        clipboard.setContents(stringSelection, null); //copies the text area contents into the clipboard
        
        
    }
    
}
