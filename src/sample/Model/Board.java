package src.sample.Model;

public class Board {
    public int line;
    public int column;
    public boolean inGame = true;
    public Cell [][] waffle;

    public Board(int line, int column) {
        this.line = line;
        this.column = column;
        this.waffle = new Cell[line][column];

        for (int i = 0; i < this.line; i++) {
            for (int j = 0; j < this.column; j++) {
                Cell c = new Cell(Cell.FULLCELL);
                waffle[i][j] = c;
            }
        }
        waffle[0][0] = new Cell(Cell.POISONEDCELL);
    }

    public int play(int line, int column) {
        if (line > -1 && line < this.line && column > -1 && column < this.column && waffle[line][column].isPlayable()) {
            if (waffle[line][column].type == Cell.POISONEDCELL) {
                inGame = false;
                // Perdu
                return 2;
            } else {
                updateWaffle(line,column);
                return 0;
            }
        } else {
            // Case non valide
            return 1;
        }
    }

    private void updateWaffle(int line, int column) {
        for (int i = line; i < this.line; i++) {
            for (int j = column; j < this.column; j++) {
                if (waffle[i][j].type == Cell.FULLCELL) {
                    waffle[i][j].type = Cell.EMPTYCELL;
                }
            }
        }
    }
}
