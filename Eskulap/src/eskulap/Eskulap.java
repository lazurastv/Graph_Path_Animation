package eskulap;

import java.awt.EventQueue;
import java.io.IOException;

public class Eskulap {
    public static void main(String[] args) throws IOException {
        FileManager fileManager = new FileManager();
        Patient[] patients = fileManager.readPatients("pacjenci.txt");  
        Hospital[] hospitals = fileManager.readHospitals("szpitale.txt");
        Construction[] constructions = fileManager.readConstructions("obiekty.txt");
        Road[] roads = fileManager.readRoads("drogi.txt");
        Crosser crosser = new Crosser(hospitals, roads);
        crosser.reenumerate();
        for (Road r : crosser.getRoads()) {
            System.out.println(r);
        }
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Board(crosser.getHospitals(), crosser.getRoads());
                //new Board(hospitals, roads);
            }
        });
    }
}
