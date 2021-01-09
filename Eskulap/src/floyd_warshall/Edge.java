package floyd_warshall;

public class Edge {

	private Vertex start;
	private Vertex end;
	private double dist;

	public Edge(Vertex start, Vertex end, double dist) {
		this.start = start;
		this.end = end;
		this.dist = dist;
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
