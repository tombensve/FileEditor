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
 * This represents an occurence hit in the editor buffer.
 */
public class Occurence {
    
    //
    // Private Members
    //
    
    /** The position of the occurence. */
    private Position position;
    
    /** The buffer the occurence belongs to. */
    private TextBuffer buffer;
        
    //
    // Constructors
    //
        
    /**
     * Creates a new instance of Occurence.
     *
     * @param position The position of this occurence
     * @param buffer The text buffer this line belongs to.
     */
    public Occurence(Position position, TextBuffer buffer) {
        this.position = position;
        this.buffer = buffer;
    }
    
    //
    // Methods
    //
        
    /**
     * Returns the line number represented by this Occurence.
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Returns the lines text.
     */
    public String getLineText() {
        return this.buffer.getLine(this.position.getLine());
    }

    /**
     * Updates the lines text.
     *
     * @param text The text to set.
     */
    public void setLineText(String text) {
        this.buffer.setLine(this.position.getLine(), text);
    }

    public String toString() {
        return "" + this.position + ":\"" + getLineText() + "\"";
    }
}
