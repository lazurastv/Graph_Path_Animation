package eskulap;

import storage.Hospital;
import storage.Road;
import storage.Line;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Crosser {

    private final ArrayList<Hospital> hospitals;
    private final ArrayList<Road> roads;

    public Crosser(Hospital[] hos, Road[] ros) {
        hospitals = new ArrayList<>(Arrays.asList(hos));
        roads = new ArrayList<>();
        ArrayList<Line> lines = Line.makeLines(hos, ros);
        sort(lines);
        for (int i = 0; i < lines.size(); i++) {
            Line first = lines.get(i);
            for (int j = i + 1; j < lines.size(); j++) {
                Line second = lines.get(j);
                if (first.getRightPoint().x > second.getLeftPoint().x) {
                    Point cross = first.crossLines(second);
                    if (cross != null && first.contains(cross) && second.contains(cross)) {
                        int id = hospitals.size() + 1;
                        hospitals.add(new Hospital(id, " Skrzyżowanie ", cross.x, cross.y, 0, 0));
                        lines.remove(j);
                        lines.remove(i);
                        lines.add(new Line(first.getLeftPoint(), cross, first));
                        lines.add(new Line(cross, first.getRightPoint(), first));
                        lines.add(new Line(second.getLeftPoint(), cross, second));
                        lines.add(new Line(cross, second.getRightPoint(), second));
                        sort(lines);
                        i--;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        lines.forEach((l) -> {
            roads.add(l.toRoad(getHospitals()));
        });
        enumerate();
    }

    public Hospital[] getHospitals() {
        return hospitals.toArray(new Hospital[0]);
    }

    public Road[] getRoads() {
        return roads.toArray(new Road[0]);
    }

    private void sort(ArrayList<Line> l) {
        l.sort(Line::compareTo);
    }

    private void enumerate() {
        roads.sort(Road::compareTo);
        for (int i = 0; i < roads.size(); i++) {
            Road r = roads.get(i);
            roads.set(i, new Road(i + 1, r.getIdFirst(), r.getIdSecond(), r.getDistance()));
        }
    }

}
