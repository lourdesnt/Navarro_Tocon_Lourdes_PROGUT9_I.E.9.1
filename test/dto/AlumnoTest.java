package dto;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase Alumno
 * @author Lourdes Navarro Tocón
 */
public class AlumnoTest {
    
    private Alumno a;
    
    public AlumnoTest() {
        a = new Alumno(11, "Celia Rodriguez", 3);
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
     * Test del método getNumMatricula, de la clase Alumno
     */
    @Test
    public void testGetNumMatricula() {
        int numMatr = a.getNumMatricula();
        assertEquals(11, numMatr);
    }

    /**
     * Test del método setNumMatricula, de la clase Alumno
     */
    @Test
    public void testSetNumMatricula() {
        int numMatr = 12;
        a.setNumMatricula(numMatr);
        assertEquals(numMatr, a.getNumMatricula());
    }

    /**
     * Test del método getNombre, de la clase Alumno
     */
    @Test
    public void testGetNombre() {
        String nombre = a.getNombre();
        assertEquals("Celia Rodriguez", nombre);
    }

    /**
     * Test del método setNombre, de la clase Alumno
     */
    @Test
    public void testSetNombre() {
        String nombre = "Cecilia Rodriguez";
        a.setNombre(nombre);
        assertEquals(nombre, a.getNombre());
    }

    /**
     * Test del método getGrupo, de la clase Alumno
     */
    @Test
    public void testGetGrupo() {
        int grupo = a.getGrupo();
        assertEquals(3, grupo);
    }

    /**
     * Test del método setGrupo, de la clase Alumno
     */
    @Test
    public void testSetGrupo() {
        int grupo = 4;
        a.setGrupo(grupo);
        assertEquals(grupo, a.getGrupo());
    }
    
}
