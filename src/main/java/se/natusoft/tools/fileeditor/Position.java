/* 
 * 
 * PROJECT
 *     Name
 *         FileEditor
 *     
 *     Code Version
 *         2.0.3
 *     
 *     Description
 *         Provides a programmable only text file editor. No gui is available. It supplies
 *         most of the features you expect from an editor including selections.
 *         
 * COPYRIGHTS
 *     Copyright (C) 2012 by Natusoft AB All rights reserved.
 *     
 * LICENSE
 *     Apache 2.0 (Open Source)
 *     
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *     
 *       http://www.apache.org/licenses/LICENSE-2.0
 *     
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *     
 * AUTHORS
 *     tommy ()
 *         Changes:
 *         2014-07-09: Created!
 *         
 */
package se.natusoft.tools.fileeditor;

import se.natusoft.tools.fileeditor.internal.TextPos;

/**
 * This represents a position in a text file by line number and column. The origin is at 1,1 not 0,0!
 * Trying to set a value less than 1 will set it to 1.
 */
public class Position {
    
    //
    // Private Members
    //
    
    /** The current line. */
    private TextPos line = TextPos.FIRST;
    
    /** The current column. */
    private TextPos column = TextPos.FIRST;
        
    //
    // Constructors
    //
    
    /**
     * Creates a new instance of Position.
     */
    public Position() {
    }
    
    /**
     * Creates a new instance of Position.
     *
     * @param line The line this position points to.
     * @param column The column this position points to.
     */
    public Position(int column, int line) {
        this.line = new TextPos(line);
        this.column = new TextPos(column);
    }
    
    /**
     * Creates a new instance of Position.
     *
     * @param line The line this position points to.
     * @param column The column this position points to.
     */
    /*package*/ Position(TextPos column, TextPos line) {
        this.line = line;
        this.column = column;
    }

    //
    // Methods
    //

    /**
     * Adjusts the position to be withing limits.
     * 
     * @param maxLines
     * @param maxColumns
     */
    public void adjustLimits(int maxLines, int maxColumns) {
        line.adjustLimits(maxLines);
        column.adjustLimits(maxColumns);
    }

    /**
     * Sets the line of the position.
     *
     * @param line The line to set.
     */
    public void setLine(int line) {
        this.line = new TextPos(line);
    }
    
    /**
     * Sets the line of the position.
     *
     * @param line The line to set.
     */
    /*package*/ void setLine(TextPos line) {
        this.line = line;
    }

    /**
     * Sets the line of the position.
     *
     * @param line The line to set.
     * @param maxLine The maximul line that can be set.
     */
    public void setLine(int line, int maxLine) {
        if (line > maxLine) {
            line = maxLine;
        }
        setLine(line);
    }

    /**
     * Returns the number of current line.
     */
    public int getLine() {
        return this.line.value();
    }

    /**
     * Returns the number of the current line.
     */
    /*package*/ TextPos getLineNumber() {
        return this.line;
    }
    
    /**
     * Sets the current column.
     *
     * @param column The colum to set.
     */ 
    public void setColumn(int column) {
        this.column = new TextPos(column);
    }
    
    /**
     * Sets the current column.
     *
     * @param column The colum to set.
     */
    /*package*/ void setColumn(TextPos column) {
        this.column = column;
    }

    /**
     * Sets the current column.
     *
     * @param column The colum to set.
     * @param maxColumn the max column that can be set.
     */
    public void setColumn(int column, int maxColumn) {
        if (column > maxColumn) {
            column = maxColumn;
        }
        setColumn(column);
    }

    /**
     * Returns the current column number.
     */
    public int getColumn() {
        return this.column.value();
    }

    /**
     * Returns the current column number.
     */
    /*package*/ TextPos getColumnNumber() {
        return this.column;
    }

    /**
     * Increments the line number.
     */
    public void incrementLine() {
        this.line = this.line.increment();
    }

    /**
     * Decrements the line number.
     */
    public void decrementLine() {
        this.line = this.line.decrement();
    }

    /**
     * Increments the column.
     */
    public void incrementColumn() {
        this.column = this.column.increment();
    }

    /**
     * Decrements the column.
     */
    public void decrementColumnm() {
        this.column = this.column.decrement();
    }

    /**
     * Returns a clone of this Position.
     */
    @Override
    public Position clone() {
        return new Position(this.column, this.line);
    }

    /**
     * Returns a String representation of the position.
     */
    public String toString() {
        return "" + this.line + ":" + this.column;
    }
}
