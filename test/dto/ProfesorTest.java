package dto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase Profesor
 * @author Lourdes Navarro Tocón
 */
public class ProfesorTest {
    
    private Profesor p;
    
    public ProfesorTest() {
        p = new Profesor("26666666F", "Cristian Jimenez");
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
     * Test del método getDni, de la clase Profesor
     */
    @Test
    public void testGetDni() {
        String dni = p.getDni();
        assertEquals("26666666F", dni);
    }

    /**
     * Test del método setDni, de la clase Profesor
     */
    @Test
    public void testSetDni() {
        String dni = "27777777G";
        p.setDni(dni);
        assertEquals(dni, p.getDni());
    }

    /**
     * Test del método getNombre, de la clase Profesor
     */
    @Test
    public void testGetNombre() {
        String nombre = p.getNombre();
        assertEquals("Cristian Jimenez", nombre);
    }

    /**
     * Test del método setNombre, de la clase Profesor
     */
    @Test
    public void testSetNombre() {
        String nombre = "Carlos Jimenez";
        p.setNombre(nombre);
        assertEquals(nombre, p.getNombre());
    }
    
}
