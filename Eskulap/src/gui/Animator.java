package gui;

import floyd_warshall.FloydWarshallAlgorithm;
import java.awt.Point;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import storage.Map;
import storage.Patient;

public class Animator extends Thread {

    private double p_x, p_y;
    private double dx, dy;
    private Point end;
    private Point start;
    private final Graph graph;
    private final Rectangle2D[] hospitals;
    private final Map map;
    private final FloydWarshallAlgorithm fwa;
    private Patient patient;
    private final ArrayList<Patient> patients;
    private int[] path;
    private int i = 0;
    private int percent = 50;
    private static final int MIN_DELAY = 10;

    public Animator(Graph g, Rectangle2D[] h, Map m) {
        graph = g;
        hospitals = h;
        map = m;
        fwa = new FloydWarshallAlgorithm(map);
        fwa.applyAlgorithm();
        patients = new ArrayList<>();
    }

    public void nextPatient() {
        if (patient == null && patients.size() > 0) {
            patient = patients.get(0).clone();
            patients.remove(0);
            graph.setPatient(patient);
            fwa.reset();
            Patient tmp_p = new Patient(0, graph.unscale_x(patient.getWsp().x), graph.unscale_y(patient.getWsp().y));
            int close = tmp_p.findNearestHospital(map.getHospitals());
            setupLine(close, 0);
        }
    }

    private void print() {
        if (map.getHospitals()[path[i]].getBedNumber() != 0) {
            graph.getCenter().print(" -> Szpital " + map.getHospitals()[path[i]].getId());
        } else {
            graph.getCenter().print(" -> Skrzyżowanie ");
        }
    }

    private void setupLine(int from, int skip) {
        i = skip;
        int to = fwa.getClosestVertex(from);
        path = fwa.getPath(from, to);
        graph.getCenter().print("Pacjent " + patient.getId());
        print();
        start = (Point) patient.getWsp().clone();
        p_x = patient.getWsp().x;
        p_y = patient.getWsp().y;
        getTarget();
        getLine();
    }

    public void setPercent(int per) {
        percent = per;
    }

    public void addPatient(Patient p) {
        patients.add(p);
    }

    private void getTarget() {
        if (i < path.length) {
            end = new Point((int) hospitals[path[i]].getCenterX(), (int) hospitals[path[i]].getCenterY());
        }
    }

    private void getLine() {
        dx = getDx();
        dy = direction();
        if (Double.isInfinite(dy)) {
            dx = 0;
            dy = getDy();
        } else if (dx < 0) {
            dy = -dy;
        }
    }

    private int getDx() {
        if (start.x < end.x) {
            return 1;
        } else {
            return -1;
        }
    }

    private int getDy() {
        if (start.y < end.y) {
            return 1;
        } else {
            return -1;
        }
    }

    private boolean crossedX() {
        return end.x != start.x && (end.x - start.x) * (end.x - patient.getWsp().x) <= 0;
    }

    private boolean crossedY() {
        return end.y != start.y && (end.y - start.y) * (end.y - patient.getWsp().y) <= 0;
    }

    private double direction() {
        return (double) (end.y - start.y) / (end.x - start.x);
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            nextPatient();
            if (patient != null && percent > 0) {
                graph.repaint();
                long time = System.currentTimeMillis();
                while (System.currentTimeMillis() - time < MIN_DELAY * 100 / percent) {
                }
                p_x += dx;
                p_y += dy;
                patient.move((int) p_x, (int) p_y);
                if (crossedX() || crossedY()) {
                    patient.move(end.x, end.y);
                    p_x = patient.getWsp().x;
                    p_y = patient.getWsp().y;
                    graph.repaint();
                    if (i == 0 || i + 1 == path.length) {
                        int ret = map.getHospitals()[path[i]].addPatient(fwa.getVisited());
                        switch (ret) {
                            case 0:
                                graph.getCenter().print(" -> Jest miejsce.\n");
                                patient = null;
                                break;
                            case -1:
                                graph.getCenter().print(" -> Brak miejsc.\n");
                                setupLine(path[i], 1);
                                for (boolean b : fwa.getVisited()) {
                                    System.out.print(b + " ");
                                }
                                break;
                            case -2:
                                graph.getCenter().print(" -> Dodano do kolejki.\n");
                                patient = null;
                        }
                    } else {
                        i++;
                        print();
                        start = (Point) patient.getWsp().clone();
                        getTarget();
                        getLine();
                    }
                }
            }
        }
    }
}
