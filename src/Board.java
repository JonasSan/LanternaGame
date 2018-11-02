/*
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;

public class Board {

   */
/* public static void printBoard(Terminal terminal) throws IOException {

        for (int column = 0; column < 80; column++) {
            terminal.setCursorPosition(column, 0); // go to position(column, row)
            terminal.putCharacter('\u25ad');
        }
        for (int column = 0; column < 80; column++) {
            terminal.setCursorPosition(column, 24); // go to position(column, row)
            terminal.putCharacter('\u25ad');
        }


        for (int row =0; row < 24; row++) {
            terminal.setCursorPosition(0, row); // go to position(column, row)
            terminal.putCharacter('\u25af');
        }
        for (int row = 0; row < 24; row++) {
            terminal.setCursorPosition(80, row); // go to position(column, row)
            terminal.putCharacter('\u25af');
        }
*//*

        for (int row = 15; row < 24; row++) {
            terminal.setCursorPosition(40, row); // go to position(column, row)
            terminal.putCharacter('\u25af');
        }
    }
    public static void moveCat() throws IOException, InterruptedException {

        // hide the terminal cursor
        terminal.setCursorVisible(false);

        // place three markers 'X', 'O' and a 'Z' on the screen at some positions.
        // Illustrates three ways to handle positions in lanterna
        terminal.setCursorPosition(5, 17);
        terminal.putCharacter('X');

        int markColumn = 3;
        int markRow = 1;
        terminal.setCursorPosition(markColumn, markRow);
        terminal.putCharacter('O');

        TerminalPosition terminalPosition = new TerminalPosition(8, 9);
        terminal.setCursorPosition(terminalPosition);
        terminal.putCharacter('Z');


        // You will be a 'block' (unicode 2588), starting at position (5,5)
        int x = 5;
        int y = 5;
        final char c = '\u005e';
        terminal.setCursorPosition(x, y);
        terminal.putCharacter(c);
        int a = 6;
        int b = 5;
        final char d = '\u005e';
        terminal.setCursorPosition(a, b);
        terminal.putCharacter(d);

        // read from keyboard input, same as previous lesson
        boolean continueReadingInput = true;
        while (continueReadingInput) {
            KeyStroke keyStroke = null;
            do {
                Thread.sleep(5);
                keyStroke = terminal.pollInput();
            } while (keyStroke == null);

            // Control the block using the arrow keys and switch-case.
            // When moving, we need to "erase" the previous position of the block.
            // We do it by putting SPACE on the previous position.
            // OBS! There exist the method "terminal.clearScreen()" that clears the screen. What are pros/cons using it?

            // To correctly update the drawing of the terminal we need to call terminal.flush() after the update
            // What happens if you catch one of the markers 'X', 'O' or 'Z'?

            boolean updatePosition = false;
            int oldX = x;
            int oldY = y;
            int oldA = a;
            int oldB = b;



            switch (keyStroke.getKeyType()) {
                case ArrowDown:
                    y += 1;
                    b +=1;
                    updatePosition = true;
                    break;
                case ArrowUp:
                    y -= 1;
                    b -=1;
                    updatePosition = true;
                    break;
                case ArrowRight:
                    x += 1;
                    // a += 1;
                    updatePosition = true;
                    break;
                case ArrowLeft:
                    x -= 1;
                    a -= 1;
                    updatePosition = true;
                    break;
            }
            if (updatePosition) {
                terminal.setCursorPosition(oldX, oldY);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(x, y);
                terminal.putCharacter(c);

                terminal.setCursorPosition(oldA, oldB);
                terminal.putCharacter(' ');
                terminal.setCursorPosition(a, b);
                terminal.putCharacter(d);
            }
            terminal.flush(); // don't forget to flush to update the terminal
        }
    }
}
*/
