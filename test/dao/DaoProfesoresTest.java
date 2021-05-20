package dao;

import dto.Profesor;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase DaoProfesores
 * @author Lourdes Navarro Tocón
 */
public class DaoProfesoresTest {
    
    private DaoProfesores daoProf;
    
    public DaoProfesoresTest() {
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
     * Test del método findAll, de la clase DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll() throws Exception {
        Profesor p = new Profesor("26666666F", "Cristian Jimenez");
        daoProf.insert(p);
        
        List<Profesor> result = daoProf.findAll();
        assertEquals(6, result.size());
        
        Profesor newP = result.get(5);
        assertEquals("26666666F", newP.getDni());
        assertEquals("Cristian Jimenez", newP.getNombre());
    }

    /**
     * Test del método findByPK, de la clase DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByPK() throws Exception {
        Profesor p = daoProf.findByPK("21111111A");
        
        assertEquals("21111111A", p.getDni());
        assertEquals("Manuel Fernandez", p.getNombre());
    }

    /**
     * Test del método findByName, of class DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByName() throws Exception {
        Profesor p = daoProf.findByName("Teresa Montes");
        
        assertEquals("23333333C", p.getDni());
        assertEquals("Teresa Montes", p.getNombre());
    }

    /**
     * Test del método insert, de la clase DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testInsert() throws Exception {
        Profesor p = new Profesor("26666666F", "Cristian Jimenez");
        daoProf.insert(p);
        
        String dni = p.getDni();
        assertNotNull(dni);
        
        assertEquals(6, daoProf.findAll().size());
        
        Profesor newP = daoProf.findByPK("26666666F");
        assertEquals("26666666F", newP.getDni());
        assertEquals("Cristian Jimenez", newP.getNombre());
    }

    /**
     * Test del método delete, de la clase DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete_Profesor() throws Exception {
        Profesor p = new Profesor("26666666F", "Cristian Jimenez");
        daoProf.insert(p);
        
        assertEquals(6, daoProf.findAll().size());
        
        daoProf.delete(p);
        assertEquals(5, daoProf.findAll().size());
    }

    /**
     * Test del método delete, de la clase DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete_String() throws Exception {
        Profesor p = new Profesor("26666666F", "Cristian Jimenez");
        daoProf.insert(p);
        
        assertEquals(6, daoProf.findAll().size());
        
        daoProf.delete("26666666F");
        assertEquals(5, daoProf.findAll().size());
    }

    /**
     * Test del método update, de la clase DaoProfesores
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdate() throws Exception {
        Profesor p = daoProf.findByPK("24444444D");
        assertEquals("24444444D", p.getDni());
        assertEquals("Carolina Segovia", p.getNombre());
        
        Profesor newP = new Profesor("24444444D", "Carolina Santos");
        daoProf.update(newP);
        assertEquals("24444444D", p.getDni());
        assertEquals("Carolina Santos", p.getNombre());
    }
}
