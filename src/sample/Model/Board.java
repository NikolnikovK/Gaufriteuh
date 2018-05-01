package src.sample.Model;

public class Board {
    public int line;
    public int column;
    public boolean inGame = true;
    public Cell [][] waffle;

    public Board(int line, int column) throws Exception {
        if (line > 1 && column > 1) {
            this.line = line;
            this.column = column;
            this.waffle = new Cell[line][column];

            initializeWaffle();
        } else {
            throw new Exception("Mauvais paramètres de construction de la gaufre");
        }
    }

    public Board(int line, int column, String waffle) throws Exception {
        if (line > 1 && column > 1 && waffle.length() == line*column) {
            this.line = line;
            this.column = column;
            this.waffle = new Cell[line][column];

            initializeWaffle(waffle);
        } else {
            throw new Exception("Mauvais paramètres de construction de la gaufre");
        }
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

    private void initializeWaffle() {
        for (int i = 0; i < this.line; i++) {
            for (int j = 0; j < this.column; j++) {
                Cell c = new Cell(Cell.FULLCELL);
                this.waffle[i][j] = c;
            }
        }
        waffle[0][0] = new Cell(Cell.POISONEDCELL);
    }

    private void initializeWaffle(String waffle) {
        for (int i = 0; i < this.line; i++) {
            for (int j = 0; j < this.column; j++) {
                Cell c = null;
                // Converti le charactère à la position i en int
                int intAtI = waffle.charAt(i*column+j) - '0';
                switch (intAtI) {
                    case Cell.EMPTYCELL:
                        c = new Cell(Cell.EMPTYCELL);
                        break;
                    case Cell.FULLCELL:
                        c = new Cell(Cell.FULLCELL);
                        break;
                    case Cell.POISONEDCELL:
                        c = new Cell(Cell.POISONEDCELL);
                        break;
                }
                this.waffle[i][j] = c;
            }
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
