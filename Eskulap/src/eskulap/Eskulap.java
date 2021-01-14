package eskulap;

import gui.Board;
import storage.Hospital;
import storage.Patient;
import storage.Road;
import storage.Construction;
import floyd_warshall.Edge;
import floyd_warshall.FloydWarshallAlgorithm;
import floyd_warshall.Vertex;
import java.awt.EventQueue;
import java.io.IOException;
import storage.Map;

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
        Map map = fileManager.readHospitals("szpitale.txt");
        Crosser crosser = new Crosser(map.getHospitals(), map.getRoads());
        crosser.sort();
        Hospital[] hospitals = crosser.getHospitals();
        Road[] roads = crosser.getRoads();
        Vertex[] vertices = new Vertex[hospitals.length];
        Edge[] edges = new Edge[roads.length];
        for (int i = 0; i < hospitals.length; i++) {
            vertices[i] = new Vertex(hospitals[i]);
        }
        for (int i = 0; i < roads.length; i++) {
            edges[i] = new Edge(findIndex(roads[i].getIdFirst(), vertices), findIndex(roads[i].getIdSecond(), vertices), roads[i].getDistance());
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
