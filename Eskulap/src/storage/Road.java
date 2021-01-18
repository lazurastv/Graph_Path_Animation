package storage;

public class Road implements Comparable<Road> {

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof Road) {
            Road r = (Road) o;
            return this.id == r.id && this.idHospitalFirst == r.idHospitalFirst
                    && this.idHospitalSecond == r.idHospitalSecond && this.distance == r.distance;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.id;
        hash = 73 * hash + this.idHospitalFirst;
        hash = 73 * hash + this.idHospitalSecond;
        hash = 73 * hash + (int) (Double.doubleToLongBits(this.distance) ^ (Double.doubleToLongBits(this.distance) >>> 32));
        return hash;
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

    @Override
    public int compareTo(Road o) {
        if (getIdFirst() == o.getIdFirst()) {
            return getIdSecond() - o.getIdSecond();
        } else {
            return getIdFirst() - o.getIdFirst();
        }
    }
}
