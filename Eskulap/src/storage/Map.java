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
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Hospital h : hospitals) {
            sb.append(h).append('\n');
        }
        for (Construction c : constructs) {
            sb.append(c).append('\n');
        }
        for (Road r : roads) {
            sb.append(r).append('\n');
        }
        return sb.toString();
    }
    
}
