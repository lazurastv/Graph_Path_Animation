package gui;

import file_managment.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;
import storage.Map;
import storage.Patient;

public class WestPane extends JPanel {

    private Map map;
    private final Board board;
    private final SpeedSlider slider;
    private final JTextField[] coords;

    public WestPane(Board b) {
        board = b;
        slider = new SpeedSlider(this);
        coords = makeFields();
        init();
    }

    public Board getBoard() {
        return board;
    }

    private void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        setLayout(new GridBagLayout());
        for (JButton j : makeSelectors()) {
            add(j, gbc);
            gbc.gridy++;
        }
        add(makeLabel("Wpisz współrzędne pacjenta."), gbc);
        gbc.gridy++;
        gbc.gridwidth = 1;
        String[] labels = {"X: ", "Y: "};
        for (int i = 0; i < labels.length; i++) {
            add(makeLabel(labels[i]), gbc);
            gbc.gridx = 1;
            add(coords[i], gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(makeOkButton(), gbc);
        gbc.gridy++;
        add(makeLabel("Prędkość programu:"), gbc);
        gbc.gridy++;
        add(slider, gbc);
        gbc.gridy++;
        for (JCheckBox j : makeCheckBoxes()) {
            add(j, gbc);
            gbc.gridy++;
        }
    }

    public boolean mapLoaded() {
        return map != null;
    }

    public Map getMap() {
        return map;
    }

    private class HospitalAction extends AbstractAction {

       @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fc = new JFileChooser();
            int returnVal = fc.showOpenDialog(new JPanel());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String path = fc.getSelectedFile().getAbsolutePath();
                try {
                    map = new FileManager().readHospitals(path);
                    map.addCrossings();
                    board.loadMap(map);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Nie udało się przeczytać pliku!");
                } catch(FileReadingException ex){
                    JOptionPane.showMessageDialog(new JFrame(), ex.getMessage());
                }
            }
        }

    }

    private class PatientAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (mapLoaded()) {
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(new JPanel());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String path = fc.getSelectedFile().getAbsolutePath();
                    Patient[] patients;
                    try {
                        patients = new FileManager().readPatients(path);
                        for (Patient p : patients) {
                            board.getCenter().getGraph().addPatient(p);
                        }
                    } catch (IOException | FileReadingException ex) {
                    }
                }
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "Najpierw wczytaj mapę!");
            }
        }

    }

    
    private JButton[] makeSelectors() {
        JButton[] file = new JButton[2];
        String[] names = {"Wczytaj szpitale", "Wczytaj pacjentów"};
        for (int i = 0; i < file.length; i++) {
            file[i] = new JButton(names[i]);
            file[i].setPreferredSize(new Dimension(100, 50));
            file[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }
        file[0].addActionListener(new HospitalAction());
        file[1].addActionListener(new PatientAction());
        return file;
    }

    private JLabel makeLabel(String s) {
        JLabel axis_names = new JLabel(s);
        axis_names.setHorizontalAlignment(CENTER);
        return axis_names;
    }

    private JTextField[] makeFields() {
        JTextField[] jtf = new JTextField[2];
        for (int i = 0; i < jtf.length; i++) {
            jtf[i] = new JTextField();
            jtf[i].setHorizontalAlignment(CENTER);
        }
        return jtf;
    }

    private JButton makeOkButton() {
        JButton button = new JButton("OK");
        button.setHorizontalAlignment(CENTER);
        button.addActionListener(new AbstractAction() {
            int id = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (coords[0].getText().isBlank() || coords[1].getText().isBlank()) {
                    JOptionPane.showMessageDialog(new JFrame(), "Najpierw wprowadź współrzędne pacjenta!");
                } else try {
                    int x = Integer.parseInt(coords[0].getText());
                    int y = Integer.parseInt(coords[1].getText());
                    board.getCenter().getGraph().addPatient(new Patient(id++, x, y));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(new JFrame(), "Współrzędne muszą być liczbami całkowitymi!");
                }
            }

        });
        return button;
    }

    private JCheckBox[] makeCheckBoxes() {
        JCheckBox[] boxes = new JCheckBox[3];
        String[] list = {"Szpitale", "Obiekty", "Drogi"};
        for (int i = 0; i < boxes.length; i++) {
            boxes[i] = new JCheckBox(list[i]);
            boxes[i].setSelected(true);
        }
        boxes[0].addActionListener((ActionEvent e) -> {
            board.getCenter().getGraph().toggleHospitals();
        });
        boxes[1].addActionListener((ActionEvent e) -> {
            board.getCenter().getGraph().toggleObjects();
        });
        boxes[2].addActionListener((ActionEvent e) -> {
            board.getCenter().getGraph().toggleRoads();
        });
        return boxes;
    }

}
