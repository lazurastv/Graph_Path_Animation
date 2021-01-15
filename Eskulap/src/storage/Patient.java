package storage;

import java.awt.Point;

public class Patient {

    private final int id;
    private final Point wsp;

    public Patient(int id, int wspx, int wspy) {
        this.id = id;
        wsp = new Point(wspx, wspy);
    }
    
    public void move(int x, int y) {
        wsp.x = x;
        wsp.y = y;
    }

    @Override
    public String toString() {
        return id + " | " + wsp.x + " | " + wsp.y;
    }
    
    public int getId() {
        return id;
    }
    
    public Point getWsp() {
        return wsp;
    }
}
