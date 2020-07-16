package tictactoe;

public class TicTacToe {

    final int FIELD_SIZE = 3;

    private String[][] playingField;
    private int countX;
    private int countO;

    TicTacToe() {
        playingField = new String[3][3];
    }

    public void setPlayingField(String line) {

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                String symbol = line.valueOf(0);
                if ("_".equals(symbol)) {
                    playingField[i][j] = " ";
                } else {
                    playingField[i][j] = symbol;
                    if ("X".equals(symbol)) {
                        countX++;
                    } else {
                        countO++;
                    }
                }

                line = line.substring(1, line.length());
            }
        }
    }

    public void printPlayingField() {
        System.out.println("---------");
        for (int i = 0; i < FIELD_SIZE; i++) {
            System.out.print("| ");
            for (int j = 0; j < FIELD_SIZE; j++) {
                System.out.print(playingField[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    public boolean isEmpty(int i, int j) {
        if ("X".equals(playingField[i][j]) || "O".equals(playingField[i][j])) {
            return false;
        }

        return true;
    }

    public void fillCell(int x, int y) {
        String symbol = countO == countX ? "X" : "O";
        if (isEmpty(FIELD_SIZE - y - 1, x - 1)) {
            playingField[FIELD_SIZE - y - 1][x - 1] = symbol;
        } else {
            System.out.println("This cell is occupied! Choose another one!");
        }

        if ("X".equals(symbol)) {
            countX++;
        } else if ("O".equals(symbol)) {
            countO++;
        }
    }

}
