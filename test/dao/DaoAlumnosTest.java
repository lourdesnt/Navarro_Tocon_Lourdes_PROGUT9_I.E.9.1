package dao;

import dto.Alumno;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase DaoAlumnos
 * @author Lourdes Navarro Tocón
 */
public class DaoAlumnosTest {
    
    private DaoAlumnos daoAlum;
    
    public DaoAlumnosTest() {
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
     * Test del método findAll, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll() throws Exception {
        Alumno a = new Alumno(11, "Celia Rodriguez", 3);
        daoAlum.insert(a);
        
        List<Alumno> result = daoAlum.findAll();
        assertEquals(11, result.size());
        
        Alumno newA = result.get(10);
        assertEquals(11, newA.getNumMatricula());
        assertEquals("Celia Rodriguez", newA.getNombre());
        assertEquals(3, newA.getGrupo());
    }

    /**
     * Test del método findByPK, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByPK() throws Exception {
        Alumno a = daoAlum.findByPK(1);
        
        assertEquals(1, a.getNumMatricula());
        assertEquals("Lourdes Navarro", a.getNombre());
        assertEquals(1, a.getGrupo());
    }

    /**
     * Test del método findByName, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByName() throws Exception {
        Alumno a = daoAlum.findByName("Eduardo Torres");
        
        assertEquals(7, a.getGrupo());
        assertEquals("Eduardo Torres", a.getNombre());
        assertEquals(1, a.getGrupo());
    }

    /**
     * Test del método insert, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testInsert() throws Exception {
        Alumno a = new Alumno(11, "Celia Rodriguez", 3);
        daoAlum.insert(a);
        
        int numMatr = a.getNumMatricula();
        assertNotNull(numMatr);
        
        assertEquals(11, daoAlum.findAll().size());
        
        Alumno newA = daoAlum.findByPK(11);
        assertEquals(11, newA.getNumMatricula());
        assertEquals("Celia Rodriguez", newA.getNombre());
        assertEquals(3, newA.getGrupo());
    }

    /**
     * Test del método delete, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete_Alumno() throws Exception {
        Alumno a = new Alumno(11, "Celia Rodriguez", 3);
        daoAlum.insert(a);
        
        assertEquals(11, daoAlum.findAll().size());
        
        daoAlum.delete(a);
        assertEquals(10, daoAlum.findAll().size());
        
        Alumno newA = daoAlum.findByPK(daoAlum.findAll().size());
        assertEquals(10, newA.getNumMatricula());
        assertEquals("Sara Romero", newA.getNombre());
        assertEquals(7, newA.getGrupo());
    }

    /**
     * Test del método delete, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete_int() throws Exception {
        Alumno a = new Alumno(11, "Celia Rodriguez", 3);
        daoAlum.insert(a);
        
        assertEquals(11, daoAlum.findAll().size());
        
        daoAlum.delete(11);
        assertEquals(10, daoAlum.findAll().size());
        
        Alumno newA = daoAlum.findByPK(daoAlum.findAll().size());
        assertEquals(10, newA.getNumMatricula());
        assertEquals("Sara Romero", newA.getNombre());
        assertEquals(7, newA.getGrupo());
    }

    /**
     * Test del método update, de la clase DaoAlumnos
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdate() throws Exception {
        Alumno a = daoAlum.findByPK(9);
        assertEquals(9, a.getNumMatricula());
        assertEquals("Antonio Torres", a.getNombre());
        assertEquals(6, a.getGrupo());
        
        Alumno newA = new Alumno(9, "Antonio Valle", 7);
        daoAlum.update(newA);
        assertEquals(9, a.getNumMatricula());
        assertEquals("Antonio Valle", a.getNombre());
        assertEquals(7, a.getGrupo());
    }
    
}
