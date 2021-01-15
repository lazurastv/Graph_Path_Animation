package storage;

import java.awt.Point;

public class Patient {

	private final int id;
	private final Point wsp;

	public Patient(int id, int wspx, int wspy) {
		this.id = id;
		this.wsp = new Point(wspx, wspy);
	}

	@Override
	public String toString() {
		return id + " | " + wsp.x + " | " + wsp.y;
	}

	public int getId() {
		return id;
	}

	public Point getWsp() {
		return wsp;
	}

	public int findNearestHospital(Hospital[] hospitals) {

		int nearest_index = -1;
		double nearest_dist = -1;

		for (int i = 0; i < hospitals.length; i++) {
			double dist = hospitals[i].getWsp().distance(wsp);
			if (hospitals[i].getBedNumber() != 0 && (dist < nearest_dist || nearest_dist == -1)) {
				nearest_dist = dist;
				nearest_index = i;
			}
		}

		return nearest_index;
	}
}
