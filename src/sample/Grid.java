package src.sample;

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
        System.out.println("--- Affichage de la grille ---");
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(grid[i][j].getType() + " ");
            }
            System.out.println();
        }
        System.out.println("--- Fin de l'affichage de la grille ---");
    }
}
