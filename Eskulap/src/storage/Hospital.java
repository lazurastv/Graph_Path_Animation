package storage;

import java.awt.Point;

public class Hospital {
	private final int id;
	private final String name;
	private final Point wsp;
	private final int bedNumber;
	private int freeBedCount;

	public Hospital(int id, String name, int wspx, int wspy, int bedNumber, int freeBedCount) {
		this.id = id;
		this.name = name;
		wsp = new Point(wspx, wspy);
		this.bedNumber = bedNumber;
		this.freeBedCount = freeBedCount;
	}

	@Override
	public String toString() {
		return id + " | " + name + " | " + wsp.x + " | " + wsp.y + " | " + bedNumber + " | " + freeBedCount;
	}

	public int getId() {
		return id;
	}

	public Point getWsp() {
		return wsp;
	}

	public int getBedNumber() {
		return bedNumber;
	}

	public int getFreeBedCount() {
		return freeBedCount;
	}

	public int addPatient(boolean visited[]) {
		if (freeBedCount > 0) {
			freeBedCount--;
			return 0;

		} else {
			for (int i = 0; i < visited.length; i++) {
				if (visited[i] == false) {
					return -1;
				}
			}

			freeBedCount--;
			return -2;
		}
	}
}
