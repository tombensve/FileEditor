package se.natusoft.tools.fileeditor;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

/**
 * This represents an occurence hit in the editor buffer.
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
        @Change(when="2009-11-08", description="Created")
    }
)
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
