/**
 *
 * @author seand
 */

//imports java library elements used by the class
import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import javax.swing.undo.UndoManager;

import javax.swing.text.SimpleAttributeSet;

import java.io.File;

/*
 * JPanel extension enables typing and display on screen
 * ActionListener Implementation ensures button/combo box interaction & reaction
*/

public class TextDisplay extends JPanel implements ActionListener{ 
    
    //Attributes
    private JTextPane textBox; 
    private JButton saveButton; 
    private JButton importButton;
    private JComboBox fontTypeCombo;
    private JSpinner textSize;
    
    private JLabel characterCount;
    
    private boolean unsavedChanges = false;
    private boolean boldActive = false;
    private boolean italicActive = false;
    private boolean underlineActive = false;
    
    private UndoManager undoManager;
    
    private JButton copyButton;
    private JButton pasteButton;
    private JButton undoButton;
    private JButton redoButton;
    private JComboBox textTemplate;
    private JButton boldButton;
    private JButton italicButton;
    private JButton underlineButton;
    
    
    //method objects so the classes can be called from withing this class
    saveFile saveFile = new saveFile();
    ImportFile loadFile = new ImportFile();
    fontControl fontClass = new fontControl();
    MainWordProcessor main = new MainWordProcessor();
    Copy copyText = new Copy();
    Paste pasteText = new Paste();
    
    /* Need 4 buttons for copy/paste & Undo/Redo
    Need Template inserter combo box
    */
    
    //arrays for the text combo boxes to extract data from
    String[] fontItems = {"Times New Roman", "Arial", "Calibri"};
    String[] Templates = {"No Template", "Letter", "Newspaper", "Resume"};
    
    
    public TextDisplay(){ //when class TextDisplay called from main class, will go here  
        init();
        
    }
    
    public void init(){ //Contains Buttons, constructs' components
        
        textBox = new JTextPane(); //area text typed is displayed
        
        undoManager = new UndoManager();
        addUndoManager();
        
        characterCount = new JLabel("Characters: 0");
        
        // Adds automatic character counting to the initial document
        addCharacterCountListener();
        
        saveButton = new JButton ("Save File");  
        importButton = new JButton ("Open File");
        
        fontTypeCombo = new JComboBox(fontItems); 
        
        SpinnerModel Value = new SpinnerNumberModel(10, 10, 72, 2);
        textSize = new JSpinner(Value);
        textSize.addChangeListener(e -> applyTextStyle());
        
        copyButton = new JButton ("Copy");
        pasteButton = new JButton ("Paste");
        undoButton = new JButton ("Undo");
        redoButton = new JButton ("Redo");

        boldButton = new JButton("Bold");
        italicButton = new JButton("Italic");
        underlineButton = new JButton("Underline");
        
        textTemplate = new JComboBox(Templates);
       
        textBox.setBackground(Color.WHITE);
        textBox.setCaretColor(Color.BLACK);

        
        //Size & Layout
        
        setPreferredSize(new Dimension(817, 525)); 
        setLayout(null);
         
        //adds components to the Frame
        add (textBox);
        add (saveButton);
        add (importButton);
        add (fontTypeCombo);
        add (textSize);
        
        add(boldButton);
        add(italicButton);
        add(underlineButton);
        
        add (characterCount); 
        
        add (copyButton);
        add (pasteButton);
        add (undoButton);
        add (redoButton);
        
        add (textTemplate);
        
        
        //Sets the components' size and positions in the Displayed Frame  
        textBox.setBounds(10, 10, 650, 500);
        textTemplate.setBounds(670, 45, 140, 35); 
        textSize.setBounds(670, 95, 140, 35);

        fontTypeCombo.setBounds(670, 140, 140, 35);

        // Formatting buttons
        boldButton.setBounds(670, 185, 65, 35);
        italicButton.setBounds(740, 185, 70, 35);
        underlineButton.setBounds(670, 230, 140, 35);

        // File buttons
        importButton.setBounds(670, 275, 140, 35);
        saveButton.setBounds(670, 320, 140, 35);

        // Editing buttons
        copyButton.setBounds(670, 365, 70, 35);
        pasteButton.setBounds(740, 365, 70, 35);

        undoButton.setBounds(670, 410, 70, 35);
        redoButton.setBounds(740, 410, 70, 35);
        characterCount.setBounds(670, 455, 140, 49);
        
        //Sets the Action Listeners for this particular class
        saveButton.addActionListener(this);
        importButton.addActionListener(this);
        fontTypeCombo.addActionListener(this);
        boldButton.addActionListener(this);
        italicButton.addActionListener(this);
        underlineButton.addActionListener(this);
        
        copyButton.addActionListener(this);
        pasteButton.addActionListener(this);
        undoButton.addActionListener(this);
        redoButton.addActionListener(this);
        textTemplate.addActionListener(this);
    }
    
    //Method for the Action Listeners to enable component functionality
    public void actionPerformed(ActionEvent e){
        
        //if statements call the relevant classes. Will contain the method's source code actions once the classes have been created and programmed.
        if(e.getSource() == saveButton){
            
            boolean saved = saveFile.save(textBox);
            
            if (saved){
                unsavedChanges = false;
            }
        }
        
        if(e.getSource() == importButton){
            File loadedFile = loadFile.open(textBox);
            
            if(loadedFile != null){
                
                //Tells saveFile which file is open
                saveFile.setCurrentFile(loadedFile);
                
                //Adds character counter to newly loaded doc
                addCharacterCountListener();
                updateCharacterCount();
                
                undoManager.discardAllEdits();
                addUndoManager();

            }
        }
        
        if(e.getSource() == fontTypeCombo){
            applyTextStyle();
        }
        
        if(e.getSource() == boldButton){
            boldActive = !boldActive;
            applyTextStyle();
        }

        if(e.getSource() == italicButton){
            italicActive = !italicActive;
            applyTextStyle();
        }

        if(e.getSource() == underlineButton){
            underlineActive = !underlineActive;
            applyTextStyle();
        }
        
        
        if(e.getSource() == copyButton){
            copyText.copy(textBox);
        }
        
        if(e.getSource() == pasteButton){
            pasteText.paste(textBox);
        }
        
        if(e.getSource() == undoButton){
            
            if(undoManager.canUndo()){
                undoManager.undo();
            }
        }
        
        if(e.getSource() == redoButton){
            
            if(undoManager.canRedo()){
                undoManager.redo();
            }
        }
        
        
        if(e.getSource() == textTemplate){
            String selectedTemplate =
                textTemplate.getSelectedItem().toString();
             
            if(selectedTemplate.equals("Letter")){
                 
                textBox.setText(
                    "Dear Sir/Madam,\n\n"
                    + "[Enter your letter here]\n\n"
                    + "Yours Sincerely,\n"
                    + "[Your Name]"
                );
            }

            if(selectedTemplate.equals("Newspaper")){

                textBox.setText(
                    "HEADLINE\n\n"
                    + "By [Author Name]\n\n"
                    + "[Opening paragraph]\n\n"
                    + "[Main article content]"
                );
            }

            if(selectedTemplate.equals("Resume")){

                textBox.setText(
                    "FULL NAME\n\n"
                    + "Contact Information\n"
                    + "[Email Address]\n"
                    + "[Phone Number]\n\n"
                    + "Profile\n"
                    + "[Professional profile]\n\n"
                    + "Experience\n"
                    + "[Work experience]\n\n"
                    + "Education\n"
                    + "[Education details]"
                );
            }

            if(selectedTemplate.equals("No Template")){

            // Does not insert anything
            }
        }
    }
    
    //Adds a listener so the character counter auto updates when text changes.
    private void addCharacterCountListener() {

        textBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCharacterCount();
                unsavedChanges = true;
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCharacterCount();
                unsavedChanges = true;
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCharacterCount();
                unsavedChanges = true;
            }
        });
    }   
    
    private void applyTextStyle() {
        String fontFamily =
                fontTypeCombo.getSelectedItem().toString();

        int size = (int) textSize.getValue();

        SimpleAttributeSet attributes =
            fontClass.createStyle(
                    fontFamily,
                    size,
                    boldActive,
                    italicActive,
                    underlineActive
            );

        int start = textBox.getSelectionStart();
        int end = textBox.getSelectionEnd();

        if (start != end) {

            textBox.getStyledDocument().setCharacterAttributes(
                    start, end - start, attributes,
                    false
            );
        }
    }
    
    //Calculates current character count & displays on GUI
    private void updateCharacterCount(){
        String text = textBox.getText();
        characterCount.setText("Characters: " + text.length());
    }
    
    private void addUndoManager(){
        textBox.getDocument().addUndoableEditListener(
        e -> undoManager.addEdit(e.getEdit())
        );
    }
    
    // Returns value of data in text box
    public JTextPane getText(){
        return textBox;
    }
    
    public boolean hasUnsavedChanges(){
        return unsavedChanges;
    }
    
}