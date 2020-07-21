package tictactoe;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TicTacToe tictactoe = new TicTacToe();
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random(10123123);
        int randomX;
        int randomY;

        tictactoe.printPlayingField();

        while(true) {
            System.out.print("Enter the coordinates: ");
            String line = scanner.nextLine();

            if (line.matches("\\d \\d")) {
                int x = Integer.parseInt(line.substring(0, 1));
                int y = Integer.parseInt(line.substring(2));

                if (x > 0 && x < 4 && y > 0 && y < 4) {
                    if (tictactoe.tryFillCell(x, y) && tictactoe.getStatus() == GameStatus.GAME_NOT_FINISHED) {
                        tictactoe.printPlayingField();
                        System.out.println("Making move level \"easy\"");

                        do {
                            randomX = rand.nextInt(3) + 1;
                            randomY = rand.nextInt(3) + 1;
                        } while (!tictactoe.tryFillCell(randomX, randomY));

                        tictactoe.printPlayingField();
                    } else if (tictactoe.getStatus() != GameStatus.GAME_NOT_FINISHED) {
                        tictactoe.printPlayingField();
                        tictactoe.checkWinner();
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
    }
}
