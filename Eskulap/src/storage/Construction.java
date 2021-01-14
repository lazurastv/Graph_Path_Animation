package storage;

import java.awt.Point;

public class Construction {

    private final int id;
    private final String name;
    private final Point wsp;

    public Construction(int id, String name, int wspx, int wspy) {
        this.id = id;
        this.name = name;
        wsp = new Point(wspx, wspy);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + wsp.x + " | " + wsp.y;
    }
    
    public int getId() {
        return id;
    }
    
    public Point getWsp() {
        return wsp;
    }
}
