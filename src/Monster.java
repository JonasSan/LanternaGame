import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;

public class Monster {
    private int x;
    private int y;
    private char symbol;
    private int previousX;
    private int previousY;


    public Monster(int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.previousX = x;
        this.previousY = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void moveTowards(Player player) {
        // a monster wants to minimize the distance between itself and the player

        // Along which axis should the monster move in?
        // The monster will move in the direction in which the distance between monster and player is the largest.
        // Let's use the absolute value of the difference between the x-ccordinates vs the y-coordinates!
        // Example of Math.abs -> https://www.tutorialspoint.com/java/lang/math_abs_int.htm

        previousX = x;
        previousY = y;

        int diffX = this.x - player.getX();
        int absDiffX = Math.abs(diffX);
        int diffY = this.y - player.getY();
        int absDiffY = Math.abs(diffY);


        if (absDiffX > absDiffY) {
            // Move horizontal! <--->
            if (diffX < 0) {
                this.x += 1;
            } else {
                this.x -= 1;
            }
        } else if (absDiffX < absDiffY) {
            // Move vertical! v / ^
            if (diffY < 0) {
                this.y += 1;
            } else {
                this.y -= 1;
            }
        } else {
            // Move diagonal! / or \
            if (diffX < 0) {
                this.x += 1;
            } else {
                this.x -= 1;
            }
            if (diffY < 0) {
                this.y += 1;
            } else {
                this.y -= 1;
            }
        }
    }

    public void moveRandom(Player player, Terminal terminal) throws IOException {

        previousX = x;
        previousY = y;

        int diffX = this.x - player.getX();
        int absDiffX = Math.abs(diffX);
        int diffY = this.y - player.getY();
        int absDiffY = Math.abs(diffY);

        Random random = new Random();

        if (absDiffX < 3 && absDiffY < 3) {
            moveTowards(player);
        } else {
            int rand = random.nextInt(5);
            int futureX;
            int futureY;
            switch (rand) {
                case 1:
                    this.x += 1;
                    break;
                case 2:
                    this.y += 1;
                    break;
                case 3:
                    this.x -= 1;
                    break;
                case 4:
                    this.y -= 1;
                    break;
            }


        }
    }


    @Override
    public String toString() {
        return "Monster{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", previousX=" + previousX +
                ", previousY=" + previousY +
                '}';

    }
}
