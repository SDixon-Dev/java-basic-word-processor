/**
 * Main GUI panel for the Basic Word Processor.
 *
 * Creates and arranges the editor controls, handles user interaction,
 * tracks document changes, manages undo/redo, applies formatting,
 * and coordinates file, clipboard and template functionality.
 *
 * @author seand
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.SpinnerNumberModel;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.text.SimpleAttributeSet;

import javax.swing.undo.UndoManager;

public class TextDisplay extends JPanel implements ActionListener {

    // Main editor components
    private JTextPane textBox;
    private JLabel characterCount;

    // File controls
    private JButton saveButton;
    private JButton importButton;

    // Formatting controls
    private JComboBox<String> fontTypeCombo;
    private JSpinner textSize;
    private JButton boldButton;
    private JButton italicButton;
    private JButton underlineButton;

    // Editing controls
    private JButton copyButton;
    private JButton pasteButton;
    private JButton undoButton;
    private JButton redoButton;

    // Template control
    private JComboBox<String> textTemplate;

    // Document state
    private boolean unsavedChanges = false;
    private boolean boldActive = false;
    private boolean italicActive = false;
    private boolean underlineActive = false;

    private UndoManager undoManager;

    // Supporting functionality
    private final SaveFile saveFile = new SaveFile();
    private final ImportFile loadFile = new ImportFile();
    private final FontControl fontClass = new FontControl();
    private final Copy copyText = new Copy();
    private final Paste pasteText = new Paste();

    // Available font and template options
    private final String[] fontItems = {
        "Times New Roman",
        "Arial",
        "Calibri"
    };

    private final String[] templates = {
        "No Template",
        "Letter",
        "Newspaper",
        "Resume"
    };

    public TextDisplay() {
        initialiseComponents();
        configureLayout();
        registerListeners();
    }

    //Creates Swing components used by the word processor.
    private void initialiseComponents() {

        textBox = new JTextPane();
        textBox.setBackground(Color.WHITE);
        textBox.setCaretColor(Color.BLACK);

        characterCount = new JLabel("Characters: 0");

        saveButton = new JButton("Save File");
        importButton = new JButton("Open File");

        fontTypeCombo = new JComboBox<>(fontItems);

        textSize = new JSpinner(
                new SpinnerNumberModel(10, 10, 72, 2)
        );

        boldButton = new JButton("Bold");
        italicButton = new JButton("Italic");
        underlineButton = new JButton("Underline");

        copyButton = new JButton("Copy");
        pasteButton = new JButton("Paste");
        undoButton = new JButton("Undo");
        redoButton = new JButton("Redo");

        textTemplate = new JComboBox<>(templates);

        undoManager = new UndoManager();

        addUndoManager();
        addCharacterCountListener();
    }

    //Adds & defines components' positions to the panel.
    private void configureLayout() {

        setPreferredSize(new Dimension(817, 525));
        setLayout(null);

        add(textBox);
        add(textTemplate);
        add(textSize);
        add(fontTypeCombo);

        add(boldButton);
        add(italicButton);
        add(underlineButton);

        add(importButton);
        add(saveButton);

        add(copyButton);
        add(pasteButton);
        add(undoButton);
        add(redoButton);

        add(characterCount);

        textBox.setBounds(10, 10, 650, 500);

        textTemplate.setBounds(670, 45, 140, 35);
        textSize.setBounds(670, 95, 140, 35);
        fontTypeCombo.setBounds(670, 140, 140, 35);

        boldButton.setBounds(670, 185, 65, 35);
        italicButton.setBounds(740, 185, 70, 35);
        underlineButton.setBounds(670, 230, 140, 35);

        importButton.setBounds(670, 275, 140, 35);
        saveButton.setBounds(670, 320, 140, 35);

        copyButton.setBounds(670, 365, 70, 35);
        pasteButton.setBounds(740, 365, 70, 35);

        undoButton.setBounds(670, 410, 70, 35);
        redoButton.setBounds(740, 410, 70, 35);

        characterCount.setBounds(670, 455, 140, 49);
    }

    //Registers listeners for all interactive controls.
    private void registerListeners() {

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

        textSize.addChangeListener(e -> applyTextStyle());
    }

    //Responses to button and combo-box actions.
    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == saveButton) {
            saveDocument();

        } else if (source == importButton) {
            openDocument();

        } else if (source == fontTypeCombo) {
            applyTextStyle();

        } else if (source == boldButton) {
            boldActive = !boldActive;
            applyTextStyle();

        } else if (source == italicButton) {
            italicActive = !italicActive;
            applyTextStyle();

        } else if (source == underlineButton) {
            underlineActive = !underlineActive;
            applyTextStyle();

        } else if (source == copyButton) {
            copyText.copy(textBox);

        } else if (source == pasteButton) {
            pasteText.paste(textBox);

        } else if (source == undoButton) {
            undo();

        } else if (source == redoButton) {
            redo();

        } else if (source == textTemplate) {
            insertTemplate();
        }
    }

    // Saves the current document.
    private void saveDocument() {

        boolean saved = saveFile.save(textBox);

        if (saved) {
            unsavedChanges = false;
        }
    }

   
    //Opens an RTF document and resets document-specific listeners.
    private void openDocument() {

        File loadedFile = loadFile.open(textBox);

        if (loadedFile == null) {
            return;
        }

        saveFile.setCurrentFile(loadedFile);

        addCharacterCountListener();
        updateCharacterCount();

        undoManager.discardAllEdits();
        addUndoManager();

        // A freshly loaded file has no unsaved user changes.
        unsavedChanges = false;
    }

    /**
     * Applies the selected font family, size and formatting
     * to the currently selected text.
     */
    private void applyTextStyle() {

        int start = textBox.getSelectionStart();
        int end = textBox.getSelectionEnd();

        if (start == end) {
            return;
        }

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

        textBox.getStyledDocument().setCharacterAttributes(
                start,
                end - start,
                attributes,
                false
        );
    }

    //Inserts the currently selected document template.
    private void insertTemplate() {

        String selectedTemplate =
                textTemplate.getSelectedItem().toString();

        switch (selectedTemplate) {

            case "Letter":
                textBox.setText(
                        "Dear Sir/Madam,\n\n"
                        + "[Enter your letter here]\n\n"
                        + "Yours Sincerely,\n"
                        + "[Your Name]"
                );
                break;

            case "Newspaper":
                textBox.setText(
                        "HEADLINE\n\n"
                        + "By [Author Name]\n\n"
                        + "[Opening paragraph]\n\n"
                        + "[Main article content]"
                );
                break;

            case "Resume":
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
                break;

            default:
                // No Template selected, so no text is inserted.
                break;
        }
    }

    //Undoes the most recent editable action when possible.
    private void undo() {

        if (undoManager.canUndo()) {
            undoManager.undo();
        }
    }

    //Redoes the most recently undone action when possible.
    private void redo() {

        if (undoManager.canRedo()) {
            undoManager.redo();
        }
    }

    /**
     * Adds a listener that updates the character count and
     * records when the document has been modified.
     */
    private void addCharacterCountListener() {

        textBox.getDocument().addDocumentListener(
                new DocumentListener() {

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        documentChanged();
                    }

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        documentChanged();
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        documentChanged();
                    }
                }
        );
    }
    
    //Handles actions common to all document changes.
    private void documentChanged() {
        updateCharacterCount();
        unsavedChanges = true;
    }

   
    //Updates the character count displayed in the GUI.
    private void updateCharacterCount() {
        characterCount.setText(
                "Characters: " + textBox.getText().length()
        );
    }

   
    // Connects the current document to the UndoManager.
    private void addUndoManager() {

        textBox.getDocument().addUndoableEditListener(
                e -> undoManager.addEdit(e.getEdit())
        );
    }

    /**
     * Returns whether the current document contains unsaved changes.
     *
     * @return true when unsaved changes exist
     */
    public boolean hasUnsavedChanges() {
        return unsavedChanges;
    }
}
