package floyd_warshall;

import storage.Hospital;

public class Vertex {

    private static int id_counter = 0;
    private final int id;
    private final int org_id;
    private final boolean visited;

    public static Vertex[] vertexArray(Hospital[] hos) {
        reset();
        Vertex[] v = new Vertex[hos.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = new Vertex(hos[i]);
        }
        return v;
    }
    
    private Vertex(Hospital h) {
        org_id = h.getId();
        id = id_counter++;
        visited = h.getBedNumber() == 0;
    }

    public int getId() {
        return id;
    }

    public int getOrgId() {
        return org_id;
    }

    public boolean getVisited() {
        return visited;
    }

    private static void reset() {
        id_counter = 0;
    }
}
