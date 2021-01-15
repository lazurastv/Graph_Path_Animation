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
        for (Line l : lines) {
            int id_s = -1;
            int id_e = -1;
            for (Hospital h : hos) {
                if (l.getStart().equals(h.getWsp())) {
                    id_s = h.getId();
                } else if (l.getEnd().equals(h.getWsp())) {
                    id_e = h.getId();
                }
            }
            for (Road r : ros) {
                if (r.getIdFirst() == id_s && r.getIdSecond() == id_e) {
                    roads.add(r);
                    break;
                }
            }
        }
        for (int i = 0; i < lines.size(); i++) {
            Line first = lines.get(i);
            Road one = roads.get(i);
            for (int j = i + 1; j < lines.size(); j++) {
                Line second = lines.get(j);
                Road two = roads.get(j);
                if (first.getRightX() > second.getLeftX()) {
                    Point cross = first.crossLines(second);
                    if (cross != null && first.contains(cross) && second.contains(cross)) {
                        int id = hospitals.size() + 1;
                        hospitals.add(new Hospital(id, " Skrzyżowanie ", cross.x, cross.y, 0, 0));

                        removeInOrder(roads, i, j);
                        removeInOrder(lines, i, j);

                        double ratio = one.getDistance() / first.length();
                        double len = ratio * first.distFromStart(cross);
                        if (first.startIsLeft()) {
                            roads.add(new Road(one.getId(), one.getIdFirst(), id, len));
                            roads.add(j, new Road(roads.size(), one.getIdSecond(), id, one.getDistance() - len));
                        } else {
                            roads.add(j, new Road(one.getId(), one.getIdFirst(), id, len));
                            roads.add(new Road(roads.size(), one.getIdSecond(), id, one.getDistance() - len));
                        }
                        lines.add(j, new Line(cross, first.getRightPoint()));

                        ratio = two.getDistance() / second.length();
                        len = ratio * second.distFromStart(cross);
                        if (second.startIsLeft()) {
                            roads.add(new Road(two.getId(), two.getIdFirst(), id, len));
                            roads.add(j + 1, new Road(roads.size(), two.getIdSecond(), id, two.getDistance() - len));
                        } else {
                            roads.add(j + 1, new Road(two.getId(), two.getIdFirst(), id, len));
                            roads.add(new Road(roads.size(), two.getIdSecond(), id, two.getDistance() - len));
                        }
                        lines.add(j + 1, new Line(cross, second.getRightPoint()));
                        
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        enumerate(roads);
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

    private void sort(ArrayList<Line> l) {
        l.sort((Line o1, Line o2) -> {
            return o1.getLeftX() - o2.getLeftX();
        });
    }

    private void enumerate(ArrayList<Road> r) {
        r.sort((Road o1, Road o2) -> {
            if (o1.getIdFirst() == o2.getIdFirst()) {
                return o1.getIdSecond() - o2.getIdSecond();
            } else {
                return o1.getIdFirst() - o2.getIdFirst();
            }
        });
    }

}
