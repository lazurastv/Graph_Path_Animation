package storage;

import java.awt.Point;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Construction) {
            Construction c = (Construction) o;
            return this.id == c.id && this.name.equals(c.name) && this.wsp.equals(c.wsp);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.wsp);
        return hash;
    }

    public int getId() {
        return id;
    }

    public Point getWsp() {
        return wsp;
    }
}
