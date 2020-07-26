package tictactoe;

import java.util.Random;
import java.util.Scanner;

public abstract class MovingMethod {

    protected TicTacToe tictactoe;

    public MovingMethod(TicTacToe tictactoe) {
        this.tictactoe = tictactoe;
    }

    public void move() { }
}

class UserMovingMethod extends MovingMethod {

    public UserMovingMethod(TicTacToe tictactoe) {
        super(tictactoe);
    }

    @Override
    public void move() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter the coordinates: ");
            String line = scanner.nextLine();

            if (line.matches("\\d \\d")) {
                int x = Integer.parseInt(line.substring(0, 1));
                int y = Integer.parseInt(line.substring(2));

                if (x > 0 && x < 4 && y > 0 && y < 4) {
                    if (tictactoe.cellIsEmpty(x, y) && tictactoe.getStatus() == GameStatus.GAME_NOT_FINISHED) {
                        tictactoe.fillCell(x, y);
                        tictactoe.printPlayingField();
                        break;
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

        scanner.close();
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

class MediumLvlMovingMethod extends MovingMethod {
    private EasyLvlMovingMethod easyMove;

    public MediumLvlMovingMethod(TicTacToe tictactoe) {
        super(tictactoe);
        easyMove = new EasyLvlMovingMethod(tictactoe);
    }

    public void move() {
        char symb = tictactoe.getCountO() == tictactoe.getCountX() ? 'X' : 'O';

        int[] cell = tictactoe.getWinningCell(symb);

        System.out.println("Making move level \"easy\"");

        if (cell != null) {
            tictactoe.fillCell(cell[1] + 1, tictactoe.FIELD_SIZE - cell[0]);
        } else {
            cell = tictactoe.getWinningCell(symb == 'X' ? 'O' : 'X');
            if (cell != null) {
                tictactoe.fillCell(cell[1] + 1, tictactoe.FIELD_SIZE - cell[0]);
            } else {
                easyMove.move();
            }
        }

        tictactoe.printPlayingField();
    }
}

class HardLvlMovingMethod extends MovingMethod {

    private char human;
    private char ai;

    HardLvlMovingMethod(TicTacToe tictactoe) {
        super(tictactoe);
    }

    public String checkWinner(char[][] playingField) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (playingField[i][j] == ' ') {
                    count++;
                }
            }
        }

        if (count == 0) {
            return "draw";
        } else if (tictactoe.checkDiagonals(playingField, ai) || tictactoe.checkLanes(playingField, ai)) {
            return "win";
        } else if (tictactoe.checkDiagonals(playingField, ai == 'X' ? 'O' : 'X') ||
                    tictactoe.checkLanes(playingField, ai == 'X' ? 'O' : 'X')) {
            return "lose";
        }

        return null;
    }

    public int minimax(char[][] playingField, boolean myMove) {
        String  status = checkWinner(playingField);

        if ("win".equals(status)) {
            return 1;
        } else if ("draw".equals(status)){
            return 0;
        } else if ("lose".equals(status)) {
            return -1;
        }

        if (myMove) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (playingField[i][j] == ' ') {
                        playingField[i][j] = ai;
                        int score = minimax(playingField.clone(), false);
                        playingField[i][j] = ' ';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (playingField[i][j] == ' ') {
                        playingField[i][j] = human;
                        int score = minimax(playingField.clone(), true);
                        playingField[i][j] = ' ';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }
    }

    public void move() {
        System.out.println("Making move level \"hard\"");
        ai = tictactoe.getCountO() == tictactoe.getCountX() ? 'X' : 'O';
        human = ai == 'X' ? 'O' : 'X';
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];
        for (int i = 0; i < tictactoe.FIELD_SIZE; i++) {
            for (int j = 0; j < tictactoe.FIELD_SIZE; j++) {
                if (tictactoe.playingField[i][j] == ' ') {
                    tictactoe.playingField[i][j] = ai;
                    int score = minimax(tictactoe.playingField.clone(), true);
                    tictactoe.playingField[i][j] = ' ';
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove = new int[]{i, j};
                    }
                }
            }
        }

        tictactoe.fillCell(bestMove[1] + 1, tictactoe.FIELD_SIZE - bestMove[0]);
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
            case "medium":
                setMethod(new MediumLvlMovingMethod(tictactoe));
                break;
            case "hard":
                setMethod(new HardLvlMovingMethod(tictactoe));
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




