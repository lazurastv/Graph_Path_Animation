package gui;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpeedSlider extends JSlider implements ChangeListener {

    private final WestPane west_pane;

    public SpeedSlider(WestPane pane) {
        west_pane = pane;
        init();
    }

    private void init() {
        setToolTipText("Zmień prędkość animacji.");
        setMinimum(0);
        setMaximum(100);
        setValue(50);
        setMajorTickSpacing(25);
        setMinorTickSpacing(5);
        setPaintTicks(true);
        setSnapToTicks(true);
        setPaintLabels(true);
        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        west_pane.setSpeed(getValue());
    }

}
