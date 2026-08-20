/**
 *
 * @author seand
 */


//imports java library elements used by the class
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JTextPane;

public class Paste {
    
    public void paste(JTextPane text){
        
        String result = "";
        
        // Gets system clipboard
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        
        // Gets current clipboard contents
        Transferable clipboardContents = clipboard.getContents(null);
        
        // Checks if clipboard has text
        boolean hasText = 
                (clipboardContents != null) 
                && clipboardContents.isDataFlavorSupported(
                        DataFlavor.stringFlavor);
        
        if(hasText){
            
            try{   
                result = (String)clipboardContents.getTransferData(
                        DataFlavor.stringFlavor);
                
                //Inserts clipboard text at cursor position
                text.replaceSelection(result);
            
            } catch(UnsupportedFlavorException | IOException ex){
                
                ex.printStackTrace();
                
            }
            
        }
    }
    
}
