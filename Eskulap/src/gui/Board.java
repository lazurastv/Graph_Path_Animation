package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import storage.Hospital;
import storage.Road;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Board extends JFrame {

    private Hospital[] hospitals;
    private Road[] roads;
    private Graph graph;
    private final JPanel box;
    private final WestPane west_pane;
    private final SouthPane south_pane;
    private static final int width = 1200;
    private static final int height = 600;

    public Board(Hospital[] hospitals, Road[] roads) {
        super("Eskulap");
        box = new JPanel();
        graph = new Graph(hospitals, roads);
        west_pane = new WestPane(new HospitalsChange(), new PatientsChange());
        south_pane = new SouthPane(new SliderChange());
        this.hospitals = hospitals;
        this.roads = roads;
        initComponents();
    }

    private void initComponents() {
        setSize(width, height);
        setContentPane(box);
        box.setLayout(new BorderLayout());
        box.add(west_pane, BorderLayout.LINE_START);
        box.add(graph, BorderLayout.CENTER);
        box.add(south_pane, BorderLayout.PAGE_END);
        //box.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class SliderChange implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            int speed = south_pane.getValue();
        }

    }

    private class HospitalsChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(new JPanel());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
            }
        }

    }
    
    private class PatientsChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(new JPanel());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
            }
        }
        
    }

}
