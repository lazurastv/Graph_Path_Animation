package floyd_warshall;

import storage.Hospital;

public class Vertex {

    private static int id_counter = 0;
    private final int id;
    private final int org_id;
    private boolean visited;

    public Vertex(int org_id) {
        this.org_id = org_id;
        id = id_counter++;
        visited = false;
    }
    
    public Vertex(Hospital h) {
        org_id = h.getId();
        id = id_counter++;
        visited = h.getBedNumber() == 0;
    }
    
    public static Vertex[] vertexArray(Hospital[] hos) {
        Vertex[] v = new Vertex[hos.length];
        for (int i = 0; i < v.length; i++) {
            v[i] = new Vertex(hos[i]);
        }
        return v;
    }

    public int getId() {
        return id;
    }

    public int getOrgId() {
        return org_id;
    }
    
    public void setVisited() {
        visited = true;
    }
    
    public boolean getVisited() {
        return visited;
    }
}
