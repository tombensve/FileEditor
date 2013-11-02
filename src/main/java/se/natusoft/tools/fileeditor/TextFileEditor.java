package se.natusoft.tools.fileeditor;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;
import se.natusoft.tools.fileeditor.internal.BufferWrapper;
import se.natusoft.tools.fileeditor.internal.TextPos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a text file editor that as the name suggests, edits text files.
 * It is only a programatic editor that allows you to load a file, search,
 * replace, delete, and insert stuff in the file, and then save it again
 * or save to a new file. The show() method are a utility for debuging so
 * that you can look at the result without saving it.
 * <p>
 * Please note that line and column numbers start at 1 while TextBuffer
 * lines start at 0!
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
        @Change(when="2009-10-31", description="Added findLast() + insertAbove(), a few bugfixes with insert (didn't insert below) and some cosmetics."),
        @Change(when="2009-11-09", description="Added a few find and match methods using regexps.")
    }
)
public class TextFileEditor {

    /** Means end of file in the buffer held by the editor. */
    public static final Character END_OF_FILE_CHAR = Character.MAX_VALUE;

    //
    // Private Members
    //
    
    /** The current position. */
    private Position position = new Position(TextPos.FIRST, TextPos.FIRST);
    
    /** The current selection when non null. */
    private Selection selection = null;
    
    /** The buffer lines. */
    private BufferWrapper buffer = null;

    /** Enables/disables loading and saving of editor content. */
    private boolean allowLoadSave = true;
        
    //
    // Constructors
    //
    
    /**
     * Creates a new instance of TextFileEditor.
     */
    public TextFileEditor() {
        this.buffer = new BufferWrapper(new TextFileBuffer());
    }

    //
    // Buffer Methods
    //

    /**
     * Change the allowLoadSave state.
     *
     * @param allowLoadSave true or false.
     */
    public void setAllowLoadSave(boolean allowLoadSave) {
        this.allowLoadSave = allowLoadSave;
    }

    /**
     * Replaces the buffer to edit.
     */
    public void setTextBuffer(TextBuffer textBuffer) {
        this.buffer = new BufferWrapper(textBuffer);
    }

    /**
     * Returns the currently edited buffer.
     */
    public TextBuffer getTextBuffer() {
        return this.buffer.getOriginalBuffer();
    }

    //
    // Loading and Saving Methods
    //

    /**
     * Loads a file into the buffer.
     *
     * @param file The file to load.
     */
    public void load(File file) throws IOException {
        if (!this.allowLoadSave) throw new IllegalStateException("Load operations is not permitted!");
        this.buffer.load(file.getAbsolutePath());
    }

    /**
     * Loads a file into the buffer.
     *
     * @param path The path to the file to load.
     */
    public void load(String path) throws IOException {
        if (!this.allowLoadSave) throw new IllegalStateException("Load operations is not permitted!");
        this.buffer.load(path);
    }

    /**
     * Saves the current buffer.
     */
    public void save() throws IOException {
        if (!this.allowLoadSave) throw new IllegalStateException("Save operations is not permitted!");
        this.buffer.save();
    }

    /**
     * Saves the current buffer to a new file.
     *
     * @param file The file to save to.
     */
    public void saveAs(File file) throws IOException {
        if (!this.allowLoadSave) throw new IllegalStateException("Save operations is not permitted!");
        this.buffer.saveAs(file.getAbsolutePath());
    }

    /**
     * Saves the current buffer to a new file.
     *
     * @param path The path to the file to save to.
     */
    public void saveAs(String path) throws IOException {
        if (!this.allowLoadSave) throw new IllegalStateException("Save operations is not permitted!");
        this.buffer.saveAs(path);
    }

    //
    // Support methods
    //

    /**
     * Adjusts the position to be within limits of the buffer and returns true
     * if anything was adjusted.
     */
    private boolean adjustPosition() {
        boolean adjusted = false;
        if (this.buffer == null) {
            this.position = new Position(1,1);
            adjusted = true;
        }
        else {
            if (this.position.getLine() > this.buffer.getLastLine()) {
                this.position.setLine(this.buffer.getLastLine());
                adjusted = true;
            }
            else if (this.position.getLineNumber().lessThan(TextPos.FIRST)) {
                this.position.setLine(TextPos.FIRST);
                adjusted = true;
            }

            String line = getLine(this.position.getLine());
            if (this.position.getColumn() > line.length()) {
                this.position.setColumn(line.length());
                adjusted = true;
            }
            else if (this.position.getColumnNumber().lessThan(TextPos.FIRST)) {
                this.position.setColumn(TextPos.FIRST);
                adjusted = true;
            }
        }
        return adjusted;
    }

    //
    // Getting information methods
    //

    /**
     * Returns the specified line.
     *
     * @param line The line to get.
     */
    public String getLine(int line) {
        return this.buffer.getLine(line).toString();
    }

    /**
     * Returns the current line.
     */
    public String getLine() {
        return getLineAsLine().toString();
    }

    /**
     * Returns the current line.
     */
    public Line getLineAsLine() {
        return this.buffer.getLine(this.position.getLine());
    }

    /**
     * Replaces a line with a new text.
     *
     * @param lineNo The line number to replace.
     * @param line The new line text.
     */
    public void setLine(int lineNo, String line) {
        this.buffer.setLine(lineNo, line);
    }

    /**
     * Replaces the current line with a new text.
     *
     * @param line The new text to set for the line.
     */
    public void setLine(String line) {
        this.buffer.setLine(this.position.getLine(), line);
    }

    /**
     * Returns a substring of the specified line.
     *
     * @param line The line to get the substring from.
     * @param startColumn The start of the substring.
     * @param endColumn The end of the substring (inclusive! Not like java substring!!).
     */
    public String getSubString(int line, int startColumn, int endColumn) {
        return this.buffer.getLine(line).subLine(startColumn, endColumn).toString();
    }

    /**
     * Returns a substring of the current line.
     *
     * @param startColumn The start of the substring.
     * @param endColumn The end of the substring.
     */
    public String getSubString(int startColumn, int endColumn) {
        return this.buffer.getLine(this.position.getLine()).subLine(startColumn, endColumn).toString();
    }

    /**
     * Returns the current word, and positions the current position at the beginning of the word.
     *
     * @return The current word.
     */
    public String getWord() {
        int i = this.position.getColumnNumber().baseZeroValue();
        int start;
        String line = getLine();
        while (i >= 0 && line.charAt(i) != ' ') {
            --i;
        }
        ++i;
        this.position.setColumn(new TextPos(TextPos.BASE_ZERO, i));
        StringBuilder word = new StringBuilder();
        while (i < line.length() && line.charAt(i) != ' ') {
            word.append(line.charAt(i));
            ++i;
        }

        return word.toString();
    }

    //
    // Positioning methods
    //

    /**
     * Sets the current position.
     *
     * @param position The position to  set.
     */
    public void setPosition(Position position) {
        this.position = position;
        adjustPosition();
    }

    /**
     * Sets the current position.
     *
     * @param column The column of the position.
     * @param line The line of the position.
     */
    public void setPosition(int column, int line) {
        setPosition(new Position(column, line));
    }

    /**
     * Returns the current position.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Moves the current position down.
     * <p>
     * Returns false on failure to do so.
     *
     * @param size The number of lines to move the position down.
     */
    public boolean moveDown(int size) {
        this.position.setLine(this.position.getLine() + size, this.buffer.getSize());
        return !adjustPosition();
    }

    /**
     * Moves the current positon up.
     * <p>
     * Returns false on failure to do so.
     *
     * @param size The number of lines to move the position up.
     */
    public boolean moveUp(int size) {
        this.position.setLine(this.position.getLine() - size);
        return !adjustPosition();
    }

    /**
     * Move the current position right.
     * <p>
     * Returns false on failure to do so.
     *
     * @param size The number of columns to move the position right.
     */
    public boolean moveRight(int size) {
        this.position.setColumn(this.position.getColumn() + size, this.buffer.getLine(this.position.getLine()).length());
        return !adjustPosition();
    }

    /**
     * Move the current position left.
     * <p>
     * Returns false on failure to do so.
     *
     * @param size The number of columns to move the position left.
     */
    public boolean moveLeft(int size) {
        this.position.setColumn(this.position.getColumn() - size);
        return !adjustPosition();
    }

    /**
     * Moves to the next character in the buffer being edited. This will move down to the
     * next line if at the end of the line. It will never move past the last character of
     * the buffer, but will stay at that character for successive calls.
     * <p>
     * Returns false on failure to do so.
     */
    public boolean moveToNextChar() {
        TextPos column = this.position.getColumnNumber();
        TextPos line = this.position.getLineNumber();
        column = column.increment();
        if (!this.buffer.columnWithinLimit(line, column)) {
            column = TextPos.FIRST;
            line = line.increment();
        }
        if (!this.buffer.lineWithinLimit(line)) {
            line = this.buffer.getLastLineNumber();
        }
        this.position.setColumn(column);
        this.position.setLine(line);
        return !adjustPosition();
    }

    /**
     * Moves to the previous character in the buffer being edited. This will move upp to the
     * previous line if at the beginning of the line. It will never move before the beginning
     * of the buffer, but will stay at the beginning for successive calls.
     * <p>
     * Returns false on failure to do so.
     */
    public boolean moveToPrevChar() {
        TextPos column = this.position.getColumnNumber();
        TextPos line = this.position.getLineNumber();
        column = column.decrement();
        if (!this.buffer.columnWithinLimit(line, column)) {
            line = line.decrement();
            if (!this.buffer.lineWithinLimit(line)) {
                line = TextPos.FIRST;
            }
            column = this.buffer.getLastLineNumber();
        }
        this.position.setColumn(column);
        this.position.setLine(line);
        return !adjustPosition();
    }

    /**
     * Returns the character at the current position.
     */
    public char getChar() {
        return this.buffer.getLine(this.position.getLineNumber()).charAt(this.position.getColumn());
    }

    /**
     * Moves to the right and returns the next character. If end of line is reached it
     * continues on the next line until end of buffer which will return END_OF_FILE_CHAR.
     *
     * @return The next character or END_OF_FILE_CHAR if end of buffer.
     */
    public char moveToAndReturnNextChar() {
        moveToNextChar();
        return getChar();
    }

    /**
     * Moves to the next word and returns it.
     */
    public String moveToAndReturnNextWord() {
        moveWordsRight(1);
        return getWord();
    }

    /**
     * Moves to the previous word and returns it.
     */
    public String moveToAndReturnPrevWord() {
        moveWordsLeft(1);
        return getWord();
    }

    /**
     * Moves the current position the specified number of words right and returns true if this was possible.
     * <p>
     * <b>Please note</b> that this method only moves right on the current line, it will not wrap to the next line!
     * When it returns false, do "moveDown(1);" and "moveToBeginningOfLine();" and then continue if you want to
     * cover several lines.
     *
     * @param words The number of words to move.
     */
    public boolean moveWordsRight(int words) {
        boolean success = true;
        Line line = this.buffer.getLine(this.position.getLine());
        int column = this.position.getColumn();

        for (int i = 0; i < words; i++) {
            while (success && line.charAt(column) == ' ') {
                ++column;
                success = line.columnWithinLimit(column);
            }
            while (success && line.charAt(column) != ' ') {
                ++column;
                success = line.columnWithinLimit(column);
            }
            while (success && line.charAt(column) == ' ') {
                ++column;
                success = line.columnWithinLimit(column);
            }
        }
        this.position.setColumn(column);

        adjustPosition();

        return success;
    }

    /**
     * Moves the current position the specified number of words left and returns true if this was possible.
     * <p>
     * <b>Please note</b> that this method only moves left on the current line, it will not wrap to the previous line!
     * When it returns false, do "moveUp(1);" and "moveToBeginningOfLine();" and then continue if you want to
     * cover several lines.
     *
     * @param words The number of words to move.
     */
    public boolean moveWordsLeft(int words) {
        boolean success = true;
        Line line = this.buffer.getLine(this.position.getLine());
        int column = this.position.getColumn();

        for (int i = 0; i < words; i++) {
            while (success && line.charAt(column) == ' ') {
                --column;
                success = line.columnWithinLimit(column);
            }
            while (success && line.charAt(column) != ' ') {
                --column;
                success = line.columnWithinLimit(column);
            }
        }
        ++column;
        this.position.setColumn(column);

        adjustPosition();

        return success;
    }

    /**
     * Moves the current position to the end of the line.
     */
    public void moveToEndOfLine() {
        Line line = this.buffer.getLine(this.position.getLine());
        this.position.setColumn(line.lastColumn());
    }

    /**
     * Moves the current position to the beginning of the line.
     */
    public void moveToBeginningOfLine() {
        this.position.setColumn(TextPos.FIRST);
    }

    /**
     * Moves the current position to the top of the file at 0,0.
     */
    public void moveToTopOfFile() {
        this.position.setColumn(TextPos.FIRST);
        this.position.setLine(TextPos.FIRST);
    }

    /**
     * Moves the current position to the bottom of the file at 0,last-line.
     */
    public void moveToBottomOfFile() {
        this.position.setColumn(TextPos.FIRST);
        this.position.setLine(this.buffer.getLastLine());
    }

    /**
     * Moves the current position to the end of the file at last-column,last-line.
     */
    public void moveToEndOfFile() {
        moveToBottomOfFile();
        moveToEndOfLine();
    }

    /**
     * Returns true if the current position is on the first line.
     */
    public boolean isOnFirstLine() {
        return this.position.getLineNumber().equals(TextPos.FIRST);
    }

    /**
     * Returns true if the current position is on the last line.
     */
    public boolean isOnLastLine() {
        return this.position.getLineNumber().equals(this.buffer.getLastLineNumber());
    }

    /**
     * Returns true if the current position is on the first column in the line.
     */
    public boolean isOnFirstColumn() {
        return this.position.getColumnNumber().equals(TextPos.FIRST);
    }

    /**
     * Returns true if the current position is on the last column of the line.
     */
    public boolean isOnLastColumn() {
        return this.position.getColumnNumber().equals(this.buffer.getLine(this.position.getLine()).lastColumnNumber());
    }

    /**
     * Finds the specified text and moves the current position. This always start searching from the
     * beginning of the buffer!
     *
     * @param text The text to find. This can be a regular expression.
     */
    public boolean find(String text) {
        return find(text, null);
    }

    /**
     * Finds the specified text starting search at current line and moves the current position.
     *
     * @param text The text to find. This can be a regular expression.
     */
    public boolean findFromCurrent(String text) {
        return findFromCurrent(text, null);
    }

    /**
     * Finds the specified text and moves the current position. This always start searching from the
     * beginning of the buffer!
     *
     * @param text The text to find. This can be a regular expression.
     * @param endAt A regexp to cause premature end of search.
     */
    public boolean find(String text, String endAt) {
        return find(text, TextPos.FIRST, endAt);
    }

    /**
     * Finds the specified text starting search at current line and moves the current position.
     *
     * @param text The text to find. This can be a regular expression.
     * @param endAt A regexp to cause premature end of search.
     */
    public boolean findFromCurrent(String text, String endAt) {
        return find(text, this.position.getLineNumber(), endAt);
    }

    /**
     * Finds the specified text and moves the current position. This always start searching from the
     * beginning of the buffer!
     *
     * @param text The text to find. This can be a regular expression.
     * @param endAt A regexp to cause premature end of search.
     */
    private boolean find(String text, TextPos startAtLine, String endAt) {
        boolean result = false;
        int lastLine = this.buffer.getLastLine();
        TextPos lineNo = startAtLine;
        for (; lineNo.lessThanOrEqual(lastLine); lineNo = lineNo.increment()) {
            Line line = this.buffer.getLine(lineNo);
            TextPos ix = line.find(text, TextPos.FIRST, endAt);
            if (ix != null) {
                if (ix.value() != Integer.MAX_VALUE) {
                    this.position = new Position(ix, lineNo);
                    result = true;
                }
                break;
            }
        }
        return result;
    }

    /**
     * Finds the next occurence of the specified text and moves the current position.
     *
     * @param text The text to find. This can be a regular expression.
     */
    public boolean findNext(String text) {
        boolean result = false;
        TextPos lastLine = this.buffer.getLastLineNumber();
        TextPos column = this.position.getColumnNumber().increment(1);
        TextPos lineNo = this.position.getLineNumber();
        while (lineNo.lessThanOrEqual(lastLine)) {
            Line line = this.buffer.getLine(lineNo);
            column = line.find(text, column);
            if (column != null) {
                this.position = new Position(column, lineNo);
                result = true;
                break;
            }
            else {
                lineNo = lineNo.increment();
                column = TextPos.FIRST;
            }
        }
        return result;
    }

    /**
     * Finds the last occurence of the specifeid text and moves the current position.
     *
     * @param text The text to find. This can be a regular expression.
     */
    public boolean findLast(String text) {
        if (findNext(text)) {
            moveRight(text.length());
            while(findNext(text)) {
                moveRight(text.length());
            }
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Returns a list of Occurence objects for each match. If no match was found the list will be empty.
     * <p>
     * This search will not move the current position, and will always start at the top of the file.
     *
     * @param text The text to find all occurences of. This can be a reqular expression.
     */
    public List<Occurence> findAllOccurences(String text) {
        Position oldpos = this.position.clone();
        moveToTopOfFile();

        List<Occurence> occurences = new ArrayList<Occurence>();

        if (find(text)) {
            Occurence occurense = new Occurence(this.position, this.buffer.getOriginalBuffer());
            occurences.add(occurense);
            while(findNext(text)) {
                occurense = new Occurence(this.position, this.buffer.getOriginalBuffer());
                occurences.add(occurense);
            }
        }

        this.position = oldpos;

        return occurences;
    }

    /**
     * Finds the last occurence of the specified text and returns it as an Occurence object.
     * This returns null if no occurence is found!
     * <p>
     * This search will not move the current position, and will always start at the top of the file.
     *
     * @param text The text to look for the last occurence of. This can be a reqular expression.
     */
    public Occurence findLastOccurence(String text) {
        Occurence occurence = null;
        List<Occurence> occurences = findAllOccurences(text);
        if (occurences.size() > 0) {
            occurence = occurences.get(occurences.size() - 1);
        }
        return occurence;
    }

    /**
     * Finds the next blank line starting at the current line. Returns true if found in which case
     * the poition will be updated to the beginning of the found line.
     */
    public boolean findBlankLine() {
        boolean found = false;
        TextPos lineNo = this.position.getLineNumber();
        for (; lineNo.lessThanOrEqual(this.buffer.getLastLineNumber()); lineNo = lineNo.increment()) {
            Line line = this.buffer.getLine(lineNo);
            if (line.isEmpty()) {
                this.position.setLine(lineNo);
                this.position.setColumn(TextPos.FIRST);
                found = true;
                break;
            }
        }

        return found;
    }

    /**
     * Finds the next blank line starting at the line after the current line. Returns true if found in which case
     * the position will be updated to the beginning of the found line.
     */
    public boolean findNextBlankLine() {
        if (this.position.getLineNumber().equals(this.buffer.getLastLineNumber())) {
            return false;
        }
        moveDown(1);
        boolean found = findBlankLine();
        if (!found) {
            moveUp(1);
        }

        return found;
    }

    /**
     * Finds all blank lines and returns them as a list of Occurence objects. If no blank lines
     * were found an empty list will be returned.
     * <p>
     * This search will not move the current position, and will always start at the top of the file.
     */
    public List<Occurence> findAllBlankLines() {
        Position oldpos = this.position.clone();
        moveToTopOfFile();

        List<Occurence> occurences = new ArrayList<Occurence>();

        if (findBlankLine()) {
            Occurence occurence = new Occurence(this.position.clone(), this.buffer.getOriginalBuffer());
            occurences.add(occurence);
            while (findNextBlankLine()) {
                occurence = new Occurence(this.position.clone(), this.buffer.getOriginalBuffer());
                occurences.add(occurence);
            }
        }

        this.position = oldpos;

        return occurences;
    }

    /**
     * Returns true if the current line matches the specified regular expression.
     *
     * @param regexp A regular expression to match with the line.
     */
    public boolean lineMatches(String regexp) {
        return getLineAsLine().matches(regexp);
    }

    /**
     * Moves down to the next line and returns true if that line matches the
     * specified regular expression. If it tries to move beyond last line of
     * file then false is returned.
     *
     * @param regexp The regular expression to match with the next line.
     */
    public boolean nextLineMatches(String regexp) {
        if (this.position.getLineNumber().equals(this.buffer.getLastLineNumber())) {
            return false;
        }
        moveDown(1);
        return lineMatches(regexp);
    }

    /**
     * Returns a list of Occurence objects for each match. If no match was found the list will be empty.
     * <p>
     * This search will not move the current position, and will always start at the top of the file.
     *
     * @param regexp The regular expression to find all occurences of.
     */
    public List<Occurence> findAllLinesMatching(String regexp) {
        List<Occurence> occurences = new ArrayList<Occurence>();

        TextPos lineNo = TextPos.FIRST;
        for (; lineNo.lessThanOrEqual(this.buffer.getLastLineNumber()); lineNo = lineNo.increment()) {
            if (this.buffer.getLine(lineNo).matches(regexp)) {
                Occurence occurense = new Occurence(new Position(TextPos.FIRST, lineNo), this.buffer.getOriginalBuffer());
                occurences.add(occurense);
            }
        }

        return occurences;
    }

    //
    // Selection methods
    //

    /**
     * Starts a new selection at the current position. End with endSelection(). Once a complete
     * selection have been made it can be replaced with replaceSelectionWithTextBuffer(...) and
     * deleted with deleteSelection().
     *
     * @see #endSelection()
     * @see #replaceSelectionWithTextBuffer(se.natusoft.tools.fileeditor.TextBuffer)
     * @see #deleteSelection()
     */
    public void startSelection() {
        this.selection = new Selection();
        this.selection.setStart(this.position);
    }

    /**
     * Marks the end of the selection at the current position.
     */
    public void endSelection() {
        this.selection.setEnd(this.position);
    }

    /**
     * Clears the selection.
     */
    public void clearSelection() {
        this.selection = null;
    }

    /**
     * Returns true if there is a valid selection set.
     */
    public boolean hasValidSelection() {
        boolean result = false;
        result =  this.selection != null && this.selection.getStart() != null && this.selection.getEnd() != null;
        if (result == true) {
            if (this.selection.getEnd().getLine() < this.selection.getStart().getLine()) {
                result = false;
            }
            else if (this.selection.getStart().getLine() == this.selection.getEnd().getLine() &&
                    this.selection.getEnd().getColumn() < this.selection.getStart().getColumn()) {
                result = false;
            }
        }

        return result;
    }

    /**
     * Moves to the next "end" character at the 0 level. To look for a matching parentesis
     * do moveToMatching('(', ')');, to a matching brace moveToMatching('{', '}');
     * <p>
     * <b>Please note</b> that this method will start looking at the next character. So if you
     * are standing on the starting character it will not be seen and thus the level count will
     * be wrong and the end will be missed, and it might en up moving to the end of the file.
     *
     * @param start This character increases the level.
     * @param end This is what to look for and also decreses the level.
     */
    public void moveToMatching(char start, char end) {
        int level = 0;
        boolean found = false;
        while (!found) {
            char c = moveToAndReturnNextChar();

            if (c == end) {
                --level;
            }

            if (c == end && level <= 0) {
                found = true;
            }
            if (c == END_OF_FILE_CHAR) {
                found = true;
            }

            if (c == start) {
                ++level;
            }

        }
    }

    /**
     * Selects to the next "end" character at the 0 level. To look for a matching parentesis
     * do selectToMatching('(', ')');, to a matching brace selectToMatching('{', '}');
     * <p>
     * A few notes:<br>
     * - You must have done startSelection() first!<br>
     * - The selection will include the matching end character, but the current position will remain at the matching end character.
     *
     * @param start This character increases the level.
     * @param end This is what to look for and also decreses the level.
     */
    public void selectToMatching(char start, char end) {
        moveToMatching(start, end);
        this.selection.setEnd(this.position);
        moveToPrevChar();
    }

    /**
     * Returns the current selection.
     */
    public Selection getSelection() {
        return this.selection;
    }

    //
    // Editing methods
    //

    /**
     * Replaces the current line completely wiht the specified text.
     *
     * @param text The text to replace the current line with.
     */
    public void replaceCurrentLine(String text) {
        this.buffer.setLine(this.position.getLineNumber(), text);
    }

    /**
     * Replaces the string between "before" and "after" in the current line.
     * <p/>
     * This is useful for updating contents of XML tags or any other before & after tags.
     *
     * @param before What should come before.
     * @param after What should be after.
     * @param newBetween The new value in between.
     */
    public void replaceCurrentLineBetween(String before, String after, String newBetween) {
        Line line = getLineAsLine();
        line.replaceBetween(before, after, newBetween);
        replaceCurrentLine(line.toString());
    }

    /**
     * Returns the text between "before" and "after" in the current line.
     * <p/>
     * This is useful to get the contents of XML tags or any other before & after tags.
     *
     * @param before The text that comes before the text to get.
     * @param after The text that comes after the text to get.
     *
     * @return The text between.
     */
    public String getCurrentLineBetween(String before, String after) {
        return getLineAsLine().getBetween(before, after);
    }

    /**
     * Replaces the first occurence of the specified text with the new text in the current line.
     *
     * @param text The text to replace. Can be a regular expression.
     * @param replaceWithText The new text to replace the old with.
     */
    public void replaceFirstInCurrentLine(String text, String replaceWithText) {
        Line line = this.buffer.getLine(this.position.getLine());
        if (line != null) {
            line.replaceFirst(text, replaceWithText);
            this.buffer.setLine(this.position.getLineNumber(), line);
        }
    }

    /**
     * Replaces all occurances of the specified text with the new text in the current line.
     *
     * @param text The text to replace. Can be a regular expression.
     * @param replaceWithText The new text to replace the old with.
     */
    public void replaceAllInCurrentLine(String text, String replaceWithText) {
        Line line = this.buffer.getLine(this.position.getLine());
        if (line != null) {
            line.replaceAll(text, replaceWithText);
            this.buffer.setLine(this.position.getLineNumber(), line);
        }
    }

    /**
     * Replaces all occurances of the specified text with the new text in the whole buffer.
     *
     * @param text The text to replace. Can be a regular expression.
     * @param replaceWithText The new text to replace the old with.
     */
    public void replaceAllInFile(String text, String replaceWithText) {
        int lastLine = this.buffer.getLastLine();
        TextPos lineNo = TextPos.FIRST;
        for (; lineNo.lessThanOrEqual(lastLine); lineNo = lineNo.increment()) {
            Line line = this.buffer.getLine(lineNo);
            if (line != null) {
                line.replaceAll(text, replaceWithText);
                this.buffer.setLine(lineNo, line);
            }
        }
    }

    /**
     * Replaces the current selection with the specified text. The text
     * should not contain any CR or NL characters!
     *
     * @param replacementText The text to replace with.
     */
    public void replaceSelectionWithText(String replacementText) {
        TextBuffer repBuf = new TextFileBuffer();
        repBuf.addLine(replacementText);
        replaceSelectionWithTextBuffer(repBuf);
    }

    /**
     * Replaces the current selection with the contents of the specified TextBuffer.
     *
     * @param replaceBuffer The buffer whose content should replace current selection.
     */
    public void replaceSelectionWithTextBuffer(TextBuffer replaceBuffer) {
        deleteSelection();
        insertTextBuffer(replaceBuffer);
    }

    /**
     * Inserts a TextBuffer at the current position and moves the current position
     * to after the last inserted character.
     *
     * @param insertBuffer The buffer to insert.
     */
    public void insertTextBuffer(TextBuffer insertBuffer) {

        String addToEnd = "";
        // Please note that TextBuffers index start at 0.
        int lastBufferLine = new TextPos(insertBuffer.getSize()).baseZeroValue();
        for (int i = 0; i <= lastBufferLine; i++) {
            String insLine = insertBuffer.getLine(i);
            if (i == 0) {
                if (this.position.getColumnNumber().greaterThan(TextPos.FIRST)) {
                    String line = getLine();
                    if (this.position.getColumn() < line.length()) {
                        addToEnd = line.substring(this.position.getColumnNumber().baseZeroValue());
                    }
                    line = line.substring(0, this.position.getColumnNumber().baseZeroValue());
                    line = line + insLine;
                    setLine(line);
                }
                else {
                    insertLineAbove(insLine);
                }
            }
            else if (i == lastBufferLine) {
                insertLine(insLine + addToEnd);
            }
            else {
                insertLine(insLine);
            }
        }
    }
    
    /**
     * Deletes the current selection and sets the current position to the position right after the deleted text.
     */
    public void deleteSelection() {
        String firstLine = this.buffer.getLine(this.selection.getStart().getLineNumber()).toString();
        String lastLine = this.buffer.getLine(this.selection.getEnd().getLineNumber()).toString();

        TextPos selStartIx = this.selection.getStart().getColumnNumber();
        TextPos selEndIx = this.selection.getEnd().getColumnNumber();

        TextPos lastPosOnLastLine = TextPos.fromLengthValue(lastLine.length());

        if (selEndIx.greaterThan(lastPosOnLastLine)) {
            selEndIx = lastPosOnLastLine;
        }
        // Please note that for substring the last value (end position) is exclusive while the first is inclusive, which is
        // why we need to add 1 to the end index for the lastLine.substring(...).
        String firstLinePart ="";
        if (firstLine.length() > selStartIx.baseZeroValue()) {
            firstLinePart = firstLine.substring(0, selStartIx.baseZeroValue());
        }
        else {
            firstLinePart = firstLine;
        }
        String lastLinePart = "";
        if (lastLine.length() > (selEndIx.baseZeroValue() + 1)) {
            lastLinePart = lastLine.substring(selEndIx.baseZeroValue() + 1);
        }
        String resultingLine = firstLinePart + lastLinePart;
        
        int noLines = this.selection.getEnd().getLine() - this.selection.getStart().getLine() + 1; // Add one since both start and end is inclusive!

        for (int i = 0; i < noLines; i++) {
            this.buffer.removeLine(this.selection.getStart().getLineNumber());
        }

        if (resultingLine.length() != 0) {
            this.buffer.insertLine(this.selection.getStart().getLineNumber(), resultingLine);
        }
        this.position = this.selection.getStart();
        this.selection = null;
    }

    /**
     * Returns the selection as a TextBuffer.
     */
    public TextBuffer getSelectedText() {
        TextBuffer retbuffer = new TextFileBuffer();

        TextPos lineNo = this.selection.getStart().getLineNumber();
        Line line = this.buffer.getLine(lineNo);
        if (this.selection.getEnd().getLineNumber().equals(lineNo)) {
            line = line.subLine(this.selection.getStart().getColumn(), this.selection.getEnd().getColumn());
            retbuffer.addLine(line.toString());
        }
        else {
            line = line.subLine(this.selection.getStart().getColumnNumber(), TextPos.LAST);
            lineNo = lineNo.increment();
            retbuffer.addLine(line.toString());
            while (lineNo.lessThanOrEqual(this.selection.getEnd().getLineNumber())) {
                line = this.buffer.getLine(lineNo);
                if (lineNo.equals(this.selection.getEnd().getLineNumber())) {
                    line = line.subLine(TextPos.FIRST, this.selection.getEnd().getColumnNumber());
                    retbuffer.addLine(line.toString());
                }
                else {
                    retbuffer.addLine(line.toString());
                }

                lineNo = lineNo.increment();
            }
        }

        return retbuffer;
    }


    /**
     * Returns the selection as a String including '\n' character at end of line for multi line selections.
     */
    public String getSelectedTextAsString() {
        TextBuffer selectionBuffer = getSelectedText();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < selectionBuffer.getSize(); i++) {
            sb.append(selectionBuffer.getLine(i));
            if ((i + 1) < selectionBuffer.getSize()) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Inserts another buffer into the buffer at the line below the current line.
     *
     * @param buffer The buffer to insert.
     */
    public void insertBuffer(TextBuffer buffer) {
        insertLine(buffer.getLine(0));
        int line;
        for (line = 1; line < buffer.getSize(); line ++) {
            insertLine(buffer.getLine(line));
        }
    }

    /**
     * Appends another buffer into this one at the end.
     */
    public void appendBuffer(TextBuffer buffer) {
        moveToEndOfFile();
        int bsize = buffer.getSize();
        for (int i = 0; i < bsize; i++) {
            insertLine(buffer.getLine(i));
        }
    }
    
    /**
     * Inserts a line after the current line.
     */
    public void insertLine(String line) {
        this.position.incrementLine();
        this.buffer.insertLine(this.position.getLine(), line);
    }
    
    /**
     * Inserts a line above the current line.
     */
    public void insertLineAbove(String line) {
        this.buffer.insertLine(this.position.getLine(), line);
    }

    /**
     * Removes the current line. The new current line will be the line above the deleted line. A new insertLine() will insert in
     * the position of the deleted line.
     */
    public void deleteCurrentLine() {
        this.buffer.removeLine(this.position.getLine());
        this.position.decrementLine();
    }

    /**
     * Deletes the current word as denoted by the specified delimeter. Afterward the current position will
     * be right after the deleted word.
     *
     * @param delimeter The delimeter that separates words.
     */
    public void deleteCurrentWord(char delimeter) {
        Line line = this.buffer.getLine(this.position.getLineNumber());

        while (this.position.getColumnNumber().greaterThan(TextPos.FIRST) && !(line.charAt(this.position.getColumn()) == delimeter)) {
            this.position.decrementColumnm();
        }
        if (line.charAt(this.position.getColumn()) == delimeter) {
            this.position.incrementColumn();
        }

        int endWord = this.position.getColumn();
        while (endWord <= line.length() && !(line.charAt(endWord) == delimeter)) {
            ++endWord;
        }

        Line newLine;
        if (this.position.getColumnNumber().greaterThan(TextPos.FIRST)) {
            newLine = line.subLine(TextPos.FIRST, this.position.getColumnNumber().decrement()).append(line.subLine(new TextPos(endWord), TextPos.LAST));
        }
        else {
            newLine = line.subLine(new TextPos(endWord), TextPos.LAST);
        }
        this.buffer.setLine(this.position.getLineNumber(), newLine);
    }
    
    /**
     * Deletes the current word.
     */
    public void deleteCurrentWord() {
        deleteCurrentWord(' ');
    }
    
    //
    // Misc methods
    //
        
    /**
     * Displays the loaded file.
     */
    public void show() {
        show(0, this.buffer.getSize() - 1);
    }
    
    /**
     * Displays the loaded file.
     */
    public void show(int start, int end) {
        for (int i = start; i <= end; i++) {
            Line line = this.buffer.getLine(i);
            if (this.selection != null && this.selection.getEnd() != null) {
                if (this.selection.getStart().getLine() == i) {
                    line = line.subLine(TextPos.FIRST, this.selection.getStart().getColumnNumber()).append("<<[" + line.subLine(this.selection.getStart().getColumnNumber().increment(), TextPos.LAST));
                }
                else if (this.selection.getEnd().getLine() == i) {
                    line = line.subLine(TextPos.FIRST, this.selection.getEnd().getColumnNumber()).append("]>>" + line.subLine(this.selection.getEnd().getColumnNumber().increment(), TextPos.LAST));
                }
            }
            System.out.println(line);
        }
    }
    
}
