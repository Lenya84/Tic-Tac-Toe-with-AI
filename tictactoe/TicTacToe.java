package tictactoe;

public class TicTacToe {

    final int FIELD_SIZE = 3;
    final int MAX_MOVE_COUNT = FIELD_SIZE * FIELD_SIZE;

    public char[][] playingField;
    private int countX;
    private int countO;
    private GameStatus status;

    TicTacToe() {
        playingField = new char[][] {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '},
        };
        status = GameStatus.GAME_NOT_FINISHED;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getCountX() {
        return countX;
    }

    public int getCountO() {
        return countO;
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

                line = line.substring(1);
            }
        }
    }

    public void newGame() {
        playingField = new char[][]{
                {' ', ' ', ' '},
                {' ', ' ', ' '},
                {' ', ' ', ' '},
        };
        status = GameStatus.GAME_NOT_FINISHED;
        countX = 0;
        countO = 0;
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

    public boolean cellIsEmpty(int x, int y) {
        return !('X' == playingField[FIELD_SIZE - y][x - 1]) && !('O' == playingField[FIELD_SIZE - y][x - 1]);
    }

    public void fillCell(int x, int y) {
        char symbol = countO == countX ? 'X' : 'O';

        if (cellIsEmpty(x, y)) {
            playingField[FIELD_SIZE - y][x - 1] = symbol;
        }

        if (symbol == 'X') {
            countX++;
        } else {
            countO++;
        }

        updateStatus();
    }



    public int[] getWinningCell(char symb) {
        int diagonalToRightCount = 0;
        int diagonalToLeftCount = 0;
        int rowCount = 0;
        int colCount = 0;
        int[] winningRowCell = null;
        int[] winningColCell = null;
        int[] winningDiagonalToRightCell = null;
        int[] winningDiagonalToLeftCell = null;

        for (int i = 0; i < FIELD_SIZE; i++) {
            if (playingField[i][i] == symb) {
                diagonalToRightCount++;
            }
            if (playingField[i][i] == ' ') {
                winningDiagonalToRightCell = new int[] {i, i};
            }

            if (playingField[FIELD_SIZE - i - 1][i] == symb) {
                diagonalToLeftCount++;
            }
            if (playingField[FIELD_SIZE - i - 1][i] == ' ') {
                winningDiagonalToLeftCell = new int[] {i, i};
            }
        }

        if (diagonalToRightCount == 2 && winningDiagonalToRightCell != null) {
            return winningDiagonalToRightCell;
        } else if (diagonalToLeftCount == 2 && winningDiagonalToLeftCell != null) {
            return winningDiagonalToLeftCell;
        }

        for (int i = 0; i < FIELD_SIZE; i++) {
            for (int j = 0; j < FIELD_SIZE; j++) {
                if (playingField[i][j] == symb) {
                    rowCount++;
                }
                if (playingField[i][j] == ' ') {
                    winningRowCell = new int[]{i, j};
                }

                if (playingField[j][i] == symb) {
                    colCount++;
                }
                if (playingField[j][i] == ' ') {
                    winningColCell = new int[]{j, i};
                }
            }

            if (rowCount == 2 && winningRowCell != null) {
                return winningRowCell;
            } else if (colCount == 2 && winningColCell != null) {
                return winningColCell;
            }

            rowCount = 0;
            colCount = 0;
            winningRowCell = null;
            winningColCell = null;
        }

        return null;
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

    public boolean checkLanes(char[][] playingField, char symb) {
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

    public boolean checkDiagonals(char[][] playingField, char symb) {
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
        } else if (countX + countO == MAX_MOVE_COUNT) {
            setStatus(GameStatus.DRAW);
        } else {
            setStatus(GameStatus.GAME_NOT_FINISHED);
        }
    }

    public void printResult() {
        if (getStatus() == GameStatus.X_WINS) {
            System.out.println("X wins");
        } else if (getStatus() == GameStatus.O_WINS) {
            System.out.println("O wins");
        } else if (getStatus() == GameStatus.GAME_NOT_FINISHED) {
            System.out.println("Game not finished");
        } else if (getStatus() == GameStatus.DRAW) {
            System.out.println("Draw");
        }
    }
}
