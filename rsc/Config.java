package rsc;

import java.util.ListResourceBundle;

public class Config extends ListResourceBundle {
    protected Object[][] getContents() {
        return new Object[][] {
            {"Line", 6},
            {"Column", 4},

            {"P0Type", 0},
            {"P0Name", "Alex"},

            {"P1Type", 0},
            {"P1Name", "Aurel"},
        };
    }
}