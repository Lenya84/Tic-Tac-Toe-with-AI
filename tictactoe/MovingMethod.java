package tictactoe;

import java.util.Random;
import java.util.Scanner;

public abstract class MovingMethod {

    protected TicTacToe tictactoe;

    public MovingMethod(TicTacToe tictactoe) {
        this.tictactoe = tictactoe;
    }

    public void move() { };
}

class UserMovingMethod extends MovingMethod {

    public UserMovingMethod(TicTacToe tictactoe) {
        super(tictactoe);
    }

    @Override
    public void move() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the coordinates: ");
        String line = scanner.nextLine();
        scanner.close();

        if (line.matches("\\d \\d")) {
            int x = Integer.parseInt(line.substring(0, 1));
            int y = Integer.parseInt(line.substring(2));

            if (x > 0 && x < 4 && y > 0 && y < 4) {
                if (tictactoe.cellIsEmpty(x, y) && tictactoe.getStatus() == GameStatus.GAME_NOT_FINISHED) {
                    tictactoe.fillCell(x, y);
                    tictactoe.printPlayingField();
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                }
            } else {
                System.out.println("Coordinates should be from 1 to 3!");
            }
        } else {
            System.out.println("You should enter numbers!");
        }
    }
}

class EasyLvlMovingMethod extends MovingMethod {

    public EasyLvlMovingMethod(TicTacToe tictactoe) {
        super(tictactoe);
    }

    @Override
    public void move() {
        Random rand = new Random(10123123);
        int randomX;
        int randomY;

        System.out.println("Making move level \"easy\"");

        while (true) {
            randomX = rand.nextInt(3) + 1;
            randomY = rand.nextInt(3) + 1;

            if (tictactoe.cellIsEmpty(randomX, randomY)) {
                tictactoe.fillCell(randomX, randomY);
                break;
            }
        }

        tictactoe.printPlayingField();
    }
}

class Player extends MovingMethod {

    private MovingMethod method;

    public Player(TicTacToe tictactoe) {
        super(tictactoe);
    }

    public void setMethod(MovingMethod method) {
        this.method = method;
    }

    public void setMethod(String movingMethod) {
        switch (movingMethod) {
            case "easy":
                setMethod(new EasyLvlMovingMethod(tictactoe));
                break;
            case "user":
                setMethod(new UserMovingMethod(tictactoe));
                break;
            default:
                setMethod(new EasyLvlMovingMethod(tictactoe));
                System.out.println("Error");
        }
    }

    public void move() {
        this.method.move();
    }
}
