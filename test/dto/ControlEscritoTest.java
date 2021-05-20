package dto;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase ControlEscrito
 * @author Lourdes Navarro Tocón
 */
public class ControlEscritoTest {
    
    private ControlEscrito c;
    
    public ControlEscritoTest() {
        c = new ControlEscrito(6, 15, Date.valueOf("2021-05-20"));
        c.getNotasControl().put(1, 7.0);
        c.getNotasControl().put(2, 4.0);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test del método getNumControl, de la clase ControlEscrito
     */
    @Test
    public void testGetNumControl() {
        int numControl = c.getNumControl();
        assertEquals(6, numControl);
    }

    /**
     * Test del método setNumControl, de la clase ControlEscrito
     */
    @Test
    public void testSetNumControl() {
        int numControl = 7;
        c.setNumControl(numControl);
        assertEquals(numControl, c.getNumControl());
    }

    /**
     * Test del método getNumPreguntas method, de la clase ControlEscrito
     */
    @Test
    public void testGetNumPreguntas() {
        int numPreg = c.getNumPreguntas();
        assertEquals(15, numPreg);
    }

    /**
     * Test del método setNumPreguntas, de la clase ControlEscrito
     */
    @Test
    public void testSetNumPreguntas() {
        int numPreg = 16;
        c.setNumPreguntas(numPreg);
        assertEquals(numPreg, c.getNumPreguntas());
    }

    /**
     * Test del método getFecha, de la clase ControlEscrito
     */
    @Test
    public void testGetFecha() {
        Date fecha = c.getFecha();
        assertEquals(Date.valueOf("2021-05-20"), fecha);
    }

    /**
     * Test del método setFecha, de la clase ControlEscrito
     */
    @Test
    public void testSetFecha() {
        Date fecha = Date.valueOf("2021-05-21");
        c.setFecha(fecha);
        assertEquals(fecha, c.getFecha());
    }

    /**
     * Test del método getNotasControl, de la clase ControlEscrito
     */
    @Test
    public void testGetNotasControl() {
        Map<Integer, Double> notas = c.getNotasControl();
        assertEquals(2, notas.size());
        assertEquals(7.0, notas.get(1), 0);
        assertEquals(4.0, notas.get(2), 0);
    }

    /**
     * Test del método setNotasControl, de la clase ControlEscrito
     */
    @Test
    public void testSetNotasControl() {
        Map<Integer, Double> notas = new HashMap<>();
        notas.put(1, 8.0);
        notas.put(2, 3.0);
        c.setNotasControl(notas);
        assertEquals(notas, c.getNotasControl());
    }

}
