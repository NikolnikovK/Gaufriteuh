package src.sample.Model;


public class Cell {
    public final static int EMPTYCELL = 0;
    public final static int FULLCELL = 1;
    public final static int POISONEDCELL = 2;

    public int type;

    public Cell(int type) {
        this.type = type;
    }

    public boolean isPlayable() {
        if (type == EMPTYCELL) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isPoisoned() {
        if (type == POISONEDCELL) {
            return true;
        } else {
            return false;
        }
    }
}
