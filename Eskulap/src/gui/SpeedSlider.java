package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JSlider;

public class SpeedSlider extends JSlider implements ActionListener {
    
    private int speed;
    
    public SpeedSlider() {
        speed = 5;
        init();
    }
    
    private void init() {
        setMinimum(0);
        setMaximum(10);
        setValue(5);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        speed = getValue();
        System.out.println(speed);
    }
    
}
