package src.sample.Model;

public class Grid {
    private int line;
    private int column;
    private Cell [][] grid;

    public Grid(int line, int column, int linePois, int columnPois) {
        this.line = line;
        this.column = column;
        this.grid = new Cell[line][column];

        for (int i = 0; i < this.line; i++) {
            for (int j = 0; j < this.column; j++) {
                Cell c = new Cell(Cell.FULLCELL);
                grid[i][j] = c;
            }
        }
        grid[linePois][columnPois] = new Cell(Cell.POISONEDCELL);
    }

    public void printGrid() {
        System.out.println();
        System.out.println("--- Affichage de la grille ---");
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(grid[i][j].type + " ");
            }
            System.out.println();
        }
        System.out.println("--- Fin de l'affichage de la grille ---");
        System.out.println();
    }

    public int play(int line, int column) {
        if (grid[line][column].isPlayable()) {
            if (grid[line][column].type == Cell.POISONEDCELL) {
                System.out.println("C'est perdu !");
                return 1;
            } else {
                for (int i = line; i < this.line; i++) {
                    for (int j = column; j < this.column; j++) {
                        if (grid[i][j].type == Cell.FULLCELL) {
                            grid[i][j].type = Cell.EMPTYCELL;
                        }
                    }
                }
            }
        } else {
            System.out.println("Ceci n'est pas une case valide !");
        }
        return 0;
    }
}
