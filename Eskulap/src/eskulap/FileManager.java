package eskulap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

    public Patient[] readPatients(String fname) throws IOException {
        ArrayList<Patient> tmp = new ArrayList<>();
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        while ((line = br.readLine()) != null) {
            if (line.toCharArray()[0] == '#') {
                continue;
            }
            String[] w = line.split("\\s+[|]");
            if (w.length == 3) {
                try {
                    Patient p = new Patient(Integer.parseInt(w[0]), Integer.parseInt(w[1].trim()), Integer.parseInt(w[2].trim()));
                    tmp.add(p);
                } catch (NumberFormatException e) {
                }
            }
        }

        return tmp.toArray(new Patient[0]);
    }

    public Hospital[] readHospitals(String fname) throws IOException {
        ArrayList<Hospital> tmp = new ArrayList<>();
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        while ((line = br.readLine()) != null) {
            if (line.toCharArray()[0] == '#') {
                continue;
            }
            String[] w = line.split("\\s+[|]");
            if (w.length == 6) {
                try {
                    Hospital h = new Hospital(Integer.parseInt(w[0]), w[1], Integer.parseInt(w[2].trim()),
                            Integer.parseInt(w[3].trim()), Integer.parseInt(w[4].trim()), Integer.parseInt(w[5].trim()));
                    tmp.add(h);
                } catch (NumberFormatException e) {
                }
            }
        }

        return tmp.toArray(new Hospital[0]);
    }

    public Construction[] readConstructions(String fname) throws IOException {
        ArrayList<Construction> tmp = new ArrayList<>();
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        while ((line = br.readLine()) != null) {
            if (line.toCharArray()[0] == '#') {
                continue;
            }
            String[] w = line.split("\\s+[|]");
            if (w.length == 4) {
                try {
                    Construction c = new Construction(Integer.parseInt(w[0]), w[1], Integer.parseInt(w[2].trim()),
                            Integer.parseInt(w[3].trim()));
                    tmp.add(c);
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return tmp.toArray(new Construction[0]);
    }
    public Road[] readRoads(String fname) throws IOException {
        ArrayList<Road> tmp = new ArrayList<>();
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String line = null;

        while ((line = br.readLine()) != null) {
            if (line.toCharArray()[0] == '#') {
                continue;
            }
            String[] w = line.split("\\s+[|]");
            if (w.length == 4) {
                try {
                    Road r = new Road(Integer.parseInt(w[0]), Integer.parseInt(w[1].trim()), Integer.parseInt(w[2].trim()),
                            Integer.parseInt(w[3].trim()));
                    tmp.add(r);
                } catch (NumberFormatException e) {
                }
            }
        }
        
        return tmp.toArray(new Road[0]);
    }
}