import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
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

        long startTime = System.currentTimeMillis();
        drawCharacters(terminal, player, monsters, walls, loots, startTime);



        do {
            KeyStroke keyStroke = getUserKeyStroke(terminal);

            movePlayer(player, keyStroke, walls, loots);

            moveMonsters(player, monsters, terminal);

            drawCharacters(terminal, player, monsters, walls, loots, startTime);
            if (isGameWon(loots) && player.getX() == 78 && player.getY() == 21) {
                createWinningScreen(terminal,startTime);
            }

        } while (isPlayerAlive(player, monsters));

        createScreenOfDeath(terminal, startTime);
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
        char monsterSymbol = '\u046a';
        List<Monster> monsters = new ArrayList<>();
        monsters.add(new Monster(52, 3, monsterSymbol));
        monsters.add(new Monster(20, 10, monsterSymbol));
        monsters.add(new Monster(60, 3, monsterSymbol));
        monsters.add(new Monster(40, 20, monsterSymbol));
        return monsters;
    }

    private static List<Wall> createWall() {
        
        String path = "Wall.txt";
        File file = new File(path);
        List<Wall> walls = new ArrayList<>();
        try (Scanner in = new Scanner(file) ) {
            int y = 0;
            while (in.hasNextLine()){
                String line = in.nextLine();

                int x = 0;
                for (char c : line.toCharArray()) {
                    Wall wallObject = new Wall(x, y, c);
                    walls.add(wallObject);
                    x++;
                }
                y++;

            }

        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }


   return walls;


    }


    private static Terminal createTerminal() throws IOException {

        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory();
        Terminal terminal = terminalFactory.createTerminal();
        terminal.setCursorVisible(false);
        return terminal;
    }


    private static void drawCharacters(Terminal terminal, Player player, List<Monster> monsters,
                                       List<Wall> walls, List<Loot> loots, long startTime) throws IOException {




        for (Wall wall : walls) {
            terminal.setForegroundColor(TextColor.ANSI.YELLOW);
            terminal.setCursorPosition(wall.getX(), wall.getY());
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

        for (Monster monster : monsters) {
            terminal.setCursorPosition(monster.getPreviousX(), monster.getPreviousY());
            terminal.putCharacter(' ');

            terminal.setCursorPosition(monster.getX(), monster.getY());
            terminal.putCharacter(monster.getSymbol());
        }
        terminal.flush();


        for (Loot loot : loots) {
            terminal.setForegroundColor(new TextColor.RGB(255, 255, 0));
            terminal.setCursorPosition(loot.getX(), loot.getY());
            terminal.putCharacter(loot.getSymbol());
            terminal.resetColorAndSGR();

        }

        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(1).withRow(24));
        String timeCount = "TOTAL TIME: " + Long.toString((System.currentTimeMillis() - startTime)/1000) + " SEC ";
        for (char c : timeCount.toCharArray()) {
            terminal.putCharacter(c);
        }
        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(20).withRow(24));
        String lootStat = "   LOOTS LEFT: " + loots.size();
        for (char c : lootStat.toCharArray()) {
            terminal.putCharacter(c);
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

    private static void createWinningScreen(Terminal terminal, long startTime) throws IOException {
        terminal.resetColorAndSGR();
        terminal.enableSGR(SGR.BOLD);
        terminal.setForegroundColor(TextColor.ANSI.GREEN);
        terminal.enableSGR(SGR.BLINK);
        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(37).withRow(12));
        String message1 = "AWESOME!!";
        for (char c : message1.toCharArray()) {
            terminal.putCharacter(c);
        }

        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(35).withRow(13));
        String message2 = "YOU HAVE WON!";
        for (char c : message2.toCharArray()) {
            terminal.putCharacter(c);
        }

        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(31).withRow(14));
        String message3 = "YOU DID IT IN: " + Long.toString((System.currentTimeMillis() - startTime)/1000) + " Seconds";
        for (char c : message3.toCharArray()) {
            terminal.putCharacter(c);
        }
        terminal.flush();
        terminal.resetColorAndSGR();

    }

    private static void createScreenOfDeath(Terminal terminal, long startTime) throws IOException {

        terminal.resetColorAndSGR();
        terminal.enableSGR(SGR.BOLD);
        terminal.setForegroundColor(TextColor.ANSI.RED);
        terminal.enableSGR(SGR.BLINK);
        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(33).withRow(12));
        String message1 = "LOOOSER!!";
        for (char c : message1.toCharArray()) {
            terminal.putCharacter(c);
        }

        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(33).withRow(13));
        String message2 = "YOU SUCK!";
        for (char c : message2.toCharArray()) {
            terminal.putCharacter(c);
        }
        terminal.resetColorAndSGR();
        terminal.setForegroundColor(TextColor.ANSI.RED);
        terminal.enableSGR(SGR.BLINK);
        terminal.setCursorPosition(terminal.getCursorPosition().withColumn(28).withRow(14));
        String message3 = "\nYOU LASTED ONLY " + Long.toString((System.currentTimeMillis() - startTime)/1000) + " SECONDS";
        for (char c : message3.toCharArray()) {
            terminal.putCharacter(c);
        }
        terminal.flush();

    }
}
