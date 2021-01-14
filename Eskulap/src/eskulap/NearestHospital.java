package eskulap;

import storage.Hospital;
import storage.Patient;

public class NearestHospital {
	
	public static Hospital findNearestHospital(Hospital [] hospitals, Patient patient) {
		
		Hospital nearest = hospitals[0];
		double ndist = hospitals[0].getWsp().distance(patient.getWsp());
		
		for (int i = 1; i< hospitals.length;i++) {
			double dist = hospitals[i].getWsp().distance(patient.getWsp());
			if (dist < ndist) {
				ndist = dist;
				nearest = hospitals[i];
			}
		}
		
		return nearest;
	}
	
}
