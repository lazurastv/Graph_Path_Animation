package floyd_warshall;

import eskulap.Hospital;

public class Vertex {

    private static int id_counter = 0;
    private int id;
    private int org_id;
    private boolean visited;

    public Vertex(int org_id) {
        this.org_id = org_id;
        id = id_counter++;
        visited = false;
    }
    
    public Vertex(Hospital h) {
        org_id = h.id;
        id = id_counter++;
        visited = h.bedNumber == 0;
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
}
