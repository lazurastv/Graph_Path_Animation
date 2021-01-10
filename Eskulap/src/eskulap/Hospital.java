package eskulap;

import java.awt.Point;

public class Hospital {
    public int id;
    String name;
    Point wsp;
    public int bedNumber;
    public int freeBedCount;

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
}
