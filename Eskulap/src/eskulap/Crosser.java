package eskulap;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
                    double ratio = one.distance / first.length();
                    int len = (int) (ratio * first.distance(true, cross));
                    roads.add(new Road(roads.get(i).id, one.idHospitalFirst, id, len));
                    lines.add(new Line(first.s, cross));
                    roads.add(new Road(lines.size(), one.idHospitalSecond, id, one.distance - len));
                    lines.add(new Line(cross, first.e));
                    ratio = two.distance / second.length();
                    len = (int) (ratio * second.distance(true, cross));
                    roads.add(new Road(roads.get(j).id, two.idHospitalFirst, id, len));
                    lines.add(new Line(second.s, cross));
                    roads.add(new Road(lines.size(), two.idHospitalSecond, id, two.distance - len));
                    lines.add(new Line(cross, second.s));
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

    public void reenumerate() {
        roads.sort(new Comparator<Road>() {
            @Override
            public int compare(Road o1, Road o2) {
                return o1.id - o2.id;
            }
        });
    }
    
}
