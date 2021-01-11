package eskulap;

import floyd_warshall.Edge;
import floyd_warshall.FloydWarshallAlgorithm;
import floyd_warshall.Vertex;
import java.awt.EventQueue;
import java.io.IOException;

public class Eskulap {

    private static Vertex findIndex(int i, Vertex[] ver) {
        for (Vertex v : ver) {
            if (v.getOrgId() == i) {
                return v;
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        Patient[] patients = fileManager.readPatients("pacjenci.txt");
        Hospital[] hospitals = fileManager.readHospitals("szpitale.txt");
        Construction[] constructions = fileManager.readConstructions("obiekty.txt");
        Road[] roads = fileManager.readRoads("drogi.txt");
        Crosser crosser = new Crosser(hospitals, roads);
        crosser.sort();
        hospitals = crosser.getHospitals();
        roads = crosser.getRoads();
        Vertex[] vertices = new Vertex[hospitals.length];
        Edge[] edges = new Edge[roads.length];
        for (int i = 0; i < hospitals.length; i++) {
            vertices[i] = new Vertex(hospitals[i]);
        }
        for (int i = 0; i < roads.length; i++) {
            edges[i] = new Edge(roads[i]);
            edges[i].setStart(findIndex(roads[i].idHospitalFirst, vertices));
            edges[i].setEnd(findIndex(roads[i].idHospitalSecond, vertices));
        }
        System.out.println("Edges:");
        for (Edge e : edges) {
            System.out.println((e.getStartVertexId() + 1) + " -> " + (e.getEndVertexId() + 1) + " " + e.getDistance());
        }
        FloydWarshallAlgorithm fwa = new FloydWarshallAlgorithm(vertices, edges);
        fwa.applyAlgorithm();
        Vertex v = vertices[0];
        System.out.println("Path:");
        while (v.getOrgId() != 5) {
            int a = fwa.getClosestVertex(v.getId());
            System.out.println(v.getOrgId() + " -> " + (a + 1));
            v = vertices[a];
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Board(crosser.getHospitals(), crosser.getRoads());
                //new Board(hospitals, roads);
            }
        });
    }
}
