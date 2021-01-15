package gui;

import eskulap.FileManager;
import floyd_warshall.Edge;
import floyd_warshall.FloydWarshallAlgorithm;
import floyd_warshall.Vertex;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import storage.Map;
import storage.Patient;

public class Board extends JFrame {

    private Map map;
    private FloydWarshallAlgorithm fwa;
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
        west_pane = new WestPane(new HospitalsFile(), new PatientsFile());
        initComponents();
    }

    private void initComponents() {
        setSize(width, height);
        setContentPane(box);
        box.setLayout(new BorderLayout());
        box.add(west_pane, BorderLayout.LINE_START);
        box.add(graph, BorderLayout.CENTER);
        box.add(makeScrollPane(), BorderLayout.PAGE_END);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        pack();
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
	
    private void routePatient(Patient p) {
        int closest = p.findNearestHospital(map.getHospitals());
        closest = fwa.findVertexId(closest);
        int[] path = fwa.getPath(closest, fwa.getClosestVertex(closest));
        graph.loadPatient(p, path);
        for (int i = 0; i < path.length; i++) {
            print("" + path[i]);
            if (i < path.length - 1) {
                print(" -> ");
            }
        }
        print("\n");
        fwa.reset();
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

    private class HospitalsFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(new JPanel());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                try {
                    map = new FileManager().readHospitals(path);
                    map.addCrossings();
                    graph.loadMap(map);
                    System.out.print(map);
                    Vertex[] ver = Vertex.vertexArray(map.getHospitals());
                    fwa = new FloydWarshallAlgorithm(ver, Edge.edgeArray(ver, map.getRoads()));
                    fwa.applyAlgorithm();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Nie udało się przeczytać pliku!");
                }
            }
        }

    }

    private class PatientsFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (map != null) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(new JPanel());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String path = fc.getSelectedFile().getAbsolutePath();
                    try {
                        Patient[] patients = new FileManager().readPatients(path);
                        routePatients(patients);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(new JFrame(), "Nie udało się przeczytać pliku!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Najpierw wczytaj państwo!");
            }
        }

    }

}
