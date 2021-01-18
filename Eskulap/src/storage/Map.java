package storage;

import eskulap.Crosser;
import java.util.Arrays;

public class Map {

    private Hospital[] hospitals;
    private Construction[] constructs;
    private Road[] roads;

    public void addCrossings() {
        Crosser c = new Crosser(hospitals, roads);
        hospitals = c.getHospitals();
        roads = c.getRoads();
    }

    public void setHospitals(Hospital[] hos) {
        hospitals = hos;
    }

    public void setConstructs(Construction[] con) {
        constructs = con;
    }

    public void setRoads(Road[] ros) {
        roads = ros;
    }

    public Hospital[] getHospitals() {
        return hospitals;
    }

    public Construction[] getConstructs() {
        return constructs;
    }

    public Road[] getRoads() {
        return roads;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Szpitale:\n");
        for (Hospital h : hospitals) {
            sb.append(h).append('\n');
        }
        sb.append("Obiekty:\n");
        for (Construction c : constructs) {
            sb.append(c).append('\n');
        }
        sb.append("Drogi:\n");
        for (Road r : roads) {
            sb.append(r).append('\n');
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Map) {
            Map m = (Map) o;
            return Arrays.equals(this.constructs, m.constructs) && Arrays.equals(this.hospitals, m.hospitals)
                    && Arrays.equals(this.roads, m.roads);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Arrays.deepHashCode(this.hospitals);
        hash = 59 * hash + Arrays.deepHashCode(this.constructs);
        hash = 59 * hash + Arrays.deepHashCode(this.roads);
        return hash;
    }

}
