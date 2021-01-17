package floyd_warshall;

import java.util.Arrays;
import java.util.LinkedList;
import storage.Map;

public class FloydWarshallAlgorithm {

	private final Vertex[] vertices;
	private final Edge[] edges;
	private final double[][] distMatrix;
	private final int[][] nextVertexMatrix;
	private final boolean[] visited;
	private static final double MAX = Double.MAX_VALUE;

	public FloydWarshallAlgorithm(Map map) {
		vertices = Vertex.vertexArray(map.getHospitals());
		edges = Edge.edgeArray(vertices, map.getRoads());
		distMatrix = new double[vertices.length][vertices.length];
		nextVertexMatrix = new int[vertices.length][vertices.length];
		visited = new boolean[vertices.length];
		fillVisited();
	}

	public void reset() {
		fillVisited();
	}

	private void fillVisited() {
		for (int i = 0; i < vertices.length; i++) {
			visited[i] = vertices[i].getVisited();
		}

	}

	public boolean[] getVisited() {
		return visited;
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
		path.addLast(start);

		while (start != end) {
			start = nextVertexMatrix[start][end];
			path.addLast(start);
		}

		return path.stream().mapToInt(Integer::intValue).toArray();
	}

	public int getClosestVertex(int start) {
		if (start >= vertices.length || start < 0) {
			return -1;
		}
		
		visited[start] = true;
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

		if (endDist == MAX || visited[end] == true) {
			return -2;
		}

		visited[end] = true;

		return end;
	}

}
