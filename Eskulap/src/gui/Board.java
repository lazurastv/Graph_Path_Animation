package gui;

import java.awt.BorderLayout;
import storage.Hospital;
import storage.Road;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JFrame {

    private Hospital[] hospitals;
    private Road[] roads;
    private final JPanel panel;
    private final JPanel box;
    private static final int width = 1200;
    private static final int height = 600;

    public Board(Hospital[] hospitals, Road[] roads) {
        super("Eskulap");
        panel = new Graph(hospitals, roads);
        box = new JPanel();
        this.hospitals = hospitals;
        this.roads = roads;
        initComponents();
    }

    private void initComponents() {
        setSize(width, height);
        getContentPane().add(box);
        box.setLayout(new BorderLayout());
        box.add(new WestPane(), BorderLayout.LINE_START);
        box.add(panel, BorderLayout.CENTER);
        box.add(new SouthPane(), BorderLayout.PAGE_END);
        //box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

}
