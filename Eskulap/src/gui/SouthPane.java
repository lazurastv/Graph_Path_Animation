package gui;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class SouthPane extends JPanel {
    
    private final Console console;
    
    public SouthPane() {
        console = new Console();
        init();
    }
    
    private void init() {
        setLayout(new GridLayout(1, 2, 0, 10));
        add(new SpeedSlider());
        add(console);
        console.addText("Text");
        console.addText("\nAnother Line");
    }
    
}
