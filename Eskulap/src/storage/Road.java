package storage;

public class Road {
    private final int id;
    private final int idHospitalFirst;
    private final int idHospitalSecond;
    private final double distance;
    
    public Road(int id, int idHospitalFirst, int idHospitalSecond, double distance) {
        this.id = id;
        this.idHospitalFirst = idHospitalFirst;
        this.idHospitalSecond = idHospitalSecond;
        this.distance = distance;
    }
    
    @Override
    public String toString() {
        return id + " | " + idHospitalFirst + " | " + idHospitalSecond + " | " + distance;
    }
    
    public int getId() {
        return id;
    }
    
    public int getIdFirst() {
        return idHospitalFirst;
    }
    
    public int getIdSecond() {
        return idHospitalSecond;
    }
    
    public double getDistance() {
        return distance;
    }
}
