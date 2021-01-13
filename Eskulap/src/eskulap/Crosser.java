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
        roads = new ArrayList<>(Arrays.asList(ros));
        ArrayList<Line> lines = Line.makeLines(hos, ros);
        for (int i = 0; i < lines.size(); i++) {
            Line first = lines.get(i);
            Road one = roads.get(i);
            for (int j = i + 1; j < lines.size(); j++) {
                Line second = lines.get(j);
                Road two = roads.get(j);
                Point cross = first.crossLines(second);
                if (first.contains(cross) && second.contains(cross)) {
                    int id = hospitals.size() + 1;
                    hospitals.add(new Hospital(id, "Skrzyżowanie " + id, cross.x, cross.y, 0, 0));
                    double ratio = one.getDistance() / first.length();
                    double len = ratio * first.distance(true, cross);
                    roads.add(new Road(roads.get(i).getId(), one.getIdFirst(), id, len));
                    lines.add(new Line(first.getStart(), cross));
                    roads.add(new Road(lines.size(), one.getIdSecond(), id, one.getDistance() - len));
                    lines.add(new Line(cross, first.getEnd()));
                    ratio = two.getDistance() / second.length();
                    len = ratio * second.distance(true, cross);
                    roads.add(new Road(roads.get(j).getId(), two.getIdFirst(), id, len));
                    lines.add(new Line(second.getStart(), cross));
                    roads.add(new Road(lines.size(), two.getIdSecond(), id, two.getDistance() - len));
                    lines.add(new Line(cross, second.getStart()));
                    removeInOrder(roads, i, j);
                    removeInOrder(lines, i, j);
                    j = lines.size();
                }
            }
        }
    }

    public Hospital[] getHospitals() {
        return hospitals.toArray(new Hospital[0]);
    }

    public Road[] getRoads() {
        return roads.toArray(new Road[0]);
    }

    private void removeInOrder(ArrayList arr, int i, int j) {
        if (i > j) {
            arr.remove(i);
            arr.remove(j);
        } else {
            arr.remove(j);
            arr.remove(i);
        }
    }

    public void sort() {
        roads.sort((Road o1, Road o2) -> {
            if (o1.getIdFirst() - o2.getIdFirst() == 0) {
                return o1.getIdSecond() - o2.getIdSecond();
            } else {
                return o1.getIdFirst() - o2.getIdFirst();
            }
        });
    }

}
