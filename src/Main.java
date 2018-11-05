import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;


import java.nio.charset.Charset;
import java.io.IOException;


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            startGame();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        } finally {
            System.out.println("Game over!");
        }

    }

    private static void startGame() throws IOException, InterruptedException {
        Terminal terminal = createTerminal();

        List<Wall> walls = createWall();


        Player player = createPlayer();

        List<Monster> monsters = createMonsters();

        List<Loot> loots = createLoots();

        drawCharacters(terminal, player, monsters, walls, loots);


        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal);

            movePlayer(player, keyStroke, walls, loots);

            moveMonsters(player, monsters, terminal);

            drawCharacters(terminal, player, monsters, walls, loots);
            if (isGameWon(loots) && player.getX() == 78 && player.getY() == 22) {
                createWinningScreen(terminal);
            }


        } while (isPlayerAlive(player, monsters));


    }

    private static List<Loot> createLoots() {
        List<Loot> loots = new ArrayList<>();
        loots.add(new Loot(70, 20, '\u229A'));
        loots.add(new Loot(15, 10, '\u229A'));
        loots.add(new Loot(13, 5, '\u229A'));
        return loots;
    }


    private static void moveMonsters(Player player, List<Monster> monsters, Terminal terminal) throws IOException {
        for (Monster monster : monsters) {
            monster.moveRandom(player, terminal);
        }
    }

    private static void movePlayer(Player player, KeyStroke keyStroke, List<Wall> walls, List<Loot> loots) {
        for (int i = 0; i < loots.size(); i++) {
            if (player.getX() == loots.get(i).getX() && player.getY() == loots.get(i).getY()) {
                loots.remove(i);
            }
        }

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

    private static Player createPlayer() throws IOException {

        return new Player(1, 1, '\u047E');


    }

    private static List<Monster> createMonsters() {
        List<Monster> monsters = new ArrayList<>();
      /*  monsters.add(new Monster(50, 3, 'X'));
        monsters.add(new Monster(15, 7, 'X'));
        monsters.add(new Monster(13, 3, 'X'));
        monsters.add(new Monster(40, 10, 'X'));*/
        return monsters;
    }

    private static List<Wall> createWall() {
        List<Wall> walls = new ArrayList<>();

        char outerWallSymbol = 'X';
        char innerWallSymbol = 'O';
// fyra väggar i en ram
        for (int columns = 0; columns < 80; columns++) { // vägg 3
            walls.add(new Wall(0, columns, outerWallSymbol));
        }
        for (int columns = 0; columns < 80; columns++) { // vägg 4
            walls.add(new Wall(23, columns, outerWallSymbol));
        }
        for (int rows = 0; rows < 24; rows++) { // vägg 2
            walls.add(new Wall(rows, 0, outerWallSymbol));
        }
        for (int rows = 0; rows < 24; rows++) { // vägg 1
            walls.add(new Wall(rows, 79, outerWallSymbol));
        }
        // Vertikala väggar
        for (int rows = 1; rows < 5; rows++) { // innervägg 1
            walls.add(new Wall(rows, 7, innerWallSymbol));
        }

        for (int rows = 4; rows < 10; rows++) { // 2
            walls.add(new Wall(rows, 4, innerWallSymbol));
        }

        for (int rows = 12; rows < 14; rows++) { // 6
            walls.add(new Wall(rows, 15, innerWallSymbol));

        }
        for (int rows = 15; rows < 19; rows++) { // 9
            walls.add(new Wall(rows, 8, innerWallSymbol));

        }

        // Horisontella väggar

        for (int columns = 1; columns < 4; columns++) { // 3
            walls.add(new Wall(6, columns, innerWallSymbol));
        }

        for (int columns = 1; columns < 15; columns++) { // 4
            walls.add(new Wall(12, columns, innerWallSymbol));
        }
        for (int columns = 15; columns < 20; columns++) { // 5
            walls.add(new Wall(14, columns, innerWallSymbol));
        }
        for (int columns = 3; columns < 8; columns++) { // 7
            walls.add(new Wall(15, columns, innerWallSymbol));
        }
        for (int columns = 0; columns < 8; columns++) { // 8
            walls.add(new Wall(18, columns, innerWallSymbol));
        }

        return walls;
    }


    private static Terminal createTerminal() throws IOException {
        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        return terminal;
    }

    private static boolean isLootTaken(Player player, List<Loot> loots) {
        for (Loot loot : loots) {
            if (player.getX() == loot.getX() && player.getY() == loot.getY()) {
                return true;

            }
        }
        return false;
    }


    private static void drawCharacters(Terminal terminal, Player player, List<Monster> monsters,
                                       List<Wall> walls, List<Loot> loots) throws IOException {
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
            terminal.resetColorAndSGR();
        }


        terminal.resetColorAndSGR();
        terminal.setCursorPosition(player.getPreviousX(), player.getPreviousY());
        terminal.putCharacter(' ');

        terminal.setForegroundColor(new TextColor.RGB(255, 0, 102));
        terminal.setCursorPosition(player.getX(), player.getY());
        terminal.putCharacter(player.getSymbol());
        terminal.resetColorAndSGR();

        terminal.flush();


        for (Loot loot : loots) {
            terminal.setForegroundColor(new TextColor.RGB(255, 255, 0));
            terminal.setCursorPosition(loot.getX(), loot.getY());
            terminal.putCharacter(loot.getSymbol());
            terminal.resetColorAndSGR();

        }

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

    private static boolean isGameWon(List<Loot> loots) {
        if (loots.size() == 0) {
            return true;
        }
        return false;
    }

    private static void createWinningScreen(Terminal terminal) throws IOException {



        terminal.resetColorAndSGR();
        terminal.enableSGR(SGR.BOLD);
        terminal.setForegroundColor(TextColor.ANSI.GREEN);
        terminal.enableSGR(SGR.BLINK);
        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(32).withRow(12));
        String message1 = "AWESOME!! \n\t\t\t\t\t\t\t YOU HAVE WON!";
        for (char c : message1.toCharArray()) {
            terminal.putCharacter(c);
        }




       /* for (int i = 32; i < message1.length() + 32; i++) {


            terminal.setCursorPosition(i, 12);
            terminal.putCharacter(message1.charAt(i - 32));
        }
        String message = "YOU HAVE WON!";
        for (int i = 32; i < message.length() + 32; i++) {
            terminal.setCursorPosition(i, 12);
            terminal.putCharacter(message.charAt(i - 32));
            terminal.resetColorAndSGR();
            terminal.flush();
        }*/
    }

}
