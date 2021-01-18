package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
import static javax.swing.SwingConstants.CENTER;
import storage.Map;
import storage.Patient;

public class CenterPane extends JPanel {
    
    private Graph graph;
    private final JTextArea console;
    
    public CenterPane() {
        console = makeConsole();
        init();
    }
    
    private void init() {
        setLayout(new BorderLayout());
        add(makeScrollPane(), BorderLayout.SOUTH);
        add(makeStartLabel(), BorderLayout.CENTER);
    }
    
    private JLabel makeStartLabel() {
        JLabel jl = new JLabel("Wczytaj państwo.");
        jl.setFont(new Font("Serif", Font.PLAIN, 20));
        jl.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        jl.setPreferredSize(new Dimension(1000, 500));
        jl.setHorizontalAlignment(CENTER);
        jl.setBackground(Color.WHITE);
        jl.setOpaque(true);
        return jl;
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
        con.setPreferredSize(new Dimension(100, 4000));
        con.setForeground(Color.WHITE);
        con.setBackground(Color.BLACK);
        con.setLineWrap(true);
        con.setEditable(false);
        return con;
    }
    
    public void print(String s) {
        console.setText(console.getText() + s);
    }
    
    public void loadMap(Map m) {
        remove(1);
        if (graph != null) {
            graph.stopAnimation();
        }
        graph = new Graph(this, m);
        add(graph, BorderLayout.CENTER);
        setVisible(false);
        repaint();
        setVisible(true);
    }
    
    public void setSpeed(int s) {
        graph.setSpeed(s);
    }
    
    public void addPatient(Patient p) {
        graph.addPatient(p);
    }
    
    public void toggle(int id) {
        graph.toggle(id);
    }
    
    public boolean mapLoaded() {
        return graph != null;
    }
    
}
