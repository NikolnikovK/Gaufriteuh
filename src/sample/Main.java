package src.sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.sample.Model.Grid;

public class Main extends Application {
    Grid gr;
    Canvas c;

    void draw() {
        double width = c.getWidth();
        double height = c.getHeight();
        int line = gr.line;
        int column = gr.column;
        GraphicsContext g = c.getGraphicsContext2D();

        g.clearRect(0, 0, width, height);
        if (!gr.inGame) {
            g.clearRect(0, 0, width, height);
            g.strokeText("Fin", width/2, height/2);
        } else {
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < column; j++) {
                    switch (gr.grid[i][j].type) {
                        case 0:
                            g.setFill(Color.WHITE);
                            break;
                        case 1:
                            g.setFill(Color.ORANGE);
                            break;
                        case 2:
                            g.setFill(Color.GREEN);
                            break;
                    }
                    g.fillRect(j*width/column, i*height/line, width/column,height/line);
                }
            }
            for (int i = 0; i < gr.line+1; i++) {
                g.strokeLine(0, i*height/line, width, i*height/line);
                g.strokeLine(i*width/column, 0, i*width/column, height);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("../../sample.fxml"));
        //primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();

        gr = new Grid(6,4);
        /*
        joueurs = new Joueur[2];
        joueurCourant = 0;
        echeance = System.nanoTime();

        Iterator<String> it = getParameters().getRaw().iterator();
        for (int i=0; i<joueurs.length; i++) {
            String nature;
            if (it.hasNext())
                nature = it.next();
            else
                nature = "IA";
            switch (nature) {
                case "humain":
                    joueurs[i] = new JoueurHumain(i, plateau);
                    break;
                case "IA":
                    joueurs[i] = new JoueurIA(i, plateau);
                    break;
                default:
                    throw new InvalidParameterException(nature);
            }
        }
        */

        primaryStage.setTitle("Gaufriteuh empoisonnée");
        c = new Canvas(400, 600);

        // Composant de regroupement qui occupe toute la place disponible
        // Le noeud donné en paramètre est placé au centre du BorderPane
        BorderPane b = new BorderPane(c);
        Scene s;
        s = new Scene(b);
        primaryStage.setScene(s);
        primaryStage.show();
        draw();

        c.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (gr.inGame) {
                    int i = (int) (e.getY()*gr.line/c.getHeight());
                    int j = (int) (e.getX()*gr.column/c.getWidth());
                    //int attente = traiteAction(i, j);
                    //fixeEcheance(attente);
                    gr.play(i,j);
                    draw();
                }
            }
        });
        /*
        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long temps) {
                if ((echeance > 0) && (temps > echeance)) {
                    int attente = traiteTemporisation();
                    fixeEcheance(attente);
                    dessin();
                }
            }
        };
        anim.start();
        */

    }


    public static void main(String[] args) {
        launch(args);
    }
}
