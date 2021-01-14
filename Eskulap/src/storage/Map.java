package storage;

public class Map {
    private Hospital[] hospitals;
    private Construction[] constructs;
    private Road[] roads;
    
    public void setHospitals(Hospital[] hos) {
        hospitals = hos;
    }
    
    public void setConstructs(Construction[] con) {
        constructs = con;
    }
    
    public void setRoads(Road[] ros) {
        roads = ros;
    }
    
    public Hospital[] getHospitals() {
        return hospitals;
    }
    
    public Construction[] getConstructs() {
        return constructs;
    }
    
    public Road[] getRoads() {
        return roads;
    }
    
}
