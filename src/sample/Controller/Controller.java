package src.sample.Controller;

import src.sample.Model.Grid;
import java.util.Scanner;

public class Controller {

    public Controller() {
        Grid g = new Grid(5,5);
        g.printGrid();

        Scanner sc = new Scanner(System.in);
        int line, column;
        System.out.println("Veuillez saisir un numéro de ligne :");
        line = sc.nextInt();
        System.out.println("Veuillez saisir un numéro de colonne :");
        column = sc.nextInt();

        while (g.play(line,column) == 0) {
            g.printGrid();
            System.out.println("Veuillez saisir un numéro de ligne :");
            line = sc.nextInt();
            System.out.println("Veuillez saisir un numéro de colonne :");
            column = sc.nextInt();
        }

    }

}
