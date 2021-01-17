package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import storage.Map;

public class Board extends JFrame {

    private final JPanel box;
    private final WestPane west_pane;
    private final CenterPane center_pane;
    private static final int width = 1200;
    private static final int height = 600;

    public Board() {
        super("Eskulap");
        box = new JPanel();
        west_pane = new WestPane(this);
        center_pane = new CenterPane(this);
        initComponents();
    }
    
    private void initComponents() {
        setSize(width, height);
        setContentPane(box);
        box.setLayout(new BorderLayout());
        box.add(west_pane, BorderLayout.LINE_START);
        box.add(center_pane, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
    }
    
    public CenterPane getCenter() {
        return center_pane;
    }
    
    public WestPane getWest() {
        return west_pane;
    }
    
    public boolean mapLoaded() {
        return west_pane.mapLoaded();
    }
    
    public void loadMap(Map m) {
        center_pane.loadMap(m);
    }

}
