package src.sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import src.sample.Model.Player.IAPlayer;
import src.sample.Model.Player.Player;
import src.sample.Model.Player.RealPlayer;
import src.sample.Model.Board;

import java.awt.*;
import java.io.*;
import java.util.ResourceBundle;

public class Main extends Application {
    Board board;
    Canvas c;
    //GridPane mainGrid;
    Player [] players;
    int currentPlayer;
    int sizeOfUserInfos = 75;


    void drawBoard() {
        double width = c.getWidth();
        double height = c.getHeight();
        double boardHeight = height-sizeOfUserInfos;
        int line = board.line;
        int column = board.column;
        GraphicsContext g = c.getGraphicsContext2D();

        g.clearRect(0, 0, width, height);
        if (!board.inGame) {
            g.strokeText(players[currentPlayer].name+" a perdu !",width/2-50,height/2);
        } else {
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < column; j++) {
                    switch (board.waffle[i][j].type) {
                        case 0:
                            g.setFill(javafx.scene.paint.Color.WHITE);
                            break;
                        case 1:
                            g.setFill(javafx.scene.paint.Color.ORANGE);
                            break;
                        case 2:
                            g.setFill(javafx.scene.paint.Color.GREEN);
                            break;
                    }
                    g.fillRect(j*width/column, i*boardHeight/line, width/column,boardHeight/line);
                }
            }
            for (int i = 0; i < board.line+1; i++) {
                g.strokeLine(0, i*boardHeight/line, width, i*boardHeight/line);
            }
            for (int i = 0; i < board.column+1; i++) {
                g.strokeLine(i*width/column, 0, i*width/column, boardHeight);
            }
            g.setFill(Color.BLACK);
            g.strokeText(players[0].name,width/4, boardHeight+30);
            g.strokeText(players[1].name,width/4*3, boardHeight+30);
            if (currentPlayer == 0) {
                g.fillOval(width/4-12, boardHeight+20, 10,10);
            } else {
                g.fillOval(width/4*3-12, boardHeight+20, 10,10);
            }
        }
    }


    void drawMenu() {
        double width = c.getWidth();
        double height = c.getHeight();
        double boardHeight = height-sizeOfUserInfos;
        int line = board.line;
        int column = board.column;
        GraphicsContext g = c.getGraphicsContext2D();

        g.clearRect(0, 0, width, height);
        if (!board.inGame) {
            g.strokeText(players[currentPlayer].name+" a perdu !",width/2-50,height/2);
        } else {
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < column; j++) {
                    switch (board.waffle[i][j].type) {
                        case 0:
                            g.setFill(javafx.scene.paint.Color.WHITE);
                            break;
                        case 1:
                            g.setFill(javafx.scene.paint.Color.ORANGE);
                            break;
                        case 2:
                            g.setFill(javafx.scene.paint.Color.GREEN);
                            break;
                    }
                    g.fillRect(j*width/column, i*boardHeight/line, width/column,boardHeight/line);
                }
            }
            for (int i = 0; i < board.line+1; i++) {
                g.strokeLine(0, i*boardHeight/line, width, i*boardHeight/line);
            }
            for (int i = 0; i < board.column+1; i++) {
                g.strokeLine(i*width/column, 0, i*width/column, boardHeight);
            }
            g.setFill(Color.BLACK);
            g.strokeText(players[0].name,width/4, boardHeight+30);
            g.strokeText(players[1].name,width/4*3, boardHeight+30);
            if (currentPlayer == 0) {
                g.fillOval(width/4-12, boardHeight+20, 10,10);
            } else {
                g.fillOval(width/4*3-12, boardHeight+20, 10,10);
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception{
        players = new Player[2];

        initializeGame();
        //reloadGame();

        BorderPane root = new BorderPane();

        // Crée le canvas
        c = new Canvas(50*board.column, 50*board.line);
        // Met des listener permettant de redimensionner en fonction de la fenêtre
        c.widthProperty().addListener(evt -> drawBoard());
        c.heightProperty().addListener(evt -> drawBoard());
        // Ajoute des ptions pour la performance
        c.setCache(true);
        c.setCacheHint(CacheHint.SPEED);

        // Crée le panel contenant le canvas
        Pane pane = new Pane();
        pane.getChildren().add(c);
        // Fait en sorte que la taille du canvas soit la même que celle du panel
        c.widthProperty().bind(pane.widthProperty());
        c.heightProperty().bind(pane.heightProperty());

        root.setCenter(pane);

        HBox paneBottom = new HBox();
        paneBottom.setPrefSize(c.getWidth(), 25);
        //paneBottom.setPadding(new Insets(20));
        paneBottom.setAlignment(Pos.TOP_CENTER);
        // Bouton reset
        Button reset = new Button("Reset");
        reset.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                initializeGame();
                drawBoard();
            }
        });
        paneBottom.getChildren().add(reset);
        // Bouton save
        Button save = new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                saveGame();
            }
        });
        paneBottom.getChildren().add(save);
        // Bouton reload
        Button reload = new Button("Reload last save");
        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                reloadGame();
                drawBoard();
            }
        });
        paneBottom.getChildren().add(reload);
        // Ajoute HBox au panneau principale
        root.setBottom(paneBottom);


        /*
        // Crée le gridPane qui contiendra l'interface
        mainGrid = new GridPane();

        // Ajoute des contraintes sur la taille des lignes
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(90);
        row1.setMinHeight(50*board.line);
        row1.setPrefHeight(50*board.line);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(10);
        row2.setMinHeight(50);
        row2.setPrefHeight(50);
        row2.setValignment(VPos.TOP);
        mainGrid.getRowConstraints().addAll(row1,row2);*/

        // Ajoute des contraintes sur la taille des colonnes
        /*ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(25);
        col1.setHalignment(HPos.CENTER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(25);
        col2.setHalignment(HPos.CENTER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPercentWidth(25);
        col3.setHalignment(HPos.CENTER);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setPercentWidth(25);
        col4.setHalignment(HPos.CENTER);
        mainGrid.getColumnConstraints().addAll(col1, col2, col3, col4);*/

        /*// Crée les textes avec le nom des joueurs
        Text p0 = new Text(players[0].name);
        p0.setStroke(Color.BLACK);
        Text p1 = new Text(players[0].name);
        p1.setStroke(Color.BLACK);*/

        /*// Ajoute le tout au grid
        mainGrid.add(p0,1,1);
        mainGrid.add(p1,3,1);
        mainGrid.add(pane,0,0,4,2);*/

        // Crée la scène, l'affiche et dessine le canvas
        Scene s = new Scene(root,100*board.column, 100*board.line+sizeOfUserInfos);
        stage.setTitle("Gaufriteuh empoisonnée");
        stage.setScene(s);
        stage.show();
        drawBoard();

        // Ajoute un handler sur le canvas pour récupérer la case dans laquelle le joueur clique
        c.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (board.inGame) {
                    int i = (int) (e.getY()* board.line/(c.getHeight()-sizeOfUserInfos));
                    int j = (int) (e.getX()* board.column/c.getWidth());
                    switch (players[currentPlayer].play(i,j)) {
                        // Case valide
                        case 0:
                            // Change le joueur courant
                            currentPlayer = 1 - currentPlayer;
                            // Raffraichit l'affichage
                            drawBoard();
                            break;
                        // Case invalide
                        case 1:
                            break;
                        // Case empoisonnée
                        case 2:
                            // Arrête le jeu
                            board.inGame = false;
                            // Raffraichit l'affichage
                            drawBoard();
                            break;
                    }
                }
            }
        });
    }

    private void saveGame() {
        // Nom du fichier
        String fileName = "rsc/save.txt";

        try {
            FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("waffleLine="+board.line);
            bufferedWriter.newLine();
            bufferedWriter.write("waffleColumn="+board.column);
            bufferedWriter.newLine();
            bufferedWriter.write("waffle=");
            for (int i = 0; i < board.line; i++) {
                for (int j = 0; j < board.column; j++) {
                    bufferedWriter.write(String.valueOf(board.waffle[i][j].type));
                }
            }
            bufferedWriter.newLine();
            // On ajoute les infos des joueurs
            for (int i = 0; i < 2; i++) {
                if (players[i] instanceof RealPlayer) {
                    bufferedWriter.write("P"+i+"Type=0");
                    bufferedWriter.newLine();
                } else {
                    bufferedWriter.write("P"+i+"Type=1");
                    bufferedWriter.newLine();
                }
                bufferedWriter.write("P"+i+"Name="+players[i].name);
                bufferedWriter.newLine();
            }
            // Qui est le joueur courant
            bufferedWriter.write("currentPlayer="+currentPlayer);
            bufferedWriter.newLine();

            // Fermeture du fichier
            bufferedWriter.close();
        } catch(IOException ex) {
            System.out.println("Erreur durant l'écriture du fichier '" + fileName + "'");
        }
    }

    private void reloadGame() {
        String fileName = "rsc/save.txt";
        String line;
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            int waffleLine = -1;
            int waffleColumn = -1;
            String waffle = "";
            int [] pType = {-1, -1};
            String [] pName = {"", ""};

            // Tant qu'il y a quelque chose à lire on le lit
            while((line = bufferedReader.readLine()) != null) {
                String[] splited = line.split("=");
                switch (splited[0]) {
                    case "waffleLine":
                        waffleLine = Integer.parseInt(splited[1]);
                        break;
                    case "waffleColumn":
                        waffleColumn = Integer.parseInt(splited[1]);
                        break;
                    case "waffle":
                        waffle = splited[1];
                        break;
                    case "P0Type":
                        pType[0] = Integer.parseInt(splited[1]);
                        break;
                    case "P0Name":
                        pName[0] = splited[1];
                        break;
                    case "P1Type":
                        pType[1] = Integer.parseInt(splited[1]);
                        break;
                    case "P1Name":
                        pName[1] = splited[1];
                        break;
                    case "currentPlayer":
                        currentPlayer = Integer.parseInt(splited[1]);
                        break;
                }
            }
            board = new Board(waffleLine, waffleColumn, waffle);
            // Pour chaque joueur Crée un joueur IA ou réel
            for (int i = 0; i < 2; i++) {
                if (pType[i] == 0) {
                    players[i] = new RealPlayer(pName[i], board);
                } else {
                    players[i] = new IAPlayer(pName[i], board);
                }
            }

            // Fermeture du fichier
            bufferedReader.close();
        } catch(FileNotFoundException ex) {
            System.out.println("Impossible d'ouvrir le fichier '" + fileName + "'");
        } catch(IOException ex) {
            System.out.println("Erreur lors de la lecture du fichier '" + fileName + "'");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeGame() {
        currentPlayer = 0;
        // Récupuère les config
        ResourceBundle config = ResourceBundle.getBundle("rsc.Config");
        int line = (int) config.getObject("Line");
        int column = (int) config.getObject("Column");

        // Crée le plateau de jeu
        try {
            board = new Board(line,column);
        } catch (Exception e1) {
            e1.printStackTrace();
        }


        // Pour chaque joueur Crée un joueur IA ou réel
        for (int i = 0; i < 2; i++) {
            String name = config.getString("P"+i+"Name");
            if ((int) config.getObject("P"+i+"Type") == 0) {
                players[i] = new RealPlayer(name, board);
            } else {
                players[i] = new IAPlayer(name, board);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
