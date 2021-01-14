package storage;

import java.awt.Point;

public class Patient {

    private final int id;
    private final Point wsp;

    public Patient(int id, int wspx, int wspy) {
        this.id = id;
        this.wsp = new Point(wspx, wspy);
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
