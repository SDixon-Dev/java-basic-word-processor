# Basic Word Processor (Java)

A Java desktop application demonstrating **object-oriented programming (OOP)**, event-driven programming and graphical user interface development through the creation of a basic word processor.

The application is built using **Java Swing** and allows users to create, edit, format, save and load Rich Text Format (`.rtf`) documents through a graphical interface.

The project demonstrates class separation, encapsulation, object interaction, file handling, event listeners, document manipulation and GUI development using Java Swing.

---

## Overview

The project was originally developed as part of an Object-Oriented Programming university assignment, with the goal of creating a simple word processor similar to applications such as Microsoft Notepad or macOS TextEdit.

The application provides a text editor where users can create and modify documents while accessing common word-processing functionality through a Swing-based graphical interface.

Users can:

- Write and edit text.
- Save documents as RTF files.
- Open existing RTF documents.
- Continue editing and saving an opened document.
- Format selected text.
- Copy and paste text.
- Undo and redo changes.
- Insert predefined document templates.
- View a live character count.
- Receive warnings before overwriting files or exiting with unsaved changes.

The application separates major functionality across several Java classes to keep the GUI, file management, clipboard operations and text formatting organised.

---

## Features

- Graphical user interface built using Java Swing.
- Create and edit text using a `JTextPane`.
- Save documents in Rich Text Format (`.rtf`).
- Open and edit existing RTF documents.
- Track the currently open file for subsequent saves.
- Warn before overwriting an existing file.
- Warn before exiting with unsaved changes.
- Automatically update the document character count.
- Copy selected text to the system clipboard.
- Paste clipboard text at the current cursor position.
- Undo and redo document changes.
- Select different font families.
- Change font size.
- Apply bold formatting.
- Apply italic formatting.
- Apply underline formatting.
- Apply formatting to selected text.
- Insert predefined document templates.

---

## Object-Oriented Design

The application separates different areas of functionality into dedicated classes rather than placing all program logic inside the graphical interface.

`MainWordProcessor` acts as the application's entry point and manages the main window and exit behaviour.

`TextDisplay` acts as the primary GUI controller. It creates the Swing components, responds to user actions and coordinates the supporting classes.

File operations are separated between `ImportFile` and `SaveFile`, while clipboard functionality is handled by the `Copy` and `Paste` classes.

Text formatting is supported through `FontControl`, which creates formatting attributes that can be applied to selected text in the editor.

This structure helps separate responsibilities and reduces the amount of unrelated functionality contained within individual classes.

---

## Project Enhancements

The original coursework implementation contained the foundation of the word processor, but several features were subsequently repaired, completed and expanded while preparing the project for this portfolio.

### Improved RTF File Saving

The save functionality was revised to correctly write documents using Java's `RTFEditorKit`.

The application now tracks the file currently being edited. After opening or initially saving a document, subsequent saves can update the same file rather than requiring the user to repeatedly select its location.

Before an existing file is replaced, the application displays an overwrite confirmation dialog.

The file chooser also automatically adds the `.rtf` extension when required.

---

### Improved File Loading

The file-loading functionality was updated to create a new `StyledDocument` when an RTF file is opened.

The selected file is read using `RTFEditorKit` and displayed within the application's `JTextPane`.

Once loaded, the application records the file as the current document so subsequent save operations can update it directly.

---

### Unsaved Changes Detection

The application tracks whether the current document has been modified.

If the user attempts to close the application while unsaved changes are present, a Swing confirmation dialog warns that those changes will be lost.

The user can either:

1. Confirm the exit.
2. Cancel the exit and return to the document.

Successfully saving the document resets its unsaved-change state.

---

### Automatic Character Counter

The original manually activated character counter was replaced with an automatic counter using a Swing `DocumentListener`.

The displayed character total now updates whenever text is:

- Added.
- Removed.
- Pasted.
- Loaded or otherwise changed.

This provides immediate feedback without requiring the user to manually request a character count.

---

### Document Templates

A template selector was implemented to automatically populate the editor with predefined document structures.

Available templates include:

- Letter
- Newspaper
- Resume

For example, the Letter template automatically creates a basic structure beginning with:

```text
Dear Sir/Madam,

[Enter your letter here]

Yours Sincerely,
[Your Name]
```

The user can then replace the placeholder text with their own content.

---

### Copy and Paste

Clipboard functionality allows selected text to be copied to the operating system clipboard.

The paste functionality retrieves text from the clipboard and inserts it at the current cursor position.

If text is already selected when Paste is used, the selected content is replaced by the clipboard contents.

---

### Undo and Redo

Undo and redo functionality was implemented using Java Swing's `UndoManager`.

Users can reverse recent document edits using the **Undo** button and restore undone changes using **Redo**.

When a different document is opened, the previous document's undo history is cleared so that undo and redo operations relate only to the currently displayed document.

---

### Improved Text Formatting

The original formatting system was redesigned to provide controls more similar to a conventional word processor.

The font dropdown is used exclusively to select the font family, including:

- Times New Roman
- Arial
- Calibri

Font size is controlled independently using a spinner.

Dedicated controls are provided for:

- **Bold**
- *Italic*
- Underline

Formatting is applied to selected text using Swing's `StyledDocument` and `SimpleAttributeSet` functionality, allowing different parts of the same document to use different styles.

---

## Technologies

- Java
- Java Swing
- Java AWT
- Rich Text Format (RTF)
- NetBeans IDE
- Object-Oriented Programming (OOP)
- Event-Driven Programming

---

## Files

| File | Description |
|------|-------------|
| `MainWordProcessor.java` | Application entry point, main JFrame creation and unsaved-changes exit handling |
| `TextDisplay.java` | Main Swing GUI, event handling, character counting, templates, formatting and undo/redo coordination |
| `SaveFile.java` | RTF file saving, current-file tracking and overwrite confirmation |
| `ImportFile.java` | Opens and loads RTF documents into the text editor |
| `Copy.java` | Copies selected text to the system clipboard |
| `Paste.java` | Retrieves clipboard text and inserts it into the editor |
| `FontControl.java` | Creates font and text-style attributes for selected text |

---

## Example Interface

```text
---------------------------------------------------------------
|                                                             |
|                                                             |
|                                                             |
|                    Document Editor                          |
|                                                             |
|                                                             |
|                                                             |
|                                      [No Template       ▼]   |
|                                      [Font Size         ↕]   |
|                                      [Times New Roman   ▼]   |
|                                      [Bold] [Italic]         |
|                                      [    Underline     ]    |
|                                      [    Open File     ]    |
|                                      [    Save File     ]    |
|                                      [Copy]   [Paste]        |
|                                      [Undo]   [Redo]         |
|                                      Characters: 0           |
---------------------------------------------------------------
```

---

## Running the Project

### Prerequisites

To run the application, you will need:

- Java Development Kit (JDK)
- Apache NetBeans or another Java-compatible IDE

### Running in NetBeans

1. Clone or download the repository.
2. Open the project in Apache NetBeans.
3. Ensure a compatible JDK is configured for the project.
4. Build the project.
5. Run `MainWordProcessor.java`.

The word processor GUI will open and allow a new document to be created or an existing RTF document to be loaded.

---

## Future Improvements

Although the application now provides the required core word-processing functionality, several features could be introduced in future development.

Potential improvements include:

- Add **Save As** functionality for creating a new copy of the current document.
- Add keyboard shortcuts such as `Ctrl+C`, `Ctrl+V`, `Ctrl+Z`, `Ctrl+Y` and `Ctrl+S`.
- Add a **New Document** option.
- Add find and replace functionality.
- Add spell checking.
- Add text alignment controls.
- Add text colour and highlighting options.
- Add additional font families and formatting options.
- Display the currently open filename in the application title.
- Add confirmation before replacing existing text with a document template.
- Add menus and a conventional word-processor toolbar.
- Improve the GUI using Swing layout managers rather than fixed component positioning.
- Add automated tests for file handling and document functionality.
- Expand file-format support beyond RTF.

---

## Learning Outcomes

This project demonstrates:

- Object-oriented software organisation using multiple cooperating classes.
- Development of desktop graphical interfaces using Java Swing.
- Event-driven programming using `ActionListener`, `DocumentListener` and window events.
- Reading and writing Rich Text Format documents.
- File handling using Java input and output streams.
- File selection using `JFileChooser`.
- User feedback and confirmation using `JOptionPane`.
- Clipboard interaction using Java AWT.
- Styled text manipulation using `StyledDocument` and `SimpleAttributeSet`.
- Undo and redo functionality using `UndoManager`.
- Tracking application and document state.
- Separating GUI, file, clipboard and formatting responsibilities.
- Refactoring and extending an existing Java application.

---

## Author

Sean Dixon
