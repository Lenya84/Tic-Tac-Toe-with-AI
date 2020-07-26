package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        Player playerX = new Player(tictactoe);
        Player playerO = new Player(tictactoe);

        while (true) {
            String[] params = scanner.nextLine().split(" ");
            if (params.length == 3 && "start".equals(params[0])) {
                tictactoe.newGame();
                playerX.setMethod(params[1]);
                playerO.setMethod(params[2]);
            } else if (params.length == 1 && "exit".equals(params[0])) {
                break;
            } else {
                System.out.println("Bad parameters!");
            }

            while (tictactoe.getStatus() == GameStatus.GAME_NOT_FINISHED) {
                playerX.move();
                if (tictactoe.getStatus() != GameStatus.GAME_NOT_FINISHED) {
                    break;
                }
                playerO.move();
            }

            tictactoe.printResult();

        }
    }
}