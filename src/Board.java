import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.nio.charset.Charset;

public class Board {

    public static void printBoard() throws IOException {
        // Create a "factory object" (it's a design-pattern) that can create a terminal for us
        DefaultTerminalFactory terminalFactory =
                new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF8"));
        Terminal terminal = terminalFactory.createTerminal(); // most terminal methods can throw IOException

        // Write out a couple of 'X'
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

        for (int row = 15; row < 24; row++) {
            terminal.setCursorPosition(40, row); // go to position(column, row)
            terminal.putCharacter('\u25af');
        }
    }
}
