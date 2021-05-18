package dao;

import dto.ControlEscrito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import conexion.ConexionDB;

/**
 * Clase DAO de Controles escritos
 * @author Lourdes Navarro Tocón
 */
public class DaoControlesEscritos {
    
    /**
     * Propiedades y métodos singleton
     */
    
    private Connection con = null;

    private static DaoControlesEscritos instance = null;
    
    public DaoControlesEscritos() throws SQLException {
	con = ConexionDB.getConnection();
    }
    
    public static DaoControlesEscritos getInstance() throws SQLException {
	if (instance == null)
            instance = new DaoControlesEscritos();
        return instance;
    }
    
    /**
     * Método para encontrar un control escrito por la clave primaria (número de control)
     * @param codigo Número de control
     * @return Control escrito
     * @throws SQLException 
     */
    public ControlEscrito findByPK(int codigo) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM control_escrito WHERE codigo = ?");
        ps.setInt(1, codigo);
		
        ResultSet rs = ps.executeQuery();

	ControlEscrito result = null;

	if (rs.next()) {
            result = new ControlEscrito(rs.getInt("codigo"), rs.getInt("num_preguntas"), rs.getDate("fecha"));
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para listar todos los controles escritos realizados por un alumno
     * @param numMatr Número de matrícula del alumno
     * @return Colección de controles escritos
     * @throws SQLException 
     */
    public Map<Integer, ControlEscrito> findByAlumno(int numMatr) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT codigo, num_preguntas, fecha, nota FROM control_escrito JOIN realiza_control ON control_escrito.codigo=realiza_control.cod_control WHERE matric_alumno = ?");
        ps.setInt(1, numMatr);
        ResultSet rs = ps.executeQuery();
        Map<Integer, ControlEscrito> result = new HashMap<>();
        
        while(rs.next()){
            ControlEscrito c = new ControlEscrito(rs.getInt("codigo"), rs.getInt("num_preguntas"), rs.getDate("fecha"));
            c.getNotasControl().put(numMatr, rs.getDouble("nota"));
            result.put(rs.getInt("codigo"), c);
        }
        rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para listar todos los controles escritos de la base de datos
     * @return Colección de todos los controles
     * @throws SQLException 
     */
    public Map<Integer, ControlEscrito> findAll() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT codigo, num_preguntas, fecha, matric_alumno, nota FROM control_escrito JOIN realiza_control ON control_escrito.codigo=realiza_control.cod_control");
	ResultSet rs = ps.executeQuery();
        Map<Integer, ControlEscrito> result = new HashMap<>();

	while (rs.next()) {
            if(result.containsKey(rs.getInt("codigo"))){
                result.get(rs.getInt("codigo")).getNotasControl().put(rs.getInt("matric_alumno"), rs.getDouble("nota"));
            } else {
                ControlEscrito c = new ControlEscrito(rs.getInt("codigo"), rs.getInt("num_preguntas"), rs.getDate("fecha"));
                c.getNotasControl().put(rs.getInt("matric_alumno"), rs.getDouble("nota"));
                result.put(rs.getInt("codigo"), c);
            }
	}
        
        rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para insertar un control escrito en la base de datos
     * @param c Control escrito a insertar
     * @throws SQLException 
     */
    public void insertControl(ControlEscrito c) throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO control_escrito (codigo, num_preguntas, fecha) VALUES (?, ?, ?)");
            ps.setInt(1, c.getNumControl());
            ps.setInt(2, c.getNumPreguntas());
            ps.setDate(3, c.getFecha());

            ps.executeUpdate();

            ps.close();
    }
    
    /**
     * Método para insertar los alumnos que han realizado cada control con sus respectivas notas en la base de datos
     * @param c Control escrito a insertar
     * @param numMatr Número de matrícula del alumno que ha realizado el control
     * @throws SQLException 
     */
    public void insertNota(ControlEscrito c, int numMatr) throws SQLException {
        PreparedStatement ps1 = con.prepareStatement("INSERT INTO realiza_control (matric_alumno, cod_control, nota) VALUES (?, ?, ?)");
        ps1.setInt(1, numMatr);
        ps1.setInt(2, c.getNumControl());
        ps1.setDouble(3, c.getNotasControl().get(numMatr));
            
        ps1.executeUpdate();
        
        ps1.close();
    }
    
    /**
     * Método para eliminar un control escrito de la base de datos
     * @param c Control escrito a eliminar
     * @throws SQLException 
     */
    public void delete(ControlEscrito c) throws SQLException {
	deleteControl(c.getNumControl());
    }
    
    /**
     * Método para eliminar un control escrito de la base de datos
     * @param codigo Número de control
     * @throws SQLException 
     */
    public void deleteControl(int codigo) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM control_escrito WHERE codigo = ?");
        ps.setInt(1, codigo);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para eliminar una nota de un alumno en un control escrito de la base de datos
     * @param codigo Número de control
     * @param numMatricula Número de matrícula del alumno
     * @throws SQLException 
     */
    public void deleteNota(int codigo, int numMatricula) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM realiza_control WHERE matric_alumno = ? AND cod_control = ?");
        ps.setInt(1, numMatricula);
        ps.setInt(2, codigo);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para modificar un control escrito de la base de datos
     * @param c Control escrito a modificar
     * @throws SQLException 
     */
    public void updateControl(ControlEscrito c) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE control_escrito SET codigo = ?, num_preguntas = ?, fecha = ? WHERE codigo = ?");
	ps.setInt(1, c.getNumControl());
	ps.setInt(2, c.getNumPreguntas());
	ps.setDate(3, c.getFecha());
        ps.setInt(4, c.getNumControl());
		
	ps.executeUpdate();
		
	ps.close();
    }
    
    /**
     * Método para modificar una nota de un alumno en un control escrito de la base de datos
     * @param c Control escrito del cual queremos modificar una nota
     * @param numMatr Número de matrícula del alumno
     * @throws SQLException 
     */
    public void updateNota(ControlEscrito c, int numMatr) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE realiza_control SET nota = ? WHERE matric_alumno = ?");
        ps.setDouble(1, c.getNotasControl().get(numMatr));
        ps.setInt(2, numMatr);
            
        ps.executeUpdate();
        
        ps.close(); 
    }
}
