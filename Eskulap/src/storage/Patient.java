package storage;

public class Patient {

    private final int id;
    private final int wspx;
    private final int wspy;

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
