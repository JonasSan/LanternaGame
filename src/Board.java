//import com.googlecode.lanterna.TerminalPosition;
//import com.googlecode.lanterna.input.KeyStroke;
//import com.googlecode.lanterna.input.KeyType;
//import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
//import com.googlecode.lanterna.terminal.Terminal;
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//
//public class Board {
//
//    private int column;
//    private int row;
//
//
//    public Board(int column, int row) {
//        this.column = column;
//        this.row = row;
//    }
//
//    public int getColumn() {
//        return column;
//    }
//
//    public void setColumn(int column) {
//        this.column = column;
//    }
//
//    public int getRow() {
//        return row;
//    }
//
//    public void setRow(int row) {
//        this.row = row;
//    }
//
//
//    private void createBoard(Terminal terminal) throws IOException {
//
//        for (column = 0; column < 80; column++) {
//            terminal.setCursorPosition(column, 0); // go to position(column, row)
//            terminal.putCharacter('\u25ad');
//        }
//        for (column = 0; column < 80; column++) {
//            terminal.setCursorPosition(column, 24); // go to position(column, row)
//            terminal.putCharacter('\u25ad');
//        }
//
//
//        for (row = 0; row < 24; row++) {
//            terminal.setCursorPosition(0, row); // go to position(column, row)
//            terminal.putCharacter('\u25af');
//        }
//        for (row = 0; row < 24; row++) {
//            terminal.setCursorPosition(80, row); // go to position(column, row)
//            terminal.putCharacter('\u25af');
//        }
//
//
//    }
//}
