package tictactoe;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter cells:");

        tictactoe.setPlayingField(scanner.next());

        tictactoe.printPlayingField();

        while(true) {
            System.out.println("Enter the coordinates:");
            String line = scanner.nextLine();
            if (line.matches("\\d \\d")) {
                int x = Integer.parseInt(line.substring(0, 1));
                int y = Integer.parseInt(line.substring(2));
                if (x > 0 && x < 4 && y > 0 && y < 4) {
                    if (tictactoe.tryFillCell(x, y)) {
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

        tictactoe.printPlayingField();

        if (tictactoe.getStatus() == GameStatus.X_WINS) {
            System.out.println("X wins");
        } else if (tictactoe.getStatus() == GameStatus.O_WINS) {
            System.out.println("O wins");
        } else if (tictactoe.getStatus() == GameStatus.GAME_NOT_FINISHED) {
            System.out.println("Game not finished");
        } else if (tictactoe.getStatus() == GameStatus.DRAW) {
            System.out.println("Draw");
        }


    }
}
