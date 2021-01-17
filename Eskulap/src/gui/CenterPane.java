package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;

public class CenterPane extends JPanel {
    
    private final Board board;
    private final Graph graph;
    private final JTextArea console;
    
    public CenterPane(Board b) {
        board = b;
        graph = new Graph(this);
        console = makeConsole();
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout());
        add(graph, BorderLayout.CENTER);
        add(makeScrollPane(), BorderLayout.SOUTH);
    }

    private JScrollPane makeScrollPane() {
        JScrollPane sp = new JScrollPane(console);
        sp.setPreferredSize(new Dimension(100, 100));
        sp.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        sp.setEnabled(true);
        sp.setAutoscrolls(true);
        return sp;
    }

    private JTextArea makeConsole() {
        JTextArea con = new JTextArea("Tutaj będzie tekst.\n");
        con.setPreferredSize(new Dimension(100, 400));
        con.setForeground(Color.WHITE);
        con.setBackground(Color.BLACK);
        con.setLineWrap(true);
        con.setEditable(false);
        return con;
    }
    
    public void print(String s) {
        console.setText(console.getText() + s);
    }
    
    public Board getBoard() {
        return board;
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    public void loadMap() {
        graph.loadMap();
    }
    
    public boolean mapLoaded() {
        return board.mapLoaded();
    }
    
}
