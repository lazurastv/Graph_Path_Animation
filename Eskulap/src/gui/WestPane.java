package gui;

import java.awt.GridLayout;
import javax.swing.JPanel;

public class WestPane extends JPanel {
    
    public WestPane() {
        init();
    }
    
    private void init() {
        setLayout(new GridLayout(2, 1, 10, 10));
        add(new FileSelector("Szpitale"));
        add(new FileSelector("Pacjenci"));
    }
    
}
