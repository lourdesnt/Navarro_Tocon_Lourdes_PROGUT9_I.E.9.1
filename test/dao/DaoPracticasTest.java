package dao;

import dto.Dificultad;
import dto.Practica;
import java.sql.Date;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase Test de la clase DaoPracticas
 * @author Lourdes Navarro Tocón
 */
public class DaoPracticasTest {
    
    private DaoPracticas daoPract;
    
    public DaoPracticasTest() {
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
     * Test del método findByAlumno, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByAlumno() throws Exception {
        Map<Integer, Practica> pa = daoPract.findByAlumno(1);
        
        assertEquals(true, pa.containsKey(5));
        assertEquals(true, pa.containsValue(pa.get(5)));
        assertEquals(7, pa.get(5).getNotasPractica().get(Date.valueOf("2021-01-30")).get(1), 0);
    }

    /**
     * Test del método findByPK, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testFindByPK() throws Exception {
        Practica pa = daoPract.findByPK(1);
        
        assertEquals(1, pa.getCodPractica());
        assertEquals("Pendulo", pa.getTitulo());
        assertEquals(Dificultad.valueOf("Baja"), pa.getDificultad());
    }
    
    /**
     * Test del método findAllNotas, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllNotas() throws Exception {
        Map<Integer, Practica> result = daoPract.findAllNotas();
        assertEquals(10, result.size());
    }

    /**
     * Test del método findAllDiseno, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testFindAllDiseno() throws Exception {
        Map<Integer, Practica> result = daoPract.findAllNotas();
        assertEquals(9, result.size());
    }

    /**
     * Test del método insertPractica, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertPractica() throws Exception {
        Practica pa = new Practica(6, "Gravedad", Dificultad.valueOf("Baja"));
        daoPract.insertPractica(pa);
        
        int codPractica = pa.getCodPractica();
        assertNotNull(codPractica);
        
        Practica newPa = daoPract.findByPK(6);
        assertEquals(6, newPa.getCodPractica());
        assertEquals("Gravedad", newPa.getTitulo());
        assertEquals(Dificultad.valueOf("Baja"), newPa.getDificultad());
    }

    /**
     * Test del método insertNota, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertNota() throws Exception {
        Practica pa = daoPract.findByPK(2);
        int numMatr = 8;
        double nota = 7;
        Date fecha = Date.valueOf("2021-02-16");
        pa.addNota(fecha, numMatr, nota);
        daoPract.insertNota(pa, fecha, numMatr);
        
        assertEquals(11, daoPract.findAllNotas().size());
        assertEquals(2, pa.getCodPractica());
        assertEquals("Densidad", pa.getTitulo());
        assertEquals(Dificultad.valueOf("Alta"), pa.getDificultad());
        assertEquals(true, pa.getNotasPractica().containsKey(fecha));
        assertEquals(true, pa.getNotasPractica().get(fecha).containsKey(numMatr));
        assertEquals(7, pa.getNotasPractica().get(fecha).get(numMatr), 0);
    }

    /**
     * Test del método insertDiseno, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testInsertDiseno() throws Exception {
        Practica pa = daoPract.findByPK(1);
        String dni = "22222222B";
        Date fecha = Date.valueOf("2021-04-14");
        pa.getProfDiseno().put(dni, fecha);
        daoPract.insertDiseno(pa, dni);
        
        assertEquals(10, daoPract.findAllDiseno().size());
        assertEquals(1, pa.getCodPractica());
        assertEquals("Pendulo", pa.getTitulo());
        assertEquals(Dificultad.valueOf("Baja"), pa.getDificultad());
        assertEquals(true, pa.getProfDiseno().containsKey("22222222B"));
        assertEquals(Date.valueOf("2021-04-14"), pa.getProfDiseno().get(dni));
    }

    /**
     * Test del método delete, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testDelete() throws Exception {
        Practica pa = new Practica(6, "Gravedad", Dificultad.valueOf("Baja"));
        daoPract.insertPractica(pa);
        
        daoPract.delete(pa);
        assertEquals(false, daoPract.findAllNotas().containsKey(6));
    }

    /**
     * Test del método deletePractica, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testDeletePractica() throws Exception {
        Practica pa = new Practica(6, "Gravedad", Dificultad.valueOf("Baja"));
        daoPract.insertPractica(pa);
        
        daoPract.deletePractica(6);
        assertEquals(false, daoPract.findAllNotas().containsKey(6));
    }

    /**
     * Test del método deleteNota, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteNota() throws Exception {
        Practica pa = daoPract.findByPK(5);
        int numMatr = 2;
        Date fecha = Date.valueOf("2021-01-31");
        assertEquals(10, daoPract.findAllNotas().size());
        
        daoPract.deleteNota(pa.getCodPractica(), numMatr);
        assertEquals(9, daoPract.findAllNotas().size());
        assertEquals(false, pa.getNotasPractica().get(fecha).containsKey(numMatr));
    }

    /**
     * Test del método deleteDiseno, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testDeleteDiseno() throws Exception {
        Practica pa = daoPract.findByPK(5);
        String dni = "23333333C";
        assertEquals(9, daoPract.findAllDiseno().size());
        
        daoPract.deleteDiseno(pa.getCodPractica(), dni);
        assertEquals(8, daoPract.findAllDiseno().size());
        assertEquals(false, pa.getProfDiseno().containsKey(dni));
    }

    /**
     * Test del método updatePractica, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdatePractica() throws Exception {
        Practica pa = daoPract.findByPK(3);
        assertEquals(3, pa.getCodPractica());
        assertEquals("Magnetismo", pa.getTitulo());
        assertEquals(Dificultad.valueOf("Media"), pa.getDificultad());
        
        Practica newPa = new Practica(3, "Electricidad", Dificultad.valueOf("Media"));
        daoPract.updatePractica(newPa);
        assertEquals(4, pa.getCodPractica());
        assertEquals("Electricidad", pa.getTitulo());
        assertEquals(Dificultad.valueOf("Media"), pa.getDificultad());
    }

    /**
     * Test del método updateNota, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateNota() throws Exception {
        Practica pa = daoPract.findByPK(2);
        assertEquals(9, pa.getNotasPractica().get(Date.valueOf("2021-02-15")).get(5), 0);
        
        double nota = 9.5;
        pa.addNota(Date.valueOf("2021-02-15"), 5, nota);
        daoPract.updateNota(pa, Date.valueOf("2021-02-15"), 5);
        assertEquals(true, pa.getNotasPractica().get(Date.valueOf("2021-02-15")).containsKey(5));
        assertEquals(9.5, pa.getNotasPractica().get(Date.valueOf("2021-02-15")).get(5), 0);
    }

    /**
     * Test del método updateDiseno, de la clase DaoPracticas
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdateDiseno() throws Exception {
        Practica pa = daoPract.findByPK(4);
        assertEquals(Date.valueOf("2021-03-15"), pa.getProfDiseno().get("22222222B"));
        
        Date fecha = Date.valueOf("2021-03-16");
        pa.getProfDiseno().put("22222222B", fecha);
        daoPract.updateDiseno(pa, "22222222B");
        assertEquals(true, pa.getProfDiseno().containsKey("22222222B"));
        assertEquals(fecha, pa.getProfDiseno().get("22222222B"));
    }
    
}
