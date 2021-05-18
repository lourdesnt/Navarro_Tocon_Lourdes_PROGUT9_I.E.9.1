package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import conexion.ConexionDB;
import dto.Profesor;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO de Profesores
 * @author Lourdes Navarro Tocón
 */
public class DaoProfesores {
    
    /**
     * Propiedades y métodos singleton
     */
    
    private Connection con = null;

    private static DaoProfesores instance = null;
    
    public DaoProfesores() throws SQLException {
	con = ConexionDB.getConnection();
    }
    
    public static DaoProfesores getInstance() throws SQLException {
	if (instance == null)
            instance = new DaoProfesores();
        return instance;
    }
    
    /**
     * Método para listar todos los profesores de la base de datos
     * @return Lista de todos los profesores
     * @throws SQLException 
     */
    public List<Profesor> findAll() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM profesor");
	ResultSet rs = ps.executeQuery();

	List<Profesor> result = null;

	while (rs.next()) {
            if (result == null)
		result = new ArrayList<>();

            result.add(new Profesor(rs.getString("dni"), rs.getString("nombre")));
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para encontrar un profesor en la base de datos mediante la clave primaria (DNI)
     * @param dni DNI del profesor
     * @return Profesor
     * @throws SQLException 
     */
    public Profesor findByPK(String dni) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM profesor WHERE dni = ?");
        ps.setString(1, dni);
		
        ResultSet rs = ps.executeQuery();

	Profesor result = null;

	if (rs.next()) {
            result = new Profesor(rs.getString("dni"), rs.getString("nombre"));
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para encontrar un profesor en la base de datos mediante su nombre
     * @param nombre Nombre del profesor
     * @return Profesor
     * @throws SQLException 
     */
    public Profesor findByName(String nombre) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM profesor WHERE nombre = ?");
        ps.setString(1, nombre);
		
        ResultSet rs = ps.executeQuery();

	Profesor result = null;

	if (rs.next()) {
            result = new Profesor(rs.getString("dni"), rs.getString("nombre"));
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para insertar un profesor en la base de datos
     * @param p Profesor a insertar
     * @throws SQLException 
     */
    public void insert(Profesor p) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO profesor (dni, nombre) VALUES (?, ?)");
	ps.setString(1, p.getDni());
	ps.setString(2, p.getNombre());

	ps.executeUpdate();

	ps.close();
    }
    
    /**
     * Método para eliminar un profesor de la base de datos
     * @param p Profesor a eliminar
     * @throws SQLException 
     */
    public void delete(Profesor p) throws SQLException {
	delete(p.getDni());
    }
	
    /**
     * Método para eliminar un profesor de la base de datos
     * @param dni DNI del profesor
     * @throws SQLException 
     */
    public void delete(String dni) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM profesor WHERE dni = ?");
        ps.setString(1, dni);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para modificar un profesor de la base de datos
     * @param p Profesor a modificar
     * @throws SQLException 
     */
    public void update(Profesor p) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE profesor SET dni = ?, nombre = ? WHERE dni = ?");
	ps.setString(1, p.getDni());
	ps.setString(2, p.getNombre());
	ps.setString(3, p.getDni());
		
	ps.executeUpdate();
		
	ps.close();
    }
}
