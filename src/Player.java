import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Player {
    private int x;
    private int y;
    private char symbol;
    private int previousX;
    private int previousY;

    public Player(int x, int y, char symbol) {
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

    public void moveUp(List<Wall> walls) {
        int futureX = x;
        int futureY = y-1;

        if (isWallNotHit(futureX, futureY, walls)) {
            previousX = x;
            previousY = y;
            y--;
        } else {
            previousX = x;
            previousY = y;
        }
    }

    public void moveDown(List<Wall> walls) {
        int futureX = x;
        int futureY = y+1;

        if (isWallNotHit(futureX, futureY, walls)) {
            previousX = x;
            previousY = y;
            y++;
        } else {
            previousX = x;
            previousY = y;
        }
    }

    public void moveLeft(List<Wall> walls) {
        int futureX = x - 1;
        int futureY = y;

        if (isWallNotHit(futureX, futureY, walls)) {
            previousX = x;
            previousY = y;
            x--;
        } else {
            previousX = x;
            previousY = y;
        }
    }

    public void moveRight(List<Wall> walls) {

        int futureX = x + 1;
        int futureY = y;


        if (isWallNotHit(futureX, futureY, walls)) {
            previousX = x;
            previousY = y;
            x++;
        } else {
            previousX = x;
            previousY = y;
        }
    }


    public boolean isWallNotHit(int futureX, int futureY, List<Wall> walls) {
/*        for (Wall wall : walls) {
        if (x == (wall.getX()-1)) { // vägg 1
            return false;
            } else if (x == (wall.getX() + 1) ) { // vägg 2
                return false;
            } else if (y == (wall.getY() + 1)) { // vägg 3
                return false;
            } else if (y == (wall.getY() - 1)) { // vägg 4
                return false;
            }


        }
        return true;*/

/*
       for (Wall w : walls) {
           if ((w.getX() == x-1 && w.getY() == y-1) ||(w.getY() == y+1) && w.getY() == y) {
               return false;
           }
       }
       return true;*/
        for (Wall wall : walls) {
            if (futureX == wall.getY() && futureY == wall.getX()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", previousX=" + previousX +
                ", previousY=" + previousY +
                '}';
    }
}
