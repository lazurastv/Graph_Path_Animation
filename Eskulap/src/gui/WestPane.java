package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;

public class WestPane extends JPanel {
    
    GridBagConstraints gbc;
    SpeedSlider slider;

    public WestPane(ActionListener a1, ActionListener a2) {
        gbc = new GridBagConstraints();
        slider = new SpeedSlider();
        init(a1, a2);
    }

    private void init(ActionListener a1, ActionListener a2) {
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        setLayout(new GridBagLayout());
        add(FileSelector("Szpitale", a1), gbc);
        gbc.gridy = 1;
        add(FileSelector("Pacjenci", a2), gbc);
        gbc.gridy = 2;
        add(makeLabel("Wpisz współrzędne pacjenta."), gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 3;
        add(makeLabel("X: "), gbc);
        gbc.gridx = 1;
        add(makeTextField(), gbc);
        gbc.gridy = 4;
        gbc.gridx = 0;
        add(makeLabel("Y: "), gbc);
        gbc.gridx = 1;
        add(makeTextField(), gbc);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(makeLabel("Prędkość programu:"), gbc);
        gbc.gridy = 6;
        add(slider, gbc);
    }

    private JButton FileSelector(String in, ActionListener act) {
        JButton file = new JButton(in);
        file.setPreferredSize(new Dimension(100, 50));
        file.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        file.addActionListener(act);
        return file;
    }
    
    private JLabel makeLabel(String s) {
        JLabel axis_names = new JLabel(s);
        axis_names.setHorizontalAlignment(CENTER);
        return axis_names;
    }
    
    private JTextField makeTextField() {
        return new JTextField();
    }
    
}
