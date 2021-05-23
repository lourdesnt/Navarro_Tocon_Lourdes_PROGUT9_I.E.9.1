package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexion.ConexionDB;
import dto.Alumno;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO de Alumnos
 * @author Lourdes Navarro Tocón
 */
public class DaoAlumnos {
     
    /**
     * Propiedades y métodos singleton
     */
    
    private Connection con = null;

    private static DaoAlumnos instance = null;
    
    public DaoAlumnos() throws SQLException {
	con = ConexionDB.getConnection();
    }
    
    public static DaoAlumnos getInstance() throws SQLException {
	if (instance == null){
            instance = new DaoAlumnos();
        }
	return instance;
    }
    
    /**
     * Método para listar todos los alumnos de la base de datos
     * @return Lista de todos los alumnos
     * @throws SQLException 
     */
    public List<Alumno> findAll() throws SQLException {
	PreparedStatement ps = con.prepareStatement("SELECT * FROM alumno");
	ResultSet rs = ps.executeQuery();

	List<Alumno> result = null;

	while (rs.next()) {
            if (result == null)
		result = new ArrayList<>();

            result.add(new Alumno(rs.getInt("num_matricula"), rs.getString("nombre"), rs.getInt("grupo")));
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para encontrar un alumno en la base de datos mediante la clave primaria (número de matrícula)
     * @param numMatricula Número de matrícula del alumno
     * @return Alumno
     * @throws SQLException 
     */
    public Alumno findByPK(int numMatricula) throws SQLException {
//      PreparedStatement ps = con.prepareStatement("SELECT * FROM alumno WHERE num_matricula = ?");
//      ps.setInt(1, numMatricula);
//		
//      ResultSet rs = ps.executeQuery();
//
//	Alumno result = null;
//
//	if (rs.next()) {
//            result = new Alumno(rs.getInt("num_matricula"), rs.getString("nombre"), rs.getInt("grupo"));
//	}
//
//	rs.close();
//	ps.close();
//
//	return result;

        CallableStatement cs = con.prepareCall("{call obtenerDatosAlumno(?)}");  
        cs.setInt(1, numMatricula);  
        
        cs.execute();    
        ResultSet rs = cs.getResultSet(); 
        
        Alumno result = null;

	if (rs.next()) {
            result = new Alumno(rs.getInt("num_matricula"), rs.getString("nombre"), rs.getInt("grupo"));
	}

	rs.close();
	cs.close();
        
        return result;
    }
    
    /**
     * Método para encontrar un alumno en la base de datos mediante su nombre
     * @param nombre Nombre del alumno
     * @return Alumno
     * @throws SQLException 
     */
    public Alumno findByName(String nombre) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM alumno WHERE nombre = ?");
        ps.setString(1, nombre);
		
        ResultSet rs = ps.executeQuery();

	Alumno result = null;

	if (rs.next()) {
            result = new Alumno(rs.getInt("num_matricula"), rs.getString("nombre"), rs.getInt("grupo"));
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para insertar un alumno en la base de datos
     * @param a Alumno a insentar
     * @throws SQLException 
     */
    public void insert(Alumno a) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO alumno (num_matricula, nombre, grupo) VALUES (?, ?, ?)");
        ps.setInt(1, a.getNumMatricula());
	ps.setString(2, a.getNombre());
	ps.setInt(3, a.getGrupo());

	ps.executeUpdate();

	ps.close();
    }
    
    /**
     * Método para eliminar un alumno de la base de datos
     * @param a Alumno a eliminar
     * @throws SQLException 
     */
    public void delete(Alumno a) throws SQLException {
	delete(a.getNumMatricula());
    }
	
    /**
     * Método para eliminar un alumno de la base de datos
     * @param numMatricula Número de matrícula del alumno
     * @throws SQLException 
     */
    public void delete(int numMatricula) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM alumno WHERE num_matricula = ?");
	ps.setInt(1, numMatricula);

	ps.executeUpdate();

	ps.close();
    }
    
    /**
     * Método para modificar un alumno de la base de datos
     * @param a Alumno a modificar
     * @throws SQLException 
     */
    public void update(Alumno a) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE alumno SET num_matricula = ?, nombre = ?, grupo = ? WHERE num_matricula = ?");
        ps.setInt(1, a.getNumMatricula());
        ps.setString(2, a.getNombre());
	ps.setInt(3, a.getGrupo());
        ps.setInt(4, a.getNumMatricula());
		
        ps.executeUpdate();
		
        ps.close();
    }
}
