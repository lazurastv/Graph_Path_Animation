package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JFrame {

    private final JPanel box;
    private final WestPane west_pane;
    private final CenterPane center_pane;
    private static final int X_SIZE = 1200;
    private static final int Y_SIZE = 600;

    public Board() {
        super("Eskulap");
        box = new JPanel();
        center_pane = new CenterPane();
        west_pane = new WestPane(center_pane);
        initComponents();
    }
    
    private void initComponents() {
        setSize(X_SIZE, Y_SIZE);
        setContentPane(box);
        box.setLayout(new BorderLayout());
        box.add(west_pane, BorderLayout.LINE_START);
        box.add(center_pane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }    

}
