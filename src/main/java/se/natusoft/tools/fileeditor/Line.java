package se.natusoft.tools.fileeditor;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;
import se.natusoft.tools.fileeditor.internal.TextPos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This represents a line of text in a buffer and provides basic line editing functionality.
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
public class Line {
    
    //
    // Private Members
    //
    
    /** Holds the internal represtentation of the line text. */
    private StringBuilder line = null;
        
    //
    // Constructors
    //
    
    /**
     * Creates a new instance of Line.
     */
    public Line() {
        this.line = new StringBuilder();
    }

    /**
     * Creates a new instance of Line.
     *
     * @param text The text of the line.
     */
    public Line(String text) {
        this.line = new StringBuilder(text);
    }
    
    
    //
    // Methods
    //

    /**
     * Empties the line.
     */
    public void empty() {
        this.line = new StringBuilder();
    }

    /**
     * Replaces the line with the specified text.
     *
     * @param text The text to replace with.
     *
     * @return itself.
     */
    public Line replace(String text) {
        this.line = new StringBuilder(text);
        return this;
    }

    /**
     * Replaces the line with the specified text.
     *
     * @param line The line to replace with.
     *
     * @return itself.
     */
    public Line replace(Line line) {
        this.line = new StringBuilder(line.toString());
        return this;
    }

    /**
     * Prepends text to the line.
     *
     * @param text The text to prepend.
     *
     * @return itself.
     */
    public Line prepend(String text) {
        this.line.insert(0, text);
        return this;
    }

    /**
     * Prepends text to the line.
     *
     * @param line The Line to prepend.
     *
     * @return itself.
     */
    public Line prepend(Line line) {
        this.line.insert(0, line.toString());
        return this;
    }

    /**
     * Appends the text to the line.
     *
     * @param text The text to append.
     *
     * @return itself.
     */
    public Line append(String text) {
        this.line.append(text);
        return this;
    }

    /**
     * Appends the text to the line.
     *
     * @param line The Line to append.
     *
     * @return itself.
     */
    public Line append(Line line) {
        this.line.append(line.toString());
        return this;
    }

    /**
     * Inserts text at a specific position in the line.
     *
     * @param text The text to insert.
     * @param column The position to insert at.
     *
     * @return itself.
     */
    public Line insert(String text, int column) {
        TextPos pos = new TextPos(column);
        insert(text, pos);

        return this;
    }

    /**
     * Inserts a Line at a specific position in the line.
     *
     * @param line The Line to insert.
     * @param column The position to insert at.
     *
     * @return itself.
     */
    public Line insert(Line line, int column) {
        TextPos pos = new TextPos(column);
        insert(line.toString(), pos);

        return this;
    }

    /**
     * Inserts text at a specific position in the line.
     *
     * @param text The text to insert.
     * @param column The position to insert at.
     *
     * @return itself.
     */
    public Line insert(String text, TextPos column) {
        this.line.insert(column.baseZeroValue(), text);

        return this;
    }

    /**
     * Deletes the part of the line between start and end where the end position is inclusive!
     * This is different from TextBuilders delete() method and Strings substring() method
     * where the end is exclusive.
     *
     * @param start Start to delete at this position.
     * @param end Delete up to including the character at this position.
     *
     * @return itself.
     */
     public Line delete(int start, int end) {
        TextPos tpStart = new TextPos(start);
        TextPos tpEnd = new TextPos(end);
        delete(tpStart, tpEnd);

        return this;
     }

    /**
     * Deletes the part of the line between start and end where the end position is inclusive!
     * This is different from TextBuilders delete() method and Strings substring() method
     * where the end is exclusive.
     *
     * @param start Start to delete at this position.
     * @param end Delete up to including the character at this position.
     *
     * @return itself.
     */
    public Line delete(TextPos start, TextPos end) {
        this.line.delete(start.baseZeroValue(), end.baseZeroValue() + 1);
        return this;
    }

    /**
     * Internal support method for replacing text in the line.
     *
     * @param toReplace The text to replace.
     * @param replaceWith What to replace it with.
     * @param startingAt Position to start looking for 'toReplace' at.
     *
     * @return The index at which 'toReplace' was found.
     */
    private int _replace(String toReplace, String replaceWith, int startingAt) {
        int ix = this.line.indexOf(toReplace, startingAt);
        if (ix >= 0) {
            this.line.replace(ix, ix + toReplace.length(), replaceWith);
        }

        return ix;
    }

    /**
     * Replaces part of a line with another text.
     *
     * @param toReplace The text to replace.
     * @param replaceWith The text to replace it with.
     * @param startingAt The column to start searching for 'toReplace'.
     *
     * @return true if anything was replaced.
     */
    public boolean replace(String toReplace, String replaceWith, TextPos startingAt) {
        boolean replaced = false;

        if (_replace(toReplace, replaceWith, startingAt.baseZeroValue()) >= 0) {
            replaced = true;
        }
        return replaced;
    }

    /**
     * Replaces the first occurance of a text whitin the line with another text.
     *
     * @param toReplace The text to replace.
     * @param replaceWith The text to replace it with.
     *
     * @return true if any text was replaced.
     */
    public boolean replaceFirst(String toReplace, String replaceWith) {
        return replace(toReplace, replaceWith, TextPos.FIRST);
    }

    /**
     * Replaces all occurances of a text within the line.
     *
     * @param toReplace The text to replace.
     * @param replaceWith The text to replace it with.
     *
     * @return true if anything was replaced.
     */
    public boolean replaceAll(String toReplace, String replaceWith) {
        boolean replaced = false;

        int ix = 0;
        while (ix >= 0) {
            ix = _replace(toReplace, replaceWith, ix);
            if (ix >= 0) {
                replaced = true;
                ix += replaceWith.length();
            }
        }

        return replaced;
    }

    /**
     * Replaces the text between "before" and "after" with "newBetween".
     *
     * @param before The text that comes before what should be replaced.
     * @param after The text that comes after what should be replaced.
     * @param newBetween The new inbetween text.
     *
     * @return true if anything was replaced, false otherwise.
     */
    public boolean replaceBetween(String  before, String after, String newBetween) {
        int bix = this.line.indexOf(before);
        if (bix >= 0) {
            int aix = this.line.indexOf(after, bix + before.length());
            if (aix >= 0) {
                String newLine = this.line.substring(0, bix + before.length()) + newBetween + this.line.substring(aix);
                this.line = new StringBuilder(newLine);
                return true;
            }
        }

        return false;
    }

    /**
     * Returns the text between the "before" and "after".
     *
     * @param before The text that comes before what should be gotten.
     * @param after The text that comes after what should be gotten.
     *
     * @return The between text.
     */
    public String getBetween(String before, String after) {
        String between = "";
        int bix = this.line.indexOf(before);
        if (bix >= 0) {
            int aix = this.line.indexOf(after, bix + before.length());
            if (aix >= 0) {
                between = this.line.substring(bix + before.length(), aix);
            }
        }

        return between;
    }

    /**
     * Returns a sub line of this line. Please note that the end specification is inclusive!
     *
     * @param start The starting column of the sub string.
     * @param end The ending column of the substring. This is inclusive!
     */
    public Line subLine(int start, int end) {
        return subLine(new TextPos(start), new TextPos(end));
    }

    /**
     * Returns a sub line of this line. Please note that the end specification is inclusive!
     *
     * @param start The starting column of the sub string.
     * @param end The ending column of the substring. This is inclusive!
     */
    public Line subLine(TextPos start, TextPos end) {
        Line subLine = null;
        if (end.greaterThan(length())) {
            end = new TextPos(length());
        }
        if (end.value() < start.value()) {
            subLine = new Line("");
        }
        else {

            // Wanted to be extra clear here. We are working with StringBuilder.substring() which is
            // base 0, but we also want the end position to be inclusive so we add 1. This would
            // ofcourse be the same as end.value(), but that is not as clear.
            //System.out.println("start:" + start.baseZeroValue() + ", end:" + end.baseZeroValue());
            subLine = new Line(this.line.substring(start.baseZeroValue(), end.baseZeroValue() + 1));
        }

        return subLine;
    }

    /**
     * Returns the character at the specified column.
     *
     * @param column The column to get the character at.
     */
    public char charAt(int column) {
        return charAt(new TextPos(column));
    }

    /**
     * Returns the character at the specified column.
     *
     * @param column The column to get the character at.
     */
    public char charAt(TextPos column) {
        return this.line.charAt(column.baseZeroValue());
    }

    /**
     * Returns the index of the first match of the text within the line or -1 if not match was found.
     * <p>
     * Please note that a match on the first character in the line will return 1 not 0!
     *
     * @param text The text to search for.
     */
    public TextPos indexOf(String text) {
        int foundIx = this.line.indexOf(text);
        if (foundIx != -1) {
            ++foundIx;
        }

        return new TextPos(foundIx);
    }

    /**
     * Returns the index of the first match of the text within the line or -1 if not match was found.
     * <p>
     * Please note that a match on the first character in the line will return 1 not 0!
     *
     * @param text The text to search for.
     * @param fromColumn The column to start searching from.
     */
    public TextPos indexOf(String text, TextPos fromColumn) {
        int foundIx = this.line.indexOf(text, fromColumn.baseZeroValue());
        if (foundIx != -1) {
            ++foundIx;
        }

        return new TextPos(foundIx);
    }

    /**
     * Finds a regexp in the line starting at the specified position.
     * <p>
     * Returns the column of the match if found or null otherwise.
     *
     * @param regexp The regular expression to search for.
     * @param fromColumn The column to start searching.
     */
    public TextPos find(String regexp, TextPos fromColumn) {
        return find(regexp, fromColumn, null);
    }

    /**
     * Finds a regexp in the line starting at the specified position.
     * <p>
     * Returns the column of the match if found or null otherwise.
     *
     * @param regexp The regular expression to search for.
     * @param fromColumn The column to start searching.
     * @param endAt If not null a regexp that ends searching.
     */
    public TextPos find(String regexp, TextPos fromColumn, String endAt) {
        TextPos result = null;

        if (length() != 0) {
            Pattern pattern = Pattern.compile(regexp);
            Matcher matcher = pattern.matcher(this.line);
            int fromCol = fromColumn.baseZeroValue();
            if (fromCol >= length()) {
                fromCol = length() - 1;
            }
            if (matcher.find(fromCol)) {
                result = new TextPos(TextPos.BASE_ZERO, matcher.start());
            }
            else if (endAt != null && this.line.toString().matches(endAt)) {
                result = new TextPos(Integer.MAX_VALUE);
            }
        }

        return result;
    }

    /**
     * Returns the length of the line.
     */
    public int length() {
        return this.line.length();
    }

    /**
     * Returns the last column of the line. This is just an alias for length()!
     */
    public int lastColumn() {
        return length();
    }

    public TextPos lastColumnNumber() {
        return new TextPos(length());
    }

    /**
     * Returns true if the specified column is whitin limit of the line.
     * 
     * @param column The column to verify.
     */
    public boolean columnWithinLimit(int column) {
        return column >= 1 && column <= length();
    }

    /**
     * Returns true if the line is empty.
     */
    public boolean isEmpty() {
        return this.line.toString().trim().length() == 0;
    }

    /**
     * Returns true if this line matches the specified regular expression.
     * 
     * @param regexp The regular expression to match.
     */
    public boolean matches(String regexp) {
        return this.line.toString().matches(regexp);
    }

    /**
     * Returns the line as a String.
     */
    @Override
    public String toString() {
        return this.line.toString();
    }

}
