package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        Player playerX = new Player(tictactoe);
        Player playerO = new Player(tictactoe);
        String modeX = "";
        String modeO = "";

        while (true) {
            String[] params = scanner.nextLine().split(" ");
            if (params.length == 3 && "start".equals(params[0])) {
                tictactoe.newGame();
                playerX.setMethod(params[1]);
                modeX = params[1];
                playerO.setMethod(params[2]);
                modeO = params[2];
            } else if (params.length == 1 && "exit".equals(params[0])) {
                break;
            } else {
                System.out.println("Bad parameters!");
                continue;
            }

            while (tictactoe.getStatus() == GameStatus.GAME_NOT_FINISHED) {
                if (!modeX.equals("user"))
                    System.out.println("Making move level " + modeX);
                playerX.move();
                tictactoe.printPlayingField();
                if (tictactoe.getStatus() != GameStatus.GAME_NOT_FINISHED) {
                    break;
                }
                if (!modeO.equals("user"))
                    System.out.println("Making move level " + modeO);
                playerO.move();
                tictactoe.printPlayingField();
            }

            tictactoe.printResult();

        }
    }
}