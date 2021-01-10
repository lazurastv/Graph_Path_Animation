package eskulap;

import floyd_warshall.Edge;
import floyd_warshall.FloydWarshallAlgorithm;
import floyd_warshall.Vertex;
import java.awt.EventQueue;
import java.io.IOException;

public class Eskulap {
    private static Hospital findIndex(int i, Hospital[] h) {
        for (Hospital h1 : h) {
            if (h1.id == i) {
                return h1;
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
            edges[i].setStart(new Vertex(findIndex(roads[i].idHospitalFirst, hospitals)));
            edges[i].setEnd(new Vertex(findIndex(roads[i].idHospitalSecond, hospitals)));
        }
        FloydWarshallAlgorithm fwa = new FloydWarshallAlgorithm(vertices, edges); //cokolwiek tu trzeba dalej
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Board(crosser.getHospitals(), crosser.getRoads());
                //new Board(hospitals, roads);
            }
        });
    }
}
