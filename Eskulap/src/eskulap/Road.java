package eskulap;

public class Road {
    int id;
    public int idHospitalFirst;
    public int idHospitalSecond;
    public int distance;
    
    public Road(int id, int idHospitalFirst, int idHospitalSecond, int distance) {
        this.id =id;
        this.idHospitalFirst = idHospitalFirst;
        this.idHospitalSecond = idHospitalSecond;
        this.distance = distance;
    }
    
    @Override
    public String toString() {
        return id + " | " + idHospitalFirst + " | " + idHospitalSecond + " | " + distance;
    }
}
