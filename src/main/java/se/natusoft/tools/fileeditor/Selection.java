package se.natusoft.tools.fileeditor;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

/**
 * This represents a selected block of text by a start and end position.
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
