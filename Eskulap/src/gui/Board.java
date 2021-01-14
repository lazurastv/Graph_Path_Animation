package gui;

import eskulap.FileManager;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import storage.Hospital;
import storage.Road;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import storage.Map;

public class Board extends JFrame {

    private Hospital[] hospitals;
    private Road[] roads;
    private final Graph graph;
    private final JTextArea console;
    private final JPanel box;
    private final WestPane west_pane;
    private static final int width = 1200;
    private static final int height = 600;

    public Board() {
        super("Eskulap");
        graph = new Graph();
        console = makeConsole();
        box = new JPanel();
        west_pane = new WestPane(new HospitalsChange(), new PatientsChange());
        initComponents();
    }

    private void initComponents() {
        setSize(width, height);
        setContentPane(box);
        box.setLayout(new BorderLayout());
        box.add(west_pane, BorderLayout.LINE_START);
        box.add(graph, BorderLayout.CENTER);
        box.add(console, BorderLayout.PAGE_END);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private JTextArea makeConsole() {
        JTextArea con = new JTextArea("Tutaj będzie tekst.\n");
        con.setPreferredSize(new Dimension(100, 100));
        con.setForeground(Color.WHITE);
        con.setBackground(Color.BLACK);
        con.setEditable(false);
        return con;
    }
    
    public void print(String s) {
        console.setText(console.getText() + s);
    }

    private class SliderChange implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            int speed = 0;//getValue();
        }

    }

    private class HospitalsChange implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(new JPanel());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                try {
                    Map map = new FileManager().readHospitals(path);
                    graph.loadMap(map);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Nie udało się przeczytać pliku!");
                }
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
