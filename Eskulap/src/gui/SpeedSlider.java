package gui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpeedSlider extends JSlider implements ChangeListener {
    
    private int speed;
    
    public SpeedSlider() {
        speed = 5;
        init();
    }
    
    private void init() {
        setToolTipText("Zmień prędkość animacji.");
        setMinimum(0);
        setMaximum(10);
        setValue(5);
        setMajorTickSpacing(1);
        setPaintTicks(true);
        setSnapToTicks(true);
        setPaintLabels(true);
        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        speed = getValue();
    }
    
    public int getSpeed() {
        return speed;
    }
    
}
