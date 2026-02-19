public class Board {
    private int size;
    private Symbol[][] grid;

    public Board(int size) {
        this.size = size;
        this.grid = new Symbol[size][size];
    }

    public boolean isCellEmpty(int r, int c) {
        return r >= 0 && r < size && c >= 0 && c < size && grid[r][c] == null;
    }

    public void applyMove(Move move) {
        grid[move.getRow()][move.getCol()] = move.getSymbol();
    }

    public boolean checkWinner(Symbol s) {
        
        for (int i = 0; i < size; i++) {
            boolean rowWin = true, colWin = true;
            for (int j = 0; j < size; j++) {
                if (grid[i][j] != s) rowWin = false;
                if (grid[j][i] != s) colWin = false;
            }
            if (rowWin || colWin) return true;
        }
        
        boolean diag = true, antiDiag = true;
        for (int i = 0; i < size; i++) {
            if (grid[i][i] != s) diag = false;
            if (grid[i][size - 1 - i] != s) antiDiag = false;
        }
        return diag || antiDiag;
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == null) return false;
            }
        }
        return true;
    }

    public void display() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print((grid[i][j] == null ? "-" : grid[i][j]) + " ");
            }
            System.out.println();
        }
    }
}
