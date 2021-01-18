package eskulap;

import static org.junit.Assert.*;
import org.junit.Test;
import storage.Hospital;
import storage.Road;

public class CrosserTest {

    Crosser crosser;
    int[] x, y, id_1, id_2;
    int[] sol_x, sol_y;
    int h_count, r_count;

    @Test
    public void testWithCrossings() {
        x = new int[]{0, 40, 0, 40, 40};
        y = new int[]{0, 0, 40, 40, 30};
        id_1 = new int[]{0, 0, 1, 1, 2, 3};
        id_2 = new int[]{3, 4, 2, 4, 4, 4};
        sol_x = new int[]{20, 22, 32};
        sol_y = new int[]{20, 17, 32};
        h_count = 8;
        r_count = 12;
        prepareData();
    }
    
    @Test
    public void testWhenAllAtopEachOther() {
        x = new int[]{0, 10, 20, 30, 40};
        y = new int[]{0, 10, 20, 30, 40};
        id_1 = new int[]{0, 0, 0, 0, 1, 1, 1, 2, 2, 3};
        id_2 = new int[]{1, 2, 3, 4, 2, 3, 4, 3, 4, 4};
        sol_x = new int[]{};
        sol_y = new int[]{};
        h_count = 5;
        r_count = 10;
        prepareData();
    }
    
    @Test
    public void testNoCrossings() {
        x = new int[]{0, 40, 0, 40, 40};
        y = new int[]{0, 0, 40, 40, 30};
        id_1 = new int[]{0, 0, 0, 1, 2, 3};
        id_2 = new int[]{3, 2, 4, 4, 3, 4};
        sol_x = new int[]{20, 22, 32};
        sol_y = new int[]{20, 17, 32};
        h_count = 5;
        r_count = 6;
        prepareData();
    }

    private void prepareData() {
        Hospital[] hospitals = new Hospital[x.length];
        for (int i = 0; i < hospitals.length; i++) {
            hospitals[i] = new Hospital(i, "" + i, x[i], y[i], 0, 0);
        }
        Road[] roads = new Road[id_1.length];
        for (int i = 0; i < roads.length; i++) {
            roads[i] = new Road(i, id_1[i], id_2[i], 100);
        }
        performTest(hospitals, roads);
    }

    private void performTest(Hospital[] hospitals, Road[] roads) {
        crosser = new Crosser(hospitals, roads);
        hospitals = crosser.getHospitals();
        roads = crosser.getRoads();
        assertEquals(h_count, hospitals.length);
        for (int i = x.length; i < h_count; i++) {
            assertEquals(sol_x[i - 5], hospitals[i].getWsp().x);
            assertEquals(sol_y[i - 5], hospitals[i].getWsp().y);
        }
        assertEquals(r_count, roads.length);
        for (Road r : roads) {
            System.out.println(r);
        }
        System.out.println();
    }

}
