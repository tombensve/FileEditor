package se.natusoft.tools.fileeditor;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of the TextBuffer interface.
 * <p>
 * Files can be loaded into the buffer and the buffer saved to file again.
 * Individual lines in the buffer can be fetched, replaced, removed, and
 * inserted.
 * <p>
 * Attatch a TextBuffer to a TextFileEditor to do more advanced editing on it.
 * Each TextFileEditor also has a default TextFileBuffer that is used if no
 * other is provided.
 *
 * @see se.natusoft.tools.fileeditor.TextFileEditor
 */
@Project(
    name="FileEditor",
    codeVersion="2.0",
    description="Provides a programmable only text file editor. No gui is available. It supplies" +
                "most of the features you expect from an editor including selections."
)
@Copyright(year="2009", holder="Natusoft AB", rights="All rights reserved.")
@License(
    type="Apache",
    version="2.0",
    description="Apache Software License",
    source=Source.OPEN,
    text={
        "Licensed under the Apache License, Version 2.0 (the 'License');",
        "you may not use this file except in compliance with the License.",
        "You may obtain a copy of the License at",
        "",
        "  http://www.apache.org/licenses/LICENSE-2.0",
        "",
        "Unless required by applicable law or agreed to in writing, software",
        "distributed under the License is distributed on an 'AS IS' BASIS,",
        "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.",
        "See the License for the specific language governing permissions and",
        "limitations under the License."
    }
)
@Author(
    name="Tommy Svensson",
    changes={
        @Change(when="2008-12-03", description="Created"),
        @Change(when="2009-10-31", description="Cosmetics")
    }
)
public class TextFileBuffer implements TextBuffer {
    
    //
    // Private Members
    //
    
    /** Holds the path of the loaded file. */
    private File loadedFile = null;
    
    /** Holds the buffer text. */
    private List<String> text = new ArrayList();
    
    /** Set to true to make buffer read only. */
    private boolean readOnly = false;

    //
    // Constructors
    //
    
    /**
     * Creates a new instance of TextFileBuffer.
     */
    public TextFileBuffer() {
        
    }
    
    //
    // Methods
    //
    
    /**
     * If true makes the buffer read only.
     *
     * @param readOnly Set to true to make buffer read only. Defaults to false.
     */
    public void setReadOnly(boolean readOnly) throws UnsupportedOperationException {
        this.readOnly = readOnly;
    }

    /**
     * Loads a text file into the buffer.
     *
     * @param file The file to load.
     */
    public void load(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        try {
            this.loadedFile = file;
            this.text = new ArrayList();
            String line = reader.readLine();
            while (line != null) {
                this.text.add(line);
                line = reader.readLine();
            }
        }
        finally {
            reader.close();
        }        
    }
    
    /**
     * Loads a text file into the buffer.
     *
     * @param path The path to the file to load.
     */
    public void load(String path) throws IOException {
        load(new File(path));
    }
    
    /**
     * Saves the buffer back to disk again.
     */
    public void save() throws IOException {
        saveAs(this.loadedFile);        
    }
    
    /**
     * Saves the buffer to another file.
     *
     * @param file The file to save to.
     */
    public void saveAs(File file) throws IOException {
        //System.out.println("##### saveAs(File): this.text=" + this.text);
        //System.out.println("##### saveAs(File): file=" + file);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file)));

        writeBufferAndClose(writer);
    }
    
    /**
     * Saves the buffer to another file.
     *
     * @param path The path to the file to save to.
     */
    public void saveAs(String path) throws IOException {
        saveAs(new File(path));
    }
    
    /**
     * Writes the content of the buffer to the specified writer.
     * 
     * @param writer The writer to write to.
     * @throws java.io.IOException
     */
    private void writeBufferAndClose(PrintWriter writer) throws IOException {
        try {
            for (String line : this.text) {
                writer.println(line);
            }
        }
        finally {
            writer.close();
        }
    }

    /**
     * Clears the buffer.
     */
    public void clear() {
        if (!this.readOnly) {
            this.text = new ArrayList();
        }
    }
    
    /**
     * Returns the number of lines in the buffer.
     */
    public int getSize() {
        return this.text.size();
    }
    
    /**
     * Gets a specific line from the buffer.
     *
     * @param line The line to get.
     */
    public String getLine(int line) {
        return this.text.get(line);
    }
    
    /**
     * Replaces a specific line in the buffer.
     *
     * @param line The line to replace.
     * @param text The new text for the line.
     */
    public void setLine(int line, String text) {
        if (!this.readOnly) {
            this.text.set(line, text);
        }
    }
    
    /**
     * Removes the specified line.
     *
     * @param line The line to remove.
     */
    public void removeLine(int line) {
        if (!this.readOnly) {
            this.text.remove(line);
        }
    }
    
    /**
     * Inserts a new line at the specified position.
     *
     * @param line the poistion to insert at.
     * @param text The text to insert.
     */
    public void insertLine(int line, String text) {
        if (!this.readOnly) {
            this.text.add(line, text);
        }
    }

    /**
     * Appends text to the specified line.
     * 
     * @param line The line to add text to.
     * @param text The text to add.
     */
    public void appendToLine(int line, String text) {
        if (!this.readOnly) {
            this.text.set(line, this.text.get(line) + text);
        }
    }

    /**
     * Adds a line of text to the end of the buffer.
     *
     * @param text The text to add.
     */
    public void addLine(String text) {
        if (!this.readOnly) {
            this.text.add(text);
        }
    }

    /**
     * Returns a new instance of the buffer.
     */
    public TextBuffer newInstance() {
        TextFileBuffer tb = new TextFileBuffer();
        tb.loadedFile = this.loadedFile;
        return tb;
    }

    /**
     * Returns the buffer as a String.
     */
    @Override
    public String toString() {
        StringWriter stringWriter = new StringWriter();
        try {
            PrintWriter printWriter = new PrintWriter(stringWriter);
            writeBufferAndClose(printWriter);
        }
        catch (IOException ioe) {
            // Do nothing.
        }
        finally {
            try {
                stringWriter.close();
            }
            catch (IOException ioe2) {
                // Do nothing.
            }
        }
        return stringWriter.toString();
    }
}
