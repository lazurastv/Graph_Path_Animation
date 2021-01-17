package file_managment;

import java.awt.Point;
import storage.Hospital;
import storage.Patient;
import storage.Road;
import storage.Construction;
import storage.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class FileManager {

    private int nline = 1;
    HashSet<Point> points = new HashSet<>();

    public Patient[] readPatients(String fname) throws IOException, FileReadingException {
        ArrayList<Patient> tmp = new ArrayList<>();
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String line;
        nline = 1;
        try{
        while ((line = br.readLine()) != null) {
            if (line.toCharArray().length == 0) {
                nline++;
                continue;
            }
            if (line.toCharArray()[0] == '#') {
                nline++;
                continue;
            }
            String[] w = line.split("\\s*[|]s*");
            if (w.length == 3) {
                try {
                    Patient p = new Patient(Integer.parseInt(w[0]), Integer.parseInt(w[1].trim()), Integer.parseInt(w[2].trim()));
                    tmp.add(p);
                } catch (NumberFormatException ex) {
                    throw new FileReadingException(nline, line);
                }
                nline++;
            } else {
                throw new FileReadingException(nline, line);
            }

        }
        }finally{
            br.close();
        }
        return tmp.toArray(new Patient[0]);
    }

    public Map readHospitals(String fname) throws IOException, FileReadingException {
        Map map = new Map();
        ArrayList<Hospital> tmp = new ArrayList<>();
        FileReader fr = new FileReader(fname);
        BufferedReader br = new BufferedReader(fr);
        String line;
        boolean next = false;
        
        try{
        nline = 1;
        while ((line = br.readLine()) != null) {
            if (line.toCharArray().length == 0) {
                nline++;
                continue;
            }
            if (line.toCharArray()[0] == '#') {
                if (next) {
                    break;
                } else {
                    next = true;
                    nline++;
                    continue;
                }
            }
            String[] w = line.split("\\s*[|]s*");
            if (w.length == 6) {
                try {
                    Hospital h = new Hospital(Integer.parseInt(w[0]), w[1].trim(), Integer.parseInt(w[2].trim()),
                            Integer.parseInt(w[3].trim()), Integer.parseInt(w[4].trim()), Integer.parseInt(w[5].trim()));
                    tmp.add(h);
                    if (points.contains(h.getWsp())) {
                        throw new DuplicatedPointException();
                    } else {
                        points.add(h.getWsp());
                    }
                    nline++;
                } catch (NumberFormatException ex) {
                    throw new FileReadingException(nline, line);
                }
            } else {
                throw new FileReadingException(nline, line);
            }

        }
        map.setHospitals(tmp.toArray(new Hospital[0]));
        map.setConstructs(readConstructions(br));
        if (points.size() < 3) {
            throw new NotEnoughPointsException(); 
        }
        boolean allColinear = true;
        Point first = map.getHospitals()[0].getWsp();
        for (Point p : points) {
            if (p.x != first.x) {
                allColinear = false;
                break;
            }
        }
        if (allColinear) {
            throw new AllPointsColinearException();
        }
        allColinear = true;
        for (Point p : points) {
            if (p.y != first.y) {
                allColinear = false;
                break;
            }
        }
        if (allColinear) {
            throw new AllPointsColinearException();
        }
        map.setRoads(readRoads(br));
        points.clear();
        }finally{
            br.close();
        }
        return map;
    }

    private Construction[] readConstructions(BufferedReader br) throws IOException, FileReadingException {
        ArrayList<Construction> tmp = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            if (line.toCharArray().length == 0) {
                nline++;
                continue;
            }
            if (line.toCharArray()[0] == '#') {
                nline++;
                break;
            }
            String[] w = line.split("\\s*[|]s*");
            if (w.length == 4) {
                try {
                    Construction c = new Construction(Integer.parseInt(w[0]), w[1].trim(), Integer.parseInt(w[2].trim()),
                            Integer.parseInt(w[3].trim()));
                    tmp.add(c);
                    if (points.contains(c.getWsp())) {
                        throw new DuplicatedPointException();
                    } else {
                        points.add(c.getWsp());
                    }
                    nline++;
                } catch (NumberFormatException ex) {
                    throw new FileReadingException(nline, line);
                }
            } else {
                throw new FileReadingException(nline, line);
            }
        }
        return tmp.toArray(new Construction[0]);
    }

    private Road[] readRoads(BufferedReader br) throws IOException, FileReadingException {
        ArrayList<Road> tmp = new ArrayList<>();
        String line;

        while ((line = br.readLine()) != null) {

            String[] w = line.split("\\s*[|]s*");
            if (w.length == 4) {
                try {
                    Road r = new Road(Integer.parseInt(w[0]), Integer.parseInt(w[1].trim()), Integer.parseInt(w[2].trim()),
                            Integer.parseInt(w[3].trim()));
                    tmp.add(r);
                } catch (NumberFormatException ex) {
                    throw new FileReadingException(nline, line);
                }
            } else {
                throw new FileReadingException(nline, line);
            }
        }
        return tmp.toArray(new Road[0]);
    }
}
