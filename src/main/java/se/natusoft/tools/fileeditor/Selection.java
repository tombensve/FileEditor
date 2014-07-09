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

/**
 * This represents a selected block of text by a start and end position.
 */
public class Selection {
    
    //
    // Private Members
    //
    
    /** The start position of the selection. */
    private Position start = null;
    
    /** The end position of the selection. */
    private Position end = null;
    
    //
    // Constructors
    //
    
    /**
     * Creates a new instance of Selection.
     */
    public Selection() {
    }
    
    /**
     * Creates a new instance of Selection.
     *
     * @param start The start of the selection.
     * @param end The end of the selection.
     */
    public Selection(Position start, Position end) {
        this.start = start;
        this.end = end;
    }
    
    //
    // Methods
    //
    
    /**
     * Sets the start position.
     *
     * @param start The start position to set.
     */
    public void setStart(Position start) {
        this.start = new Position(start.getColumn(), start.getLine());
    }
    
    /**
     * Returns the start of the selection.l
     */
    public Position getStart() {
        return this.start;
    }
    
    /**
     * Sets the end position.
     *
     * @param end The position to set.
     */
    public void setEnd(Position end) {
        this.end = new Position(end.getColumn(), end.getLine());
    }
    
    /**
     * Returns the end of the selection.
     */
    public Position getEnd() {
        return this.end;
    }

    @Override
    public String toString() {
        return "" + this.start + " - " + this.end;
    }
}
