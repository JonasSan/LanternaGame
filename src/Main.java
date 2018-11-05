import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            startGame();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("Game over!");
        }

    }

    private static void startGame() throws IOException, InterruptedException {
        Terminal terminal = createTerminal();

        List<Wall> walls = createWall();
/*
List<Wall> walls = new ArrayList<>();
walls.add(0,new Wall(1,12,'x'));
walls.add(1,new Wall(1,12,'x'));
*/

        Player player = createPlayer();

        List<Monster> monsters = createMonsters();

        drawCharacters(terminal, player, monsters, walls);

        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal);

            movePlayer(player, keyStroke, walls);

            moveMonsters(player, monsters, terminal);

            drawCharacters(terminal, player, monsters, walls);

        } while (isPlayerAlive(player, monsters));



    }

    private static void moveMonsters(Player player, List<Monster> monsters, Terminal terminal) throws IOException {
        for (Monster monster : monsters) {
            monster.moveRandom(player, terminal);
        }
    }

    private static void movePlayer(Player player, KeyStroke keyStroke, List<Wall> walls) {



        switch (keyStroke.getKeyType()) {
            case ArrowUp:
                player.moveUp(walls);
                break;
            case ArrowDown:
                player.moveDown(walls);
                break;
            case ArrowLeft:
                player.moveLeft(walls);
                break;
            case ArrowRight:
                player.moveRight(walls);
                break;
        }

    }



    private static KeyStroke getUserKeyStroke(Terminal terminal) throws InterruptedException, IOException {
        KeyStroke keyStroke;
        do {
            Thread.sleep(5);
            keyStroke = terminal.pollInput();
        } while (keyStroke == null);
        return keyStroke;
    }

    private static Player createPlayer() {
        return new Player(10, 10, '\u0C24');

    }

    private static List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
//        monsters.add(new Monster(3, 3, 'X'));
//        monsters.add(new Monster(15, 7, 'X'));
//        monsters.add(new Monster(13, 3, 'X'));
//        monsters.add(new Monster(3, 10, 'X'));
        return monsters;
    }

    private static List<Wall> createWall() {
        List<Wall> walls = new ArrayList<>();
// fyra väggar i en ram
        for (int columns = 0; columns < 80; columns++) { // vägg 3
            walls.add(new Wall(0, columns, 'X'));
        }
        for (int columns = 0; columns < 80; columns++) { // vägg 4
            walls.add(new Wall(23, columns, 'X'));
        }
        for (int rows = 0; rows < 24; rows++) { // vägg 2
            walls.add(new Wall(rows, 0, 'X'));
        }
        for (int rows = 0; rows < 24; rows++) { // vägg 1
            walls.add(new Wall(rows, 79, 'X'));
        }

        for (int rows = 15; rows < 23; rows++) {
            walls.add(new Wall(rows, 40, 'O'));
        }



        return walls;
    }


    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        return terminal;
    }

    private static void drawCharacters(Terminal terminal, Player player, List<Monster> monsters, List<Wall> walls) throws IOException {
        for (Monster monster : monsters) {
            terminal.setCursorPosition(monster.getPreviousX(), monster.getPreviousY());
            terminal.putCharacter(' ');

            terminal.setCursorPosition(monster.getX(), monster.getY());
            terminal.putCharacter(monster.getSymbol());
        }

        for (Wall wall : walls) {
            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
            terminal.setCursorPosition(wall.getY(), wall.getX());
            terminal.putCharacter(wall.getSymbol());
        }

        terminal.resetColorAndSGR();
        terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
        terminal.putCharacter(' ');
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());

        terminal.flush();

    }

    private static boolean isPlayerAlive(Player player, List<Monster> monsters) {
        for (Monster monster : monsters) {
            if (monster.getX() == player.getX() && monster.getY() == player.getY()) {
                return false;
            }
        }
        return true;
    }

//    private static void createBoard(Terminal terminal)throws IOException{
//        for (int column = 0; column < 80; column++) {
//            terminal.setCursorPosition(column, 0); // go to position(column, row)
//            terminal.putCharacter('\u25ad');
//        }
//        for (int column = 0; column < 80; column++) {
//            terminal.setCursorPosition(column, 24); // go to position(column, row)
//            terminal.putCharacter('\u25ad');
//        }
//
//
//        for (int row =0; row < 24; row++) {
//            terminal.setCursorPosition(0, row); // go to position(column, row)
//            terminal.putCharacter('\u25af');
//        }
//        for (int row = 0; row < 24; row++) {
//            terminal.setCursorPosition(80, row); // go to position(column, row)
//            terminal.putCharacter('\u25af');
//        }
//    }




}
