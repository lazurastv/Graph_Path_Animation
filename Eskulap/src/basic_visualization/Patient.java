package eskulap;

public class Patient {

    int id;
    int wspx;
    int wspy;

    public Patient(int id, int wspx, int wspy) {
        this.id = id;
        this.wspx = wspx;
        this.wspy = wspy;
    }

    @Override
    public String toString() {
        return id + " | " + wspx + " | " + wspy;
    }
}
