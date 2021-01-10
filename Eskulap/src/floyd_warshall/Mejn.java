package floyd_warshall;

public class Mejn {

    public static void main(String[] args) {
        Vertex[] vertices = new Vertex[5];
        Edge[] edges = new Edge[6];

        for (int i = 0; i < 5; i++) {
            vertices[i] = new Vertex(i + 3);
        }

        edges[0] = new Edge(vertices[0], vertices[1], 50);
        edges[1] = new Edge(vertices[0], vertices[2], 100);
        edges[2] = new Edge(vertices[1], vertices[3], 200);
        edges[3] = new Edge(vertices[1], vertices[4], 35);
        edges[4] = new Edge(vertices[2], vertices[3], 140);
        edges[5] = new Edge(vertices[2], vertices[4], 10);

        FloydWarshallAlgorithm fwl = new FloydWarshallAlgorithm(vertices, edges);
        fwl.applyAlgorithm();
        System.out.print(fwl.toString() + "\n" + "path: ");

        int[] path = fwl.getPath(3, 0);
        for (int i : path) {
            System.out.print(i + " ");
        }
        System.out.print("\n" + "path: ");
        path = fwl.getPath(0, 2);
        for (int i : path) {
            System.out.print(i + " ");
        }
        System.out.print("\n" + "closest to " + 0 + ": " + fwl.getClosestVertex(0));
        System.out.print("\n" + "closest to " + 1 + ": " + fwl.getClosestVertex(1));
        System.out.print("\n" + "closest to " + 2 + ": " + fwl.getClosestVertex(2));
        System.out.print("\n" + "closest to " + 3 + ": " + fwl.getClosestVertex(3));
    }

}
