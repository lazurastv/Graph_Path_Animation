package gui;

import java.awt.Dimension;
import javax.swing.JTextArea;

public class Console extends JTextArea {
    
    private String text;
    
    public Console() {
        text = "";
        init();
    }
    
    private void init() {
        setPreferredSize(new Dimension(100, 100));
        setEditable(false);
    }
    
    public void addText(String in) {
        text += in;
        setText(text);
    }
    
}
