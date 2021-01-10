package floyd_warshall;

import java.util.Arrays;
import java.util.LinkedList;

public class FloydWarshallAlgorithm {

	private static final double MAX = Double.MAX_VALUE;
	private Vertex[] vertices;
	private Edge[] edges;
	private double[][] distMatrix;
	private int[][] nextVertexMatrix;
	private boolean[] visited;

	public FloydWarshallAlgorithm(Vertex[] vertices, Edge[] edges) {
		this.vertices = vertices;
		this.edges = edges;
		distMatrix = new double[vertices.length][vertices.length];
		nextVertexMatrix = new int[vertices.length][vertices.length];
		visited = new boolean[vertices.length];
		fillVisited(vertices);
	}

	public void fillVisited(Vertex[] vertices) {
		for (int i = 0; i < vertices.length; i++) {
			visited[i] = vertices[i].getVisited();
		}

	}

	public void applyAlgorithm() {
		for (int i = 0; i < vertices.length; i++) {
			Arrays.fill(distMatrix[i], MAX);
			Arrays.fill(nextVertexMatrix[i], -1);
		}

		for (Edge edge : edges) {
			distMatrix[edge.getStartVertexId()][edge.getEndVertexId()] = edge.getDistance();
			nextVertexMatrix[edge.getStartVertexId()][edge.getEndVertexId()] = edge.getEndVertexId();
			distMatrix[edge.getEndVertexId()][edge.getStartVertexId()] = edge.getDistance();
			nextVertexMatrix[edge.getEndVertexId()][edge.getStartVertexId()] = edge.getStartVertexId();
		}

		for (Vertex vertex : vertices) {
			distMatrix[vertex.getId()][vertex.getId()] = 0;
			nextVertexMatrix[vertex.getId()][vertex.getId()] = vertex.getId();
		}

		for (int k = 0; k < vertices.length; k++) {
			for (int i = 0; i < vertices.length; i++) {
				for (int j = 0; j < vertices.length; j++) {
					if (distMatrix[i][k] != MAX && distMatrix[k][j] != MAX) {
						if (distMatrix[i][j] > distMatrix[i][k] + distMatrix[k][j]) {
							distMatrix[i][j] = distMatrix[i][k] + distMatrix[k][j];
							nextVertexMatrix[i][j] = nextVertexMatrix[i][k];
						}
					}
				}
			}
		}

	}

	public int[] getPath(int start, int end) {
		if (nextVertexMatrix[start][end] == -1) {
			return null;
		}

		LinkedList<Integer> path = new LinkedList<>();
		path.addLast(Integer.valueOf(start));

		while (start != end) {
			start = nextVertexMatrix[start][end];
			path.addLast(Integer.valueOf(start));
		}

		return path.stream().mapToInt(Integer::intValue).toArray();
	}

	public int getClosestVertex(int start) {
		int end = 0;

		if (start == 0) {
			end = 1;
		}

		double endDist = distMatrix[start][end];

		for (int i = end + 1; i < vertices.length; i++) {
			double temp = distMatrix[start][i];
			if (temp < endDist && visited[i] == false && temp != 0.0) {
				endDist = temp;
				end = i;
			}
		}

		if (endDist == MAX) {
			return -1;
		}

		visited[end] = true;

		return end;
	}

}
