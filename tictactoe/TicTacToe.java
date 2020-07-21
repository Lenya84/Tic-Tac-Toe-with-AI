package tictactoe;

public class TicTacToe {

    final int FIELD_SIZE = 3;

    private char[][] playingField;
    private int countX;
    private int countO;
    private GameStatus status;

    TicTacToe() {
        playingField = new char[3][3];
        status = GameStatus.GAME_NOT_FINISHED;
    }

    public void setPlayingField(String line) {

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                char symbol = line.charAt(0);
                if (symbol == '_') {
                    playingField[i][j] = ' ';
                } else {
                    playingField[i][j] = symbol;
                    if (symbol == 'X') {
                        countX++;
                    } else {
                        countO++;
                    }
                }

                line = line.substring(1, line.length());
            }
        }
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
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
        return !('X' == playingField[i][j]) && !('O' == playingField[i][j]);
    }

    public boolean tryFillCell(int x, int y) {
        char symbol = countO == countX ? 'X' : 'O';
        if (isEmpty(FIELD_SIZE - y, x - 1)) {
            playingField[FIELD_SIZE - y][x - 1] = symbol;
        } else {
            return false;
        }

        if (symbol == 'X') {
            countX++;
        } else {
            countO++;
        }

        updateStatus();
        return true;
    }


    public boolean checkLanes(char symb) {
        boolean cols, rows;

        for (int i = 0; i < FIELD_SIZE; i++) {
            cols = true;
            rows = true;
            
            for (int j = 0; j < FIELD_SIZE; j++) {
                cols &= (playingField[i][j] == symb);
                rows &= (playingField[j][i] == symb);
            }
            
            if (cols || rows)
                return true;
        }
        return false;
    }
    
    public boolean checkDiagonals(char symb) {
        boolean toRight = true,
                toLeft = true;

        for (int i = 0; i < FIELD_SIZE; i++) {
            toRight &= (playingField[i][i] == symb);
            toLeft &= (playingField[FIELD_SIZE - i - 1][i] == symb);
        }

        return toRight || toLeft;
    }

    public void updateStatus() {
        if (checkLanes('X') || checkDiagonals('X')) {
            setStatus(GameStatus.X_WINS);
        } else if (checkLanes('O') || checkDiagonals('O')) {
            setStatus(GameStatus.O_WINS);
        } else if (countX + countO == FIELD_SIZE * FIELD_SIZE) {
            setStatus(GameStatus.DRAW);
        } else {
            setStatus(GameStatus.GAME_NOT_FINISHED);
        }
    }
}
