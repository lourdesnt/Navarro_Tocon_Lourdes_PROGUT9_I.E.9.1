package dao;

import dto.ControlEscrito;
import java.sql.Date;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase DaoControlesEscritos
 * @author Lourdes Navarro Tocón
 */
public class DaoControlesEscritosTest {
    
    private DaoControlesEscritos daoContr;
    
    public DaoControlesEscritosTest() {
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
     * Test del método findByPK, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByPK() throws Exception {
        ControlEscrito c = daoContr.findByPK(1);
        
        assertEquals(1, c.getNumControl());
        assertEquals(10, c.getNumPreguntas());
        assertEquals(Date.valueOf("2021-04-26"), c.getFecha());
    }

    /**
     * Test del método findByAlumno, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByAlumno() throws Exception {
        Map<Integer, ControlEscrito> c = daoContr.findByAlumno(1);
        
        assertEquals(true, c.containsKey(1));
        assertEquals(true, c.containsValue(c.get(1)));
        assertEquals(10, c.get(1).getNotasControl().get(1), 0);
    }

    /**
     * Test del método findAll, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAll() throws Exception {
        Map<Integer, ControlEscrito> result = daoContr.findAll();
        assertEquals(10, result.size());
    }

    /**
     * Test del método insertControl, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertControl() throws Exception {
        ControlEscrito c = new ControlEscrito(6, 15, Date.valueOf("2021-05-20"));
        daoContr.insertControl(c);
        
        int numControl = c.getNumControl();
        assertNotNull(numControl);
        
        ControlEscrito newC = daoContr.findByPK(6);
        assertEquals(6, newC.getNumControl());
        assertEquals(15, newC.getNumPreguntas());
        assertEquals(Date.valueOf("2021-05-20"), newC.getFecha());
    }

    /**
     * Test del método insertNota, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertNota() throws Exception {
        ControlEscrito c = daoContr.findByPK(1);
        int numMatr = 2;
        double nota = 8;
        c.getNotasControl().put(numMatr, nota);
        daoContr.insertNota(c, numMatr);
        
        assertEquals(11, daoContr.findAll().size());
        assertEquals(1, c.getNumControl());
        assertEquals(10, c.getNumPreguntas());
        assertEquals(Date.valueOf("2021-04-26"), c.getFecha());
        assertEquals(true, c.getNotasControl().containsKey(numMatr));
        assertEquals(8, c.getNotasControl().get(numMatr), 0);
    }

    /**
     * Test del método delete,de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete() throws Exception {
        ControlEscrito c = new ControlEscrito(6, 15, Date.valueOf("2021-05-20"));
        daoContr.insertControl(c);
        
        daoContr.delete(c);
        assertEquals(false, daoContr.findAll().containsKey(6));
    }

    /**
     * Test del método deleteControl, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteControl() throws Exception {
        ControlEscrito c = new ControlEscrito(6, 15, Date.valueOf("2021-05-20"));
        daoContr.insertControl(c);
        
        daoContr.deleteControl(6);
        assertEquals(false, daoContr.findAll().containsKey(6));
    }

    /**
     * Test of deleteNota method, of class DaoControlesEscritos.
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteNota() throws Exception {
        ControlEscrito c = daoContr.findByPK(2);
        int numMatr = 3;
        assertEquals(10, daoContr.findAll().size());
        
        daoContr.deleteNota(c.getNumControl(), numMatr);
        assertEquals(9, daoContr.findAll().size());
        assertEquals(false, c.getNotasControl().containsKey(3));
    }

    /**
     * Test del método updateControl, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateControl() throws Exception {
        ControlEscrito c = daoContr.findByPK(4);
        assertEquals(4, c.getNumControl());
        assertEquals(20, c.getNumPreguntas());
        assertEquals(Date.valueOf("2021-06-01"), c.getFecha());
        
        Date fecha = Date.valueOf("2021-06-02");
        ControlEscrito newC = new ControlEscrito(4, 16, fecha);
        daoContr.updateControl(newC);
        assertEquals(4, c.getNumControl());
        assertEquals(16, c.getNumPreguntas());
        assertEquals(fecha, c.getFecha());
    }

    /**
     * Test del método updateNota, de la clase DaoControlesEscritos
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateNota() throws Exception {
        ControlEscrito c = daoContr.findByPK(1);
        assertEquals(10, c.getNotasControl().get(1), 0);
        
        double nota = 3;
        c.getNotasControl().put(1, nota);
        daoContr.updateNota(c, 1);
        assertEquals(true, c.getNotasControl().containsKey(1));
        assertEquals(nota, c.getNotasControl().get(1), 0);
    }
    
}
