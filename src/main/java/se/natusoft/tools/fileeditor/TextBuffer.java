package se.natusoft.tools.fileeditor;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

import java.io.IOException;

/**
 * This is an interface representing a buffer of text.
 * <p>
 * This interface exists just for the heck of it. There is currently only one implementation
 * available, and I cannot really think of a reason for implementing another. Maybe someone
 * else can. Anyhow, this interface is relativly small and was easy to make, so what the heck.
 * <p>
 * Please note that line numbers in this interface starts at 0. In the editor itself the line
 * numbers start at 1.
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
public interface TextBuffer {
        
    /**
     * Loads a text file into the buffer.
     *
     * @param path The path to the file to load.
     */
    public void load(String path) throws IOException;
    
    /**
     * Saves the buffer back to disk again.
     */
    public void save() throws IOException;
    
    /**
     * Saves the buffer to another file.
     *
     * @param path The path to the file to save to.
     */
    public void saveAs(String path) throws IOException;
    
    /**
     * Clears the buffer.
     */
    public void clear();
    
    /**
     * Returns the number of lines in the buffer.
     */
    public int getSize();
    
    /**
     * Gets a specific line from the buffer.
     *
     * @param line The line to get.
     */
    public String getLine(int line);
    
    /**
     * Replaces a specific line in the buffer.
     *
     * @param line The line to replace.
     * @param text The new text for the line.
     */
    public void setLine(int line, String text);
    
    /**
     * Removes the specified line.
     *
     * @param line The line to remove.
     */
    public void removeLine(int line);
    
    /**
     * Inserts a new line at the specified position.
     *
     * @param line the poistion to insert at.
     * @param text The text to insert.
     */
    public void insertLine(int line, String text);
        
    /**
     * Adds a line of text to the end of the buffer.
     *
     * @param text The text to add.
     */
    public void addLine(String text);
    
    /**
     * Returns a new instance of the buffer.
     * <p>
     * Some editor operations work by creating a new buffer. This method
     * allows those operations to create a buffer of the same type.
     * <p>
     * Please note however that any saved reference to the loaded file must be
     * copied to the new instance before it is returned.
     */
    public TextBuffer newInstance();

    /**
     * If true makes the buffer read only.
     *
     * @param readOnly Set to true to make buffer read only. Defaults to false.
     */
    public void setReadOnly(boolean readOnly);
}
