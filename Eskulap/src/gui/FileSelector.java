package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileSelector extends JButton implements ActionListener {

    private String path;

    public FileSelector(String in) {
        init(in);
    }

    private void init(String in) {
        setText(in);
        setPreferredSize(new Dimension(100, 50));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(new JPanel());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            path = fc.getSelectedFile().getAbsolutePath();
        }
    }

    public String getPath() {
        return path;
    }

}
