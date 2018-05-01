package src.sample;

import javafx.application.Application;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import src.sample.Model.Player.IAPlayer;
import src.sample.Model.Player.Player;
import src.sample.Model.Player.RealPlayer;
import src.sample.Model.Board;

import java.util.ResourceBundle;

public class Main extends Application {
    Board board;
    Canvas c;
    Player [] players;
    int currentPlayer;

    void draw() {
        double width = c.getWidth();
        double height = c.getHeight();
        double boardHeight = height-100;
        int line = board.line;
        int column = board.column;
        GraphicsContext g = c.getGraphicsContext2D();

        g.clearRect(0, 0, width, height);
        if (!board.inGame) {
            g.strokeText(players[currentPlayer].name + " a perdu !", width/3, height/2);
        } else {
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < column; j++) {
                    switch (board.waffle[i][j].type) {
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
                    g.fillRect(j*width/column, i*boardHeight/line, width/column,boardHeight/line);
                }
            }
            for (int i = 0; i < board.line+1; i++) {
                g.strokeLine(0, i*boardHeight/line, width, i*boardHeight/line);
                g.strokeLine(i*width/column, 0, i*width/column, boardHeight);
            }
            /*g.setFill(Color.BLACK);
            g.strokeText(players[0].name,width/4, height-50);
            g.strokeText(players[1].name,width/4*3, height-50);
            if (currentPlayer == 0) {
                g.fillOval(width/4-12, height-60, 10,10);
            } else {
                g.fillOval(width/4*3-12, height-60, 10,10);
            }*/
        }


    }

    @Override
    public void start(Stage stage) throws Exception{
        players = new Player[2];
        currentPlayer = 0;

        // Récupuère les config
        ResourceBundle config = ResourceBundle.getBundle("rsc.Config");
        int line = (int) config.getObject("Line");
        int column = (int) config.getObject("Column");

        // Crée le plateau de jeu
        board = new Board(line,column);

        // Pour chaque joueur Crée un joueur IA ou réel
        for (int i = 0; i < 2; i++) {
            String name = config.getString("P"+i+"Name");
            if ((int) config.getObject("P"+i+"Type") == 0) {
                players[i] = new RealPlayer(name, board);
            } else {
                players[i] = new IAPlayer(name, board);
            }
        }


        stage.setTitle("Gaufriteuh empoisonnée");

        // Crée le canvas
        c = new Canvas();
        // Met des listener permettant de redimensionner en fonction de la fenêtre
        c.widthProperty().addListener(evt -> draw());
        c.heightProperty().addListener(evt -> draw());
        // Ajoute des ptions pour la performance
        c.setCache(true);
        c.setCacheHint(CacheHint.SPEED);

        // Crée le panel contenant le canvas
        Pane pane = new Pane(c);
        // Fait en sorte que la taille du canvas soit la même que celle du panel
        c.widthProperty().bind(pane.widthProperty());
        c.heightProperty().bind(pane.heightProperty());

        // Crée le gridPane qui contiendra l'interface
        GridPane mainGrid = new GridPane();

        // Ajoute des contraintes sur la taille des lignes
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(90);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        mainGrid.getRowConstraints().addAll(row1,row2);

        // Ajoute des contraintes sur la taille des colonnes
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        mainGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

        // Crée les textes avec le nom des joueurs
        Text p0 = new Text(players[0].name);
        p0.setStroke(Color.BLACK);
        Text p1 = new Text(players[0].name);
        p1.setStroke(Color.BLACK);

        // Ajoute le tout au grid
        mainGrid.add(p0,1,1);
        mainGrid.add(p1,3,1);
        mainGrid.add(pane,0,0,4,1);

        // Crée la scène, l'affiche et dessine le canvas
        Scene s = new Scene(mainGrid,100*column, 100+100*line);
        stage.setScene(s);
        stage.show();
        draw();

        // Ajoute un handler sur le canvas pour récupérer la case dans laquelle le joueur clique
        c.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.inGame) {
                    int i = (int) (e.getY()* board.line/(c.getHeight()-100));
                    int j = (int) (e.getX()* board.column/c.getWidth());
                    switch (players[currentPlayer].bite(i,j)) {
                        // Case valide
                        case 0:
                            // Change le joueur courant
                            currentPlayer = 1 - currentPlayer;
                            break;
                        // Case invalide
                        case 1:
                            break;
                        // Case empoisonnée
                        case 2:
                            // Arrête le jeu
                            board.inGame = false;
                            break;
                    }
                    // Raffraichit l'affichage
                    draw();
                }
            }
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
