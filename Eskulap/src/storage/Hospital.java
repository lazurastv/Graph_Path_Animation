package storage;

import java.awt.Point;

public class Hospital {
    private final int id;
    private final String name;
    private final Point wsp;
    private final int bedNumber;
    private final int freeBedCount;

    public Hospital(int id, String name, int wspx, int wspy, int bedNumber, int freeBedCount) {
        this.id = id;
        this.name = name;
        wsp = new Point(wspx, wspy);
        this.bedNumber = bedNumber;
        this.freeBedCount = freeBedCount;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + wsp.x + " | " + wsp.y + " | " + bedNumber + " | " + freeBedCount;
    }
    
    public int getId() {
        return id;
    }
    
    public Point getWsp() {
        return wsp;
    }
    
    public int getBedNumber() {
        return bedNumber;
    }
}
