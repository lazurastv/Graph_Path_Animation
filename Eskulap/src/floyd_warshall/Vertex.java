package floyd_warshall;

public class Vertex {

	private static int id_counter = 0;
	private int id;
	private int org_id;

	public Vertex(int org_id) {
		this.org_id = org_id;
		id = id_counter++;
	}

	public int getId() {
		return id;
	}

	public int getOrgId() {
		return org_id;
	}
}
