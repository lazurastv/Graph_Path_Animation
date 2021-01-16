package eskulap;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import storage.Map;
import storage.Hospital;
import storage.Patient;
import storage.Road;
import storage.Construction;
import static org.junit.jupiter.api.Assertions.*;

public class FileManagerTest {

    private FileManager fManager;

    @Before
    public void setUp() {
        fManager = new FileManager();
    }

    @Test
    public void testReadPatients() throws IOException {
        String fname = "test/txt/pacjenci.txt";

        Patient[] expResult = {new Patient(1, 20, 20),
            new Patient(2, 99, 105),
            new Patient(3, 23, 40)};
        Patient[] result = fManager.readPatients(fname);

        assertArrayEquals(expResult, result);
    }

    @Test
    public void testReadHospitals() throws Exception {
        String fname = "test/txt/szpitale.txt";

        Hospital[] expResultHospitals = {new Hospital(1, "Szpital Wojewodzki nr 997", 10, 10, 1000, 100),
            new Hospital(2, "Krakowski Szpital Kliniczny", 100, 120, 999, 99),
            new Hospital(3, "Pierwszy Szpital im. Prezesa RP", 120, 130, 99, 0),
            new Hospital(4, "Drugi Szpital im. Naczelnika RP", 10, 140, 70, 1),
            new Hospital(5, "Trzeci Szpital im. Krola RP", 140, 10, 996, 0)};
        Construction[] expResultContructions = {new Construction(1, "Pomnik Wikipedii", -1, 50),
            new Construction(2, "Pomnik Fryderyka Chopina", 110, 55),
            new Construction(3, "Pomnik Anonimowego Przechodnia", 40, 70)};
        Road[] expResultRoads = {new Road(1, 1, 2, 700), new Road(2, 1, 4, 550),
            new Road(3, 1, 5, 800), new Road(4, 2, 3, 300),
            new Road(5, 2, 4, 550), new Road(6, 3, 5, 600),
            new Road(7, 4, 5, 750)};
        Map expResult = new Map();
        expResult.setHospitals(expResultHospitals);
        expResult.setConstructs(expResultContructions);
        expResult.setRoads(expResultRoads);
        Map result = fManager.readHospitals(fname);

        assertTrue(result.equals(expResult));
    }

    @Test(expected = NumberFormatException.class)
    public void should_throwNumberFormatException_whenParsedString_isNotANumber() throws IOException {
        String fname = "test/txt/exceptions/NumberFormatException.txt";

        fManager.readPatients(fname);

        assert false;
    }

}
