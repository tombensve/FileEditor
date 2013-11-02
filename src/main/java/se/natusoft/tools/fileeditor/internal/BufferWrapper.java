package se.natusoft.tools.fileeditor.internal;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;
import se.natusoft.tools.fileeditor.Line;
import se.natusoft.tools.fileeditor.Position;
import se.natusoft.tools.fileeditor.TextBuffer;

import java.io.IOException;

/**
 * This wraps a TextBuffer and works with internal editor objects.
 * <p>
 * Note that the TextBuffer interface works with base 0 indexes while
 * this wrapper works with base 1 indexes as does the TextFileEditor!
 *
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
        @Change(when="2009-11-13", description="Created")
    }
)
public class BufferWrapper {
    
    //
    // Private Members
    //
    
    /** The TextBuffer to wrap. */
    private TextBuffer buffer = null;
    
        
    //
    // Constructors
    //
    
    /**
     * Creates a new instance of Position.
     */
    public BufferWrapper(TextBuffer buffer) {
        this.buffer = buffer;
    }
    
    
    //
    // Methods
    //

    /**
     * Loads a text file into the buffer.
     *
     * @param path The path to the file to load.
     */
    public void load(String path) throws IOException {
        this.buffer.load(path);
    }

    /**
     * Saves the buffer back to disk again.
     */
    public void save() throws IOException {
        this.buffer.save();
    }

    /**
     * Saves the buffer to another file.
     *
     * @param path The path to the file to save to.
     */
    public void saveAs(String path) throws IOException {
        this.buffer.saveAs(path);
    }

    /**
     * Clears the buffer.
     */
    public void clear() {
        this.buffer.clear();
    }

    /**
     * Returns the number of lines in the buffer.
     */
    public int getSize() {
        return this.buffer.getSize();
    }

    /**
     * Gets a specific line from the buffer.
     *
     * @param lineNo The line to get.
     */
    public Line getLine(int lineNo) {
        return getLine(new TextPos(lineNo));
    }

    /**
     * Gets a specific line from the buffer.
     *
     * @param lineNo The line to get.
     */
    public Line getLine(TextPos lineNo) {
        return new Line(this.buffer.getLine(lineNo.baseZeroValue()));
    }

    /**
     * Replaces a specific line in the buffer.
     *
     * @param lineNo The line to replace.
     * @param text The new text for the line.
     */
    public void setLine(int lineNo, String text) {
        setLine(new TextPos(lineNo), text);
    }

    /**
     * Replaces a specific line in the buffer.
     *
     * @param lineNo The line to replace.
     * @param text The new text for the line.
     */
    public void setLine(TextPos lineNo, String text) {
        this.buffer.setLine(lineNo.baseZeroValue(), text);
    }

    /**
     * Replaces a specific line in the buffer.
     *
     * @param lineNo The line to replace.
     * @param line The new text for the line.
     */
    public void setLine(TextPos lineNo, Line line) {
        this.buffer.setLine(lineNo.baseZeroValue(), line.toString());
    }

    /**
     * Removes the specified line.
     *
     * @param lineNo The line to remove.
     */
    public void removeLine(int lineNo) {
        removeLine(new TextPos(lineNo));
    }

    /**
     * Removes the specified line.
     *
     * @param lineNo The line to remove.
     */
    public void removeLine(TextPos lineNo) {
        this.buffer.removeLine(lineNo.baseZeroValue());
    }

    /**
     * Inserts a new line at the specified position.
     *
     * @param lineNo the poistion to insert at.
     * @param text The text to insert.
     */
    public void insertLine(int lineNo, String text) {
        insertLine(new TextPos(lineNo), text);
    }

    /**
     * Inserts a new line at the specified position.
     *
     * @param lineNo the poistion to insert at.
     * @param text The text to insert.
     */
    public void insertLine(TextPos lineNo, String text) {
        this.buffer.insertLine(lineNo.baseZeroValue(), text);
    }

    /**
     * Inserts a new line at the specified position.
     *
     * @param lineNo the poistion to insert at.
     * @param line The line to insert.
     */
    public void insertLine(TextPos lineNo, Line line) {
        this.buffer.insertLine(lineNo.baseZeroValue(), line.toString());
    }

    /**
     * Adds a line of text to the end of the buffer.
     *
     * @param text The text to add.
     */
    public void addLine(String text) {
        this.buffer.addLine(text);
    }

    /**
     * Adds a line to the end of the buffer.
     *
     * @param line The line to add.
     */
    public void addLine(Line line) {
        this.buffer.addLine(line.toString());
    }

    /**
     * Returns true if the specified column on the specified line is outside of the limit for that line.
     * 
     * @param line The line to check.
     * @param column The column to verify.
     */
    public boolean columnWithinLimit(TextPos line, TextPos column) {
        column.adjustLimits(getLastColumnNumber(line).value());
        return column.wasValid();
    }

    /**
     * Returns the column limit for the specified line.
     * 
     * @param line The line to get the column limit for.
     */
    public int getLastColumn(int line) {
        return getLine(line).length();
    }

    /**
     * Returns the column limit for the specified line.
     *
     * @param line The line to get the column limit for.
     */
    public TextPos getLastColumnNumber(TextPos line) {
        return new TextPos(getLine(line).length());
    }

    /**
     * Returns true if the specified line is outside the limit for this buffer.
     *
     * @param line The line to verify.
     */
    public boolean lineWithinLimit(TextPos line) {
        line.adjustLimits(getLastLineNumber().value());
        return line.wasValid();
    }

    /**
     * Returns the line number limit for the buffer.
     */
    public int getLastLine() {
        return getSize();
    }

    /**
     * Returns the line number limit for the buffer.
     */
    public TextPos getLastLineNumber() {
        return new TextPos(getSize());
    }

    /**
     * Adjusts the position to be within limits of the buffer and line in buffer.
     *
     * @param position The Position object to adjust.
     */
    public void adjustPosition(Position position) {
        position.adjustLimits(getSize(), getLine(position.getLine()).length());
    }

    /**
     * Returns a new instance of the buffer.
     * <p>
     * Some editor operations work by creating a new buffer. This method
     * allows those operations to create a buffer of the same type.
     * <p>
     * Please note however that any saved reference to the loaded file must be
     * copied to the new instance before it is returned.
     */
    public BufferWrapper newInstance() {
        return new BufferWrapper(this.buffer.newInstance());
    }

    /**
     * Returns the wrapped buffer.
     */
    public TextBuffer getOriginalBuffer() {
        return this.buffer;
    }
}
