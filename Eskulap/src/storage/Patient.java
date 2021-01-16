package storage;

import java.awt.Point;
import java.util.Objects;

public class Patient {

    private final int id;
    private final Point wsp;

    public Patient(int id, int wspx, int wspy) {
        this.id = id;
        wsp = new Point(wspx, wspy);
    }

    public Patient(int id, Point p) {
        this.id = id;
        wsp = p;
    }

    public void move(int x, int y) {
        wsp.x = x;
        wsp.y = y;
    }

    @Override
    public String toString() {
        return id + " | " + wsp.x + " | " + wsp.y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Patient) {
            Patient p = (Patient) o;
            return this.id == p.id && this.wsp.equals(p.wsp);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.wsp);
        return hash;
    }

    public int getId() {
        return id;
    }

    public Point getWsp() {
        return wsp;
    }

    public int findNearestHospital(Hospital[] hospitals) {

        int nearest_index = -1;
        double nearest_dist = -1;

        for (int i = 0; i < hospitals.length; i++) {
            double dist = hospitals[i].getWsp().distance(wsp);
            if (hospitals[i].getBedNumber() != 0 && (dist < nearest_dist || nearest_dist == -1)) {
                nearest_dist = dist;
                nearest_index = i;
            }
        }

        return nearest_index;
    }
}
