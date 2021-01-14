package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class WestPane extends JPanel {

    public WestPane(ActionListener a1, ActionListener a2) {
        init(a1, a2);
    }

    private void init(ActionListener a1, ActionListener a2) {
        setLayout(new GridLayout(2, 1, 10, 10));
        add(FileSelector("Szpitale", a1));
        add(FileSelector("Pacjenci", a2));
    }

    private JButton FileSelector(String in, ActionListener act) {
        JButton file = new JButton(in);
        file.setPreferredSize(new Dimension(100, 50));
        file.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        file.addActionListener(act);
        return file;
    }

}
