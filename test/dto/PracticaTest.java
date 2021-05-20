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
 * Clase Test de la clase Practica
 * @author Lourdes Navarro Tocón
 */
public class PracticaTest {
    
    private Practica pa;
    
    public PracticaTest() {
        pa = new Practica(6, "Gravedad", Dificultad.valueOf("Baja"));
        pa.addNota(Date.valueOf("2021-05-20"), 3, 6.0);
        pa.addNota(Date.valueOf("2021-05-20"), 4, 10.0);
        pa.addNota(Date.valueOf("2021-05-21"), 5, 4.0);
        pa.getProfDiseno().put("21111111A", Date.valueOf("2021-05-17"));
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
     * Test del método getCodPractica, de la clase Practica
     */
    @Test
    public void testGetCodPractica() {
        int codPract = pa.getCodPractica();
        assertEquals(6, codPract);
    }

    /**
     * Test del método setCodPractica, de la clase Practica
     */
    @Test
    public void testSetCodPractica() {
        int codPract = 7;
        pa.setCodPractica(codPract);
        assertEquals(codPract, pa.getCodPractica());
    }

    /**
     * Test del método getTitulo, de la clase Practica
     */
    @Test
    public void testGetTitulo() {
        String titulo = pa.getTitulo();
        assertEquals("Gravedad", titulo);
    }

    /**
     * Test del método setTitulo, de la clase Practica
     */
    @Test
    public void testSetTitulo() {
        String titulo = "Fuerzas";
        pa.setTitulo(titulo);
        assertEquals(titulo, pa.getTitulo());
    }

    /**
     * Test del método getDificultad, de la clase Practica
     */
    @Test
    public void testGetDificultad() {
        Dificultad dificultad = pa.getDificultad();
        assertEquals(Dificultad.valueOf("Baja"), dificultad);
    }

    /**
     * Test del método setDificultad, de la clase Practica
     */
    @Test
    public void testSetDificultad() {
        Dificultad dificultad = Dificultad.valueOf("Media");
        pa.setDificultad(dificultad);
        assertEquals(dificultad, pa.getDificultad());
    }

    /**
     * Test del método getNotasPractica, de la clase Practica
     */
    @Test
    public void testGetNotasPractica() {
        Map<Date, Map<Integer, Double>> notas = pa.getNotasPractica();
        assertEquals(2, notas.size());
        assertEquals(6.0, notas.get(Date.valueOf("2021-05-20")).get(3), 0);
        assertEquals(10.0, notas.get(Date.valueOf("2021-05-20")).get(4), 0);
        assertEquals(4.0, notas.get(Date.valueOf("2021-05-21")).get(5), 0);
    }

    /**
     * Test del método setNotasPractica, de la clase Practica
     */
    @Test
    public void testSetNotasPractica() {
        Map<Integer, Double> notas = new HashMap<>();
        notas.put(3, 5.0);
        notas.put(4, 9.0);
        Map<Date, Map<Integer,Double>> realiz = new HashMap<>();
        realiz.put(Date.valueOf("2021-05-22"), notas);
        pa.setNotasPractica(realiz);
        assertEquals(realiz, pa.getNotasPractica());
    }

    /**
     * Test del método getProfDiseno, de la clase Practica
     */
    @Test
    public void testGetProfDiseno() {
        Map<String, Date> diseno = pa.getProfDiseno();
        assertEquals(1, diseno.size());
        assertEquals(Date.valueOf("2021-05-17"), diseno.get("21111111A"));
    }

    /**
     * Test del método setProfDiseno, de la clase Practica
     */
    @Test
    public void testSetProfDiseno() {
        Map<String, Date> diseno = new HashMap<>();
        diseno.put("21111111A", Date.valueOf("2021-05-16"));
        diseno.put("22222222B", Date.valueOf("2021-05-16"));
        pa.setProfDiseno(diseno);
        assertEquals(diseno, pa.getProfDiseno());
    }

    /**
     * Test del método addNota, de la clase Practica
     */
    @Test
    public void testAddNota() {
        Map<Integer, Double> notas1 = new HashMap<>();
        notas1.put(3, 5.0);
        notas1.put(4, 10.0);
        Map<Integer, Double> notas2 = new HashMap<>();
        notas2.put(5, 4.0);
        Map<Date, Map<Integer,Double>> realiz = new HashMap<>();
        realiz.put(Date.valueOf("2021-05-20"), notas1);
        realiz.put(Date.valueOf("2021-05-21"), notas2);
        
        pa.addNota(Date.valueOf("2021-05-20"), 3, 5.0);
        
        assertEquals(realiz, pa.getNotasPractica());
        
    }
    
}
