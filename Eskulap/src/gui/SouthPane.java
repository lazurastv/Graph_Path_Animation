package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.event.ChangeListener;

public class SouthPane extends JPanel {

    private final JTextArea console;
    private final SpeedSlider slider;

    public SouthPane(ChangeListener ch) {
        console = console();
        slider = new SpeedSlider();
        slider.addChangeListener(ch);
        init();
    }

    private void init() {
        setLayout(new GridLayout(1, 2, 0, 10));
        add(leftPanel());
        add(console);
    }

    private JPanel leftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(animationLabel(), BorderLayout.NORTH);
        leftPanel.add(slider, BorderLayout.CENTER);
        return leftPanel;
    }

    private JLabel animationLabel() {
        JLabel anim = new JLabel("Prędkość animacji");
        anim.setHorizontalAlignment(CENTER);
        return anim;
    }

    private JTextArea console() {
        JTextArea con = new JTextArea("Tekst");
        con.setPreferredSize(new Dimension(100, 100));
        con.setEditable(false);
        con.setBackground(Color.BLACK);
        con.setForeground(Color.WHITE);
        return con;
    }
    
    public void addText(String text) {
        console.setText(console.getText() + text);
    }

    public int getValue() {
        return slider.getValue();
    }

}
