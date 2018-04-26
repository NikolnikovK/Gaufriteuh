package src.sample.Model;


public class Cell {
    final static int EMPTYCELL = 0;
    final static int FULLCELL = 1;
    final static int POISONEDCELL = 2;

    private int type;

    public Cell(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
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
