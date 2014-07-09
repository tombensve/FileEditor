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

import junit.framework.TestCase;
import se.natusoft.tools.fileeditor.internal.TextPos;

import java.util.List;

/**
 * Tests the editor functions.
 */
public class TextFileEditorTest extends TestCase {

    public TextFileEditorTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    private TextBuffer getTestBuffer1() {
        TextBuffer buffer = new TextFileBuffer();
        buffer.addLine("import some.path.to.somewhere;");
        buffer.addLine("import elsewhere.some.place;");
        buffer.addLine("");
        buffer.addLine("@Test1(blaha=qwerty)");
        buffer.addLine("@Test2(qaz=wsx)");
        buffer.addLine("  ");
        buffer.addLine("One Two Three Four Five Six Seven Eight Nine Ten");
        return buffer;
    }

    private TextBuffer getTestReplaceBuffer1() {
        TextBuffer buffer = new TextFileBuffer();
        buffer.addLine("Test3(tjohej=tjoho)");
        buffer.addLine("@Test4");
        return buffer;
    }

    private TextFileEditor getTestBuffer1Editor() {
        TextFileEditor editor = new TextFileEditor();
        editor.setTextBuffer(getTestBuffer1());
        return editor;
    }

    private TextFileEditor getLoremIpsumEditor() {
        TextFileEditor editor = new TextFileEditor();
        editor.setTextBuffer(getLoremIpsumBuffer());
        return editor;
    }

    private TextBuffer getLoremIpsumBuffer() {
        TextBuffer buffer = new TextFileBuffer();
        buffer.addLine("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore ");
        buffer.addLine("et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut ");
        buffer.addLine("aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum ");
        buffer.addLine("dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui ");
        buffer.addLine("officia deserunt mollit anim id est laborum.");
        return buffer;
    }

    private TextBuffer getXMLTestBuffer() {
        TextBuffer buffer = new TextFileBuffer();
        buffer.addLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        buffer.addLine("<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">");
        buffer.addLine("<modelVersion>4.0.0</modelVersion>");
        buffer.addLine("");
        buffer.addLine("<groupId>se.natusoft.tools.fileeditor</groupId>");
        buffer.addLine("<artifactId>FileEditor</artifactId>");
        buffer.addLine("<packaging>jar</packaging>");
        buffer.addLine("<version>2.0</version>");
        buffer.addLine("<name>FileEditor</name>");
        buffer.addLine("");
        buffer.addLine("<url>https://github.com/tombensve/FileEditor</url>");
        return buffer;
    }

    /**
     * Test of getSubString method, of class TextFileEditor.
     */
    public void testGetSubString_3args() {
        TextFileEditor editor =  getTestBuffer1Editor();
        assertTrue(editor.getSubString(4, 8, 12).equals("blaha"));

    }

    /**
     * Test of setPosition method, of class TextFileEditor.
     */
    public void testSetPosition_Position() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.setPosition(5, 2);
        assertTrue(editor.getPosition().getColumn() == 5);
        assertTrue(editor.getPosition().getLine() == 2);

        // Test line out of bounds. Should adjust to be within buffer limit.
        editor.setPosition(1,10);
        assertTrue(editor.getPosition().getColumn() == 1);
        assertTrue(editor.getPosition().getLine() == 7);

        // Test column out of bounds. Should adjust to be within buffer limit.
        editor.setPosition(5,3);
        assertTrue(editor.getPosition().getColumn() == 1);
        assertTrue(editor.getPosition().getLine() == 3);
        

    }

    /**
     * Test of moveDown method, of class TextFileEditor.
     */
    public void testMoveDown() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.moveDown(2);
        assertTrue(editor.getPosition().getLine() == 3);
    }

    /**
     * Test of moveUp method, of class TextFileEditor.
     */
    public void testMoveUp() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.setPosition(1,4);
        editor.moveUp(1);
        assertTrue(editor.getPosition().getLine() == 3);
    }

    /**
     * Test of moveRight method, of class TextFileEditor.
     */
    public void testMoveRight() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.moveRight(3);
        assertTrue(editor.getPosition().getColumn() == 4);
    }

    /**
     * Test of moveLeft method, of class TextFileEditor.
     */
    public void testMoveLeft() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.moveRight(9);
        editor.moveLeft(4);
        assertTrue(editor.getPosition().getColumn() == 6);
    }

    /**
     * Test of moveToNextChar method, of class TextFileEditor.
     */
    public void testMoveToNextChar() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.moveToNextChar();
        assertTrue(editor.getPosition().getColumn() == 2);
    }

    /**
     * Test of moveToPrevChar method, of class TextFileEditor.
     */
    public void testMoveToPrevChar() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.setPosition(5, 2);
        editor.moveToPrevChar();
        assertTrue(editor.getPosition().getColumn() == 4);
    }

    /**
     * Test of getChar method, of class TextFileEditor.
     */
    public void testGetChar() {
        TextFileEditor editor =  getTestBuffer1Editor();
        assertTrue(editor.getChar() == 'i');
    }

    /**
     * Test of moveToAndReturnNextChar method, of class TextFileEditor.
     */
    public void testMoveAndReturnNextChar() {
        TextFileEditor editor =  getTestBuffer1Editor();
        assertTrue(editor.moveToAndReturnNextChar() == 'm');
    }

    /**
     * Test of moveWordsRight method, of class TextFileEditor.
     */
    public void testMoveWordsRigth() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveWordsRight(2);
        //System.out.println("current word:[" + editor.getWord() + "]");
        assertTrue(editor.getWord().equals("dolor"));
    }

    /**
     * Test of moveWordsLeft method, of class TextFileEditor.
     */
    public void testMoveWordsLeft() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveToEndOfLine();
        editor.moveWordsLeft(4);
        //System.out.println("current word:[" + editor.getWord() + "]");
        assertTrue(editor.getWord().equals("tempor"));
    }

    /**
     * Test of moveToEndOfLine method, of class TextFileEditor.
     */
    public void testMoveToEndOfLine() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveToEndOfLine();
        assertTrue(editor.getPosition().getColumnNumber().equals(new TextPos(101)));
    }

    /**
     * Test of moveToBeginningOfLine method, of class TextFileEditor.
     */
    public void testMoveToBeginningOfLine() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveLeft(20);
        editor.moveToBeginningOfLine();
        assertTrue(editor.getPosition().getColumnNumber().equals(TextPos.FIRST));
    }

    /**
     * Test of moveToTopOfFile method, of class TextFileEditor.
     */
    public void testMoveToTopOfFile() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveDown(2);
        editor.moveToTopOfFile();
        assertTrue(editor.getPosition().getLineNumber().equals(TextPos.FIRST));
        assertTrue(editor.getPosition().getColumnNumber().equals(TextPos.FIRST));
    }

    /**
     * Test of moveToBottomOfFile method, of class TextFileEditor.
     */
    public void testMoveToBottomOfFile() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveToBottomOfFile();
        assertTrue(editor.getPosition().getLineNumber().equals(new TextPos(5)));
    }

    /**
     * Test of moveToEndOfFile method, of class TextFileEditor.
     */
    public void testMoveToEndOfFile() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveToEndOfFile();
        //System.out.println("pos:" + editor.getPosition());
        assertTrue(editor.getPosition().getLineNumber().equals(new TextPos(5)));
        assertTrue(editor.getPosition().getColumnNumber().equals(new TextPos(44)));
    }

    /**
     * Test of isOnFirstLine method, of class TextFileEditor.
     */
    public void testIsOnFirstLine() {
        TextFileEditor editor =  getLoremIpsumEditor();
        assertTrue(editor.isOnFirstLine());
    }

    /**
     * Test of isOnLastLine method, of class TextFileEditor.
     */
    public void testIsOnLastLine() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveToBottomOfFile();
        assertTrue(editor.isOnLastLine());
    }

    /**
     * Test of isOnFirstColumn method, of class TextFileEditor.
     */
    public void testIsOnFirstColumn() {
        TextFileEditor editor =  getLoremIpsumEditor();
        assertTrue(editor.isOnFirstColumn());
    }

    /**
     * Test of isOnLastColumn method, of class TextFileEditor.
     */
    public void testIsOnLastColumn() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.moveToEndOfLine();
        assertTrue(editor.isOnLastColumn());
    }

    /**
     * Test of find method, of class TextFileEditor.
     */
    public void testFind() {
        TextFileEditor editor = getTestBuffer1Editor();
        editor.find("import elsewhere");
        assertTrue("TextPos:Line number should be 2, but is " + editor.getPosition().getLineNumber() + "!", editor.getPosition().getLineNumber().equals(new TextPos(2)));
        assertTrue("int:Line number should be 2, but is " + editor.getPosition().getLine() + "!", editor.getPosition().getLine() == 2);
        assertTrue("TextPos:Column number should be 1, but is " + editor.getPosition().getColumnNumber() + "!", editor.getPosition().getColumnNumber().equals(TextPos.FIRST));
        assertTrue("int:Column number should be 1, but is " + editor.getPosition().getColumn() + "!", editor.getPosition().getColumn() == 1);

        editor.find("qwerty");
        assertTrue("int:Line number should be 4, but is " + editor.getPosition().getLine() + "!", editor.getPosition().getLine() == 4);
        assertTrue("int:Column number should be 14, but is " + editor.getPosition().getColumn() + "!", editor.getPosition().getColumn() == 14);
    }

    /**
     * Test of findNext method, of class TextFileEditor.
     */
    public void testFindNext() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.findNext("Test");
        assertTrue("int:Line number should be 4, but is " + editor.getPosition().getLine() + "!", editor.getPosition().getLine() == 4);
        assertTrue("int:Column number should be 2, but is " + editor.getPosition().getColumn() + "!", editor.getPosition().getColumn() == 2);

        editor.findNext("Test");
        assertTrue("int:Line number should be 5, but is " + editor.getPosition().getLine() + "!", editor.getPosition().getLine() == 5);
        assertTrue("int:Column number should be 2, but is " + editor.getPosition().getColumn() + "!", editor.getPosition().getColumn() == 2);
    }

    /**
     * Test of findLast method, of class TextFileEditor.
     */
    public void testFindLast() {
        TextFileEditor editor =  getLoremIpsumEditor();
        editor.findLast("dolor");
        //System.out.println("pos:" + editor.getPosition());
        assertTrue(editor.getPosition().getLineNumber().equals(new TextPos(4)));
        assertTrue(editor.getPosition().getColumnNumber().equals(new TextPos(6)));
    }

    /**
     * Test of findAllOccurences method, of class TextFileEditor.
     */
    public void testFindAllOccurences() {
        TextFileEditor editor =  getLoremIpsumEditor();
        List<Occurence> occurences = editor.findAllOccurences("dolor");
        //System.out.println("occurences.size():" + occurences.size());
        assertTrue(occurences.size() == 4);

        // Please note that find() and findLast() positions efter the found string, while
        // an Occurence points the the beginning of a match.

        Occurence occ = occurences.get(0);
        assertTrue(occ.getPosition().getLine() == 1);
        assertTrue(occ.getPosition().getColumn() == 13);

        occ = occurences.get(1);
        assertTrue(occ.getPosition().getLine() == 2);
        assertTrue(occ.getPosition().getColumn() == 4);

        occ = occurences.get(2);
        assertTrue(occ.getPosition().getLine() == 3);
        assertTrue(occ.getPosition().getColumn() == 50);

        occ = occurences.get(3);
        assertTrue(occ.getPosition().getLine() == 4);
        assertTrue(occ.getPosition().getColumn() == 1);
    }

    /**
     * Test of findLastOccurence method, of class TextFileEditor.
     */
    public void testFindLastOccurence() {
        TextFileEditor editor =  getLoremIpsumEditor();
        Occurence occ  = editor.findLastOccurence("dolor");
        assertTrue(occ.getPosition().getLine() == 4);
        assertTrue(occ.getPosition().getColumn() == 1);
    }

    /**
     * Test of findBlankLine method, of class TextFileEditor.
     */
    public void testFindBlankLine() {
        TextFileEditor editor =  getTestBuffer1Editor();
        assertTrue(editor.findBlankLine());
        assertTrue(editor.getPosition().getLine() == 3);
    }

    /**
     * Test of findNextBlankLine method, of class TextFileEditor.
     */
    public void testFindNextBlankLine() {
        TextFileEditor editor =  getTestBuffer1Editor();
        assertTrue(editor.findBlankLine());
        assertTrue(editor.findNextBlankLine());
        assertTrue(editor.getPosition().getLine() == 6);
    }


    /**
     * Test of findAllBlankLines method, of class TextFileEditor.
     */
    public void testFindAllBlankLines() {
        TextFileEditor editor =  getTestBuffer1Editor();
        List<Occurence> occurences = editor.findAllBlankLines();
        assertTrue(occurences.size() == 2);
        //System.out.println("pos:" + occurences.get(0).getPosition());
        assertTrue(occurences.get(0).getPosition().getLine() == 3);
        assertTrue(occurences.get(1).getPosition().getLine() == 6);
    }

    /**
     * Test of lineMatches method, of class TextFileEditor.
     */
    public void testLineMatches() {
        TextFileEditor editor =  getLoremIpsumEditor();
        assertTrue(editor.lineMatches(".*dolor.*sed do.*"));
    }

    /**
     * Test of nextLineMatches method, of class TextFileEditor.
     */
    public void testNextLineMatches() {
        TextFileEditor editor =  getLoremIpsumEditor();
        assertTrue(editor.nextLineMatches(".*dolore.*minim veniam,.*"));
    }

    /**
     * Test of findAllLinesMatching method, of class TextFileEditor.
     */
    public void testFindAllLinesMatching() {
        TextFileEditor editor =  getLoremIpsumEditor();
        List<Occurence> occurences = editor.findAllLinesMatching(".*dolor.*");

        //System.out.println("size:" + occurences.size());
        assertTrue(occurences.size() == 4);

        assertTrue(occurences.get(0).getPosition().getLine() == 1);

        assertTrue(occurences.get(1).getPosition().getLine() == 2);

        assertTrue(occurences.get(2).getPosition().getLine() == 3);

        assertTrue(occurences.get(3).getPosition().getLine() == 4);
    }

    /**
     * Test of startSelection & endSelection methods, of class TextFileEditor.
     */
    public void testSelection() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(2);
        editor.getPosition().setColumn(8);
        editor.startSelection();
        editor.getPosition().setColumn(21);
        editor.endSelection();
        assertTrue("", editor.getSelection().getStart().getLine() == 2);
        assertTrue("", editor.getSelection().getStart().getColumn() == 8);
        assertTrue("", editor.getSelection().getEnd().getLine() == 2);
        assertTrue("", editor.getSelection().getEnd().getColumn() == 21);
        assertTrue("Unexpected selection=[" + editor.getSelectedText().getLine(0) + "]", editor.getSelectedText().getLine(0).equals("elsewhere.some"));

        editor.getPosition().setLine(4);
        editor.getPosition().setColumn(2);
        editor.startSelection();
        editor.getPosition().setLine(5);
        editor.getPosition().setColumn(6);
        editor.endSelection();
        //System.out.println("selectionBuffer=[" + editor.getSelectedText() + "]");
        assertTrue("", editor.getSelection().getStart().getLine() == 4);
        assertTrue("", editor.getSelection().getStart().getColumn() == 2);
        assertTrue("", editor.getSelection().getEnd().getLine() == 5);
        assertTrue("", editor.getSelection().getEnd().getColumn() == 6);
        assertTrue("Expected buffer size to be 2 lines, but it is " + editor.getSelectedText().getSize() + " lines!", editor.getSelectedText().getSize() == 2);
        assertTrue("Unexpected selection=[" + editor.getSelectedText().getLine(0) + "]", editor.getSelectedText().getLine(0).equals("Test1(blaha=qwerty)"));
        assertTrue("Unexpected selection=[" + editor.getSelectedText().getLine(1) + "]", editor.getSelectedText().getLine(1).equals("@Test2"));
    }


    /**
     * Test of clearSelection method, of class TextFileEditor.
     */
    public void testClearSelection() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(2);
        editor.getPosition().setColumn(8);
        editor.startSelection();
        editor.getPosition().setColumn(21);
        editor.endSelection();
        assertTrue(editor.hasValidSelection());

        editor.clearSelection();
        assertTrue(!editor.hasValidSelection());
    }

    /**
     * Test of deleteSelection method, of class TextFileEditor.
     */
    public void testDeleteSelectionSingleLine() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(2);
        editor.getPosition().setColumn(8);
        editor.startSelection();
        editor.getPosition().setColumn(21);
        editor.endSelection();

        editor.deleteSelection();

        //System.out.println("testDeleteSelectionSingleLine():[" + editor.getTextBuffer() + "]");
        
        assertTrue("Line 4 should only have 'import .place;', but doesnt:[" + editor.getLine(2) + "]" , editor.getLine(2).equals("import .place;"));

    }

    /**
     * Test of deleteSelection method, of class TextFileEditor.
     */
    public void testDeleteSelectionMultiLine() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(4);
        editor.getPosition().setColumn(2);
        editor.startSelection();
        editor.getPosition().setLine(5);
        editor.getPosition().setColumn(6);
        editor.endSelection();

        editor.deleteSelection();

        //System.out.println("testDeleteSelectionMultiLine():[" + editor.getTextBuffer() + "]");

        assertTrue("Line 4 should only have an @, but doesnt:[" + editor.getLine(4) + "]" , editor.getLine(4).equals("@(qaz=wsx)"));

    }

    /**
     * Test of deleteSelection method, of class TextFileEditor.
     */
    public void testDeleteSelectionMultiLineFullLines() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(4);
        editor.getPosition().setColumn(1);
        editor.startSelection();
        editor.getPosition().setLine(5);
        editor.getPosition().setColumn(15);
        editor.endSelection();

        editor.deleteSelection();

        //System.out.println("testDeleteSelectionMultiLineFullLines():[" + editor.getTextBuffer() + "]");

        assertTrue("Line 4 should only have an @, but doesnt:[" + editor.getLine(4) + "]" , editor.getLine(4).equals("  "));

    }

    /**
     * Test of moveToMatching method, of class TextFileEditor.
     */
    public void testMoveToMatching() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.find("\\(");
        editor.moveToMatching('(', ')');
        //System.out.println("Current pos: " + editor.getPosition());
        assertTrue(editor.getPosition().getLine() == 4);
        assertTrue(editor.getPosition().getColumn() == 20);
    }

    /**
     * Test of selectToMatching method, of class TextFileEditor.
     */
    public void testSelectToMatching() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.find("\\(");
        editor.startSelection();
        editor.selectToMatching('(', ')');
        //System.out.println("selection: [" + editor.getSelection() + "], selection text:[" + editor.getSelectedTextAsString() + "]");
        assertTrue(editor.getSelectedTextAsString().equals("(blaha=qwerty)"));
    }

    /**
     * Test of replaceCurrentLine method, of class TextFileEditor.
     */
    public void testReplaceCurrentLine() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.replaceCurrentLine("replacement");
        assertTrue(editor.getTextBuffer().getSize() == 7);
        assertTrue(editor.getLine().equals("replacement"));
    }

    /**
     * Test of replaceFirstInCurrentLine method, of class TextFileEditor.
     */
    public void testReplaceFirstInCurrentLine() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.replaceFirstInCurrentLine("some", "every");
        assertTrue(editor.getLine().equals("import every.path.to.somewhere;"));
    }

    /**
     * Test of replaceAllInCurrentLine method, of class TextFileEditor.
     */
    public void testReplaceAllInCurrentLine() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.replaceAllInCurrentLine("some", "every");
        assertTrue(editor.getLine().equals("import every.path.to.everywhere;"));
    }

    /**
     * Test of replaceAllInFile method, of class TextFileEditor.
     */
    public void testReplaceAllInFile() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.replaceAllInFile("some", "every");
        assertTrue(editor.getLine().equals("import every.path.to.everywhere;"));
        assertTrue(editor.getLine(2).equals("import elsewhere.every.place;"));
    }

    /**
     * Test of getSelectedTextAsString method, of class TextFileEditor.
     */
    public void testGetSelectedTextAsString() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(4);
        editor.getPosition().setColumn(2);
        editor.startSelection();
        editor.getPosition().setLine(5);
        editor.getPosition().setColumn(6);
        editor.endSelection();

        assertTrue(editor.getSelectedTextAsString().equals("Test1(blaha=qwerty)\n@Test2"));
    }

    /**
     * Test of replaceSelectionWithTextBuffer method, of class TextFileEditor.
     */
    public void testReplaceSelectionWithTextBuffer() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(4);
        editor.getPosition().setColumn(2);
        editor.startSelection();
        editor.getPosition().setLine(5);
        editor.getPosition().setColumn(6);
        editor.endSelection();

        //System.out.println("selected Text = [" + editor.getSelectedTextAsString() + "]");
        editor.replaceSelectionWithTextBuffer(getTestReplaceBuffer1());

        //System.out.println("replace:[" + editor.getTextBuffer() + "]");

        assertTrue(editor.getTextBuffer().getSize() == 7);
        assertTrue(editor.getLine(4).equals("@Test3(tjohej=tjoho)"));
        assertTrue(editor.getLine(5).equals("@Test4(qaz=wsx)"));
    }

    /**
     * Test of insertBuffer method, of class TextFileEditor.
     */
    public void testInsertBuffer() {
        TextFileEditor editor = getTestBuffer1Editor();

        editor.getPosition().setLine(4);
        editor.getPosition().setColumn(8);
        editor.insertTextBuffer(getLoremIpsumBuffer());

        //System.out.println("insertBuffer:[" + editor.getTextBuffer() + "]");

        assertTrue(editor.getTextBuffer().getSize() == 11);
        assertTrue(editor.getLine(9).equals("@Test2(qaz=wsx)"));
    }

    /**
     * Test of appendBuffer method, of class TextFileEditor.
     */
    public void testAppendBuffer() {
        TextFileEditor editor = getTestBuffer1Editor();
        editor.appendBuffer(getLoremIpsumBuffer());
        assertTrue(editor.getTextBuffer().getSize() == 12);
        //System.out.println("line8:[" + editor.getLine(8) + "]");
        assertTrue(editor.getLine(8).startsWith("Lorem ipsum dolor"));
    }

    /**
     * Test of insertLine method, of class TextFileEditor.
     */
    public void testInsertLine() {
        TextFileEditor editor = getTestBuffer1Editor();
        editor.getPosition().setLine(5);
        editor.insertLine("@Test3");

        assertTrue(editor.getTextBuffer().getSize() == 8);
        assertTrue(editor.getLine(5).startsWith("@Test2"));
        assertTrue(editor.getLine(6).startsWith("@Test3"));
    }

    /**
     * Test of insertLineAbove method, of class TextFileEditor.
     */
    public void testInsertLineAbove() {
        TextFileEditor editor = getTestBuffer1Editor();
        editor.getPosition().setLine(5);
        editor.insertLineAbove("@Test1.5");

        assertTrue(editor.getTextBuffer().getSize() == 8);
        assertTrue(editor.getLine(6).startsWith("@Test2"));
        assertTrue(editor.getLine(5).startsWith("@Test1.5"));
    }

    /**
     * Test of deleteCurrentLine method, of class TextFileEditor.
     */
    public void testDeleteCurrentLine() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.deleteCurrentLine();
        assertTrue(editor.getTextBuffer().getSize() == 6);
        assertTrue(editor.getLine(1).equals("import elsewhere.some.place;"));
    }

    /**
     * Test of deleteCurrentWord method, of class TextFileEditor.
     */
    public void testDeleteCurrentWord_char() {
        TextFileEditor editor =  getTestBuffer1Editor();
        editor.deleteCurrentWord(' ');
        //System.out.println("line:[" + editor.getLine() + "]");
        assertTrue(editor.getLine().equals(" some.path.to.somewhere;"));

        editor.getPosition().setLine(7);
        editor.moveWordsRight(1);
        editor.deleteCurrentWord();
        //System.out.println("line:[" + editor.getLine() + "]");
        assertTrue(editor.getLine().equals("One  Three Four Five Six Seven Eight Nine Ten"));
    }

    public void testXMLReplaceBetween() {
        TextFileEditor editor = new TextFileEditor();
        editor.setTextBuffer(getXMLTestBuffer());
        assertTrue(editor.find("project"));
        assertTrue(editor.findFromCurrent("version"));
        editor.replaceCurrentLineBetween("<version>", "</version>", "5.8.2-SNAPSHOT");
        editor.moveToTopOfFile();
        assertTrue(editor.find("<version>5.8.2-SNAPSHOT</version>"));
    }
}
