package src.sample.Model.Player;

import src.sample.Model.Board;

public abstract class Player {
    public String name;
    Board board;

    public Player(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    public int play(int line, int column) {
        return board.play(line,column);
    }
}
