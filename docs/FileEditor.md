# FileEditor 2.0

## Introduction

FileEditor provides a programmable only text file editor. No gui is
available. It supplies most of the features you expect from an editor
including selections.

It is useful for advanced code generation and first version created
a couple of years ago was made to script a set of special refactorings
using BeanShell. It has now been updated and more heavily tested,
and released as open source in hope it can be useful for others.

## Maven Usage

If you are using maven, add the following to your pom:

	  <project>
	    ...
	    <repositories>
	        ...
	        <repository>
	            <id>maven-natusoft-se</id>
	            <name>Natusoft maven repository</name>
	            <url>http://maven.natusoft.se/</url>
	        </repository>
	    </repositories>
	    ...
	  </project>

Then add the following dependency:

	  <dependency>
	    <groupId>se.natusoft.tools</groupId>
	    <artifactId>FileEditor</artifactId>
	    <version>2.0</version>
	  </dependency>

## Usage

Each instance of TextFileEdtior can edit one TextBuffer. The default constructor
creates a TextFileBuffer instance. Text buffers can however be gotten and set on a
TextFileEditor instance, so you can really edit as many buffers you want with the
same TextFileEdtior instance.

TextBuffer is an interface implemented by TextFileBuffer. The idea was that you could
wrap some other object and make it editable (as long as it handles text). I have no
idea if that is useful or not, but it was a simple thing to do.

The editor always has a current position within the editor. It starts at 1,1 (column,line).
Column and line numbers are 1 based. There is a Position class that represent a position
that some methods use. There are methods to move the current position up, down, right, and left.

All find methods move the current position to the first character of the found text on success.
They also return a boolean to indicate if the find was a success or not so that you can do if()
on it.

Example:


	if(editor.find("start")) {
	    editor.startSelection();
	    if (editor.find("end")) {
	        editor.endSelection();
	        editor.replaceSelectionWithTextBuffer(newTextBuff);
	    }
	}

You can check if the current position is on first column, first line,
last column, or last line.

All find*() methods search criteria are regular expressions.

You can mark a text block as selected. You can do
selectToMatching(char start, char end) which will select to the next
'end' character at level 0. Level is incremented on start and decremented
on end. 

Example:

	selectToMatching('(', ')');

You can find and replace text within the buffer. You can replace a
selection. You can replace a line.

You can insert lines. You can insert another file by loading it into
a separate buffer and then inserting the buffer.

You can delete words, lines, and selections.

You can get a line or a word.

Creating an editor and loading a file into it:

	TextFileEditor editor = new TextFileEditor();
	editor.load("some.file");
  
	...
  
	editor.save();

If you want to create an editor instance, pass it to some user script
that will manipulate the text in the editor, but want to inhibit the
script to load other files or to save the changes, do:

	editor.setAllowLoadSave(false);

But remember to set it back to true again before you save it! This is
of course not fail safe since the script can also call setAllowLoadSave().

Here is an example of usage copied from another tool where I use
FileEditor to update source code:

	if (!editor.find("import " + annotationsPkg + ".")) {
	    System.out.println("   Adding 'import " + annotationsPkg + ".*'");
	    if (!editor.find("^import")) { 
	        if (editor.find("package")) {
	            editor.insertLine("");
	        }
	    }
	    else {
	        editor.findLast("^import");
	    }
	    editor.insertLine("import " + annotationsPkg + ".*;");
	}

	editor.moveToTopOfFile();
	if (!editor.find("import " + enumsPkg + ".Source")) {
	    editor.find("import " + codelicmgrPkg);
	    System.out.println("   Adding 'import " + enumsPkg + ".Source'");
	    editor.insertLine("import " + enumsPkg + ".Source;");
	}


There is not much more to say about this. 


# Special note to users of version < 2.0.

A previous version of this utility exists on <http://fileditor.sf.net/>.
As of version 2.0 this has moved to github and also changed package from _se.biltmore..._
to _se.natusoft..._ The package change was required due to a bad decision by me to use the
package name of a company my company was part owner of, but in the end didn't work out as
intended and I left. Thereby I have changed package to my own company name.

If you used the previous version then a search and replace of _biltmore_ to _natusoft_
in your poms and imports should be enough. Nothing else have change between previous
version and 2.0.
