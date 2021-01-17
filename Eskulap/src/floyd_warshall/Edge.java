package floyd_warshall;

import storage.Road;

public class Edge {

    private final Vertex start;
    private final Vertex end;
    private final double dist;

    public Edge(Vertex start, Vertex end, double dist) {
        this.start = start;
        this.end = end;
        this.dist = dist;
    }
    
    public static Edge[] edgeArray(Vertex[] ver, Road[] ros) {
        Edge[] edges = new Edge[ros.length];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new Edge(findIndex(ros[i].getIdFirst(), ver), findIndex(ros[i].getIdSecond(), ver), ros[i].getDistance());
        }
        return edges;
    }
    
    private static Vertex findIndex(int i, Vertex[] ver) {
        for (Vertex v : ver) {
            if (v.getOrgId() == i) {
                return v;
            }
        }
        return null;
    }

    public int getStartVertexId() {
        return start.getId();
    }

    public int getEndVertexId() {
        return end.getId();
    }

    public double getDistance() {
        return dist;
    }
    
}
