package eskulap;

import gui.Board;
import java.awt.EventQueue;

public class Eskulap {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Board board = new Board();
        });
    }
    
}
