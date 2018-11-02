import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.nio.charset.Charset;

public class Game {
    public static void runGame () {
        DefaultTerminalFactory terminalFactory =
                new DefaultTerminalFactory(System.out, System.in, Charset.forName("UTF8"));
        Terminal terminal = terminalFactory.createTerminal();
    }

}
