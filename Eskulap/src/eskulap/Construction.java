package eskulap;

import java.awt.Point;

public class Construction {

    int id;
    String name;
    Point wsp;

    public Construction(int id, String name, int wspx, int wspy) {
        this.id = id;
        this.name = name;
        wsp = new Point(wspx, wspy);
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + wsp.x + " | " + wsp.y;
    }
}
