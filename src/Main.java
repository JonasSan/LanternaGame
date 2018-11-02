import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.nio.charset.Charset;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {


        try {

            Board.printBoard(); // LESSON 5
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("DONE!");
        }
    }
    public static void runGame () {
        DefaultTerminalFactory terminalFactory =
                        new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF8"));
                Terminal terminal = terminalFactory.createTerminal(); // most terminal methods can throw IOException
}

}
