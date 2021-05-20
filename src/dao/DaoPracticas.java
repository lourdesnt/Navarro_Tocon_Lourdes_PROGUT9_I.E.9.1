package dao;

import dto.Dificultad;
import dto.Practica;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import conexion.ConexionDB;

/**
 * Clase DAO de Prácticas
 * @author Lourdes Navarro Tocón
 */
public class DaoPracticas {
    
    /**
     * Propiedades y métodos singleton
     */
    
    private Connection con = null;

    private static DaoPracticas instance = null;
    
    public DaoPracticas() throws SQLException {
	con = ConexionDB.getConnection();
    }
    
    public static DaoPracticas getInstance() throws SQLException {
	if (instance == null)
            instance = new DaoPracticas();
        return instance;
    }
    
    /**
     * Método para listar todas las prácticas realizadas por un alumno
     * @param numMatr Número de matrícula del alumno
     * @return Colección de prácticas
     * @throws SQLException 
     */
    public Map<Integer, Practica> findByAlumno(int numMatr) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT codigo, titulo, dificultad, fecha, nota FROM practica JOIN realiza_practica ON practica.codigo=realiza_practica.cod_pract WHERE matric_alumno = ?");
        ps.setInt(1, numMatr);
        ResultSet rs = ps.executeQuery();
        Map<Integer, Practica> result = new HashMap<>();
        
        while(rs.next()){
            Dificultad dificultad = Dificultad.valueOf(rs.getString("dificultad"));
            Practica pa = new Practica(rs.getInt("codigo"), rs.getString("titulo"), dificultad);
            pa.addNota(rs.getDate("fecha"), numMatr, rs.getDouble("nota"));
            result.put(rs.getInt("codigo"), pa);
        }
        rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para encontrar una práctica por la clave primaria (código de práctica)
     * @param codigo Código de práctica
     * @return Práctica
     * @throws SQLException 
     */
    public Practica findByPK(int codigo) throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT * FROM practica WHERE codigo = ?");
        ps.setInt(1, codigo);
		
        ResultSet rs = ps.executeQuery();

	Practica result = null;

	if (rs.next()) {
            Dificultad dificultad = Dificultad.valueOf(rs.getString("dificultad"));
            result = new Practica(rs.getInt("codigo"), rs.getString("titulo"), dificultad);
	}

	rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para listar todas las prácticas de la base de datos junto con las notas de los alumnos
     * @return Colección de todas las prácticas
     * @throws SQLException 
     */
    public Map<Integer, Practica> findAllNotas() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT codigo, titulo, dificultad, matric_alumno, fecha, nota FROM practica JOIN realiza_practica ON practica.codigo=realiza_practica.cod_pract");
	ResultSet rs = ps.executeQuery();
        Map<Integer, Practica> result = new HashMap<>();
        
	while (rs.next()) {
            if(result.containsKey(rs.getInt("codigo"))){
                if(result.get(rs.getInt("codigo")).getNotasPractica().containsKey(rs.getDate("fecha"))){
                    result.get(rs.getInt("codigo")).getNotasPractica().get(rs.getDate("fecha")).put(rs.getInt("matric_alumno"), rs.getDouble("nota"));
                } else{
                    result.get(rs.getInt("codigo")).addNota(rs.getDate("fecha"), rs.getInt("matric_alumno"), rs.getDouble("nota"));
                }
            } else { 
                Dificultad dificultad = Dificultad.valueOf(rs.getString("dificultad"));
                Practica pa = new Practica(rs.getInt("codigo"), rs.getString("titulo"), dificultad);
                pa.addNota(rs.getDate("fecha"), rs.getInt("matric_alumno"), rs.getDouble("nota"));
                result.put(rs.getInt("codigo"), pa);
            }
        }
        
        rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para listar todas las prácticas de la base de datos junto con los profesores que las han diseñado y las fechas del diseño
     * @return Colección de prácticas
     * @throws SQLException 
     */
    public Map<Integer, Practica> findAllDiseno() throws SQLException {
        PreparedStatement ps = con.prepareStatement("SELECT codigo, titulo, dificultad, dni_prof, fecha FROM practica JOIN disena ON practica.codigo=disena.cod_pract");
	ResultSet rs = ps.executeQuery();
        Map<Integer, Practica> result = new HashMap<>();

	while (rs.next()) {
            if(result.containsKey(rs.getInt("codigo"))){
                result.get(rs.getInt("codigo")).getProfDiseno().put(rs.getString("dni_prof"), rs.getDate("fecha"));
            } else {
                Dificultad dificultad = Dificultad.valueOf(rs.getString("dificultad"));
                Practica pa = new Practica(rs.getInt("codigo"), rs.getString("titulo"), dificultad);
                pa.getProfDiseno().put(rs.getString("dni_prof"), rs.getDate("fecha"));
                result.put(rs.getInt("codigo"), pa);
            }
	}
        
        rs.close();
	ps.close();

	return result;
    }
    
    /**
     * Método para insertar una práctica en la base de datos
     * @param pa Práctica a insertar
     * @throws SQLException 
     */
    public void insertPractica(Practica pa) throws SQLException{
        PreparedStatement ps = con.prepareStatement("INSERT INTO practica (codigo, titulo, dificultad) VALUES (?, ?, ?)");
        ps.setInt(1, pa.getCodPractica());
        ps.setString(2, pa.getTitulo());
        ps.setString(3, pa.getDificultad().toString());

        ps.executeUpdate();

        ps.close();
    }
    
    /**
     * Método para insertar los alumnos que han realizado cada práctica con sus respectivas notas y la fecha en la que han realizado la práctica
     * @param pa Práctica a insertar
     * @param fecha Fecha de realización de la práctica
     * @param numMatr Número de matrícula del alumno
     * @throws SQLException 
     */
    public void insertNota(Practica pa, Date fecha, int numMatr) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO realiza_practica (matric_alumno, cod_pract, fecha, nota) VALUES (?, ?, ?, ?)");
        ps.setInt(1, numMatr);
        ps.setInt(2, pa.getCodPractica());
        ps.setDate(3, fecha);
        ps.setDouble(4, pa.getNotasPractica().get(fecha).get(numMatr));
            
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para insertar los profesores que han diseñado cada práctica junto con la fecha del diseño
     * @param pa Práctica a insertar
     * @param dni DNI del profesor que diseña la práctica
     * @throws SQLException 
     */
    public void insertDiseno(Practica pa, String dni) throws SQLException {
        PreparedStatement ps = con.prepareStatement("INSERT INTO disena (dni_prof, cod_pract, fecha) VALUES (?, ?, ?)");
        ps.setString(1, dni);
        ps.setInt(2, pa.getCodPractica());
        ps.setDate(3, pa.getProfDiseno().get(dni));
            
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para eliminar una práctica de la base de datos
     * @param pa Práctica a eliminar
     * @throws SQLException 
     */
    public void delete(Practica pa) throws SQLException {
	deletePractica(pa.getCodPractica());
    }
    
    /**
     * Método para eliminar una práctica de la base de datos
     * @param codigo Código de práctica
     * @throws SQLException 
     */
    public void deletePractica(int codigo) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM practica WHERE codigo = ?");
        ps.setInt(1, codigo);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para eliminar una nota de un alumno en una práctica de la base de datos
     * @param codigo Código de práctica
     * @param numMatricula Número de matrícula del alumno
     * @throws SQLException 
     */
    public void deleteNota(int codigo, int numMatricula) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM realiza_practica WHERE matric_alumno = ? AND cod_pract = ?");
        ps.setInt(1, numMatricula);
        ps.setInt(2, codigo);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para eliminar un profesor que ha diseñado una práctica de la base de datos junto con la fecha del diseño
     * @param codigo Código de práctica
     * @param dni DNI del profesor
     * @throws SQLException 
     */
    public void deleteDiseno(int codigo, String dni) throws SQLException {
	PreparedStatement ps = con.prepareStatement("DELETE FROM disena WHERE dni_prof = ? AND cod_pract = ?");
        ps.setString(1, dni);
        ps.setInt(2, codigo);
        
        ps.executeUpdate();
        
        ps.close();
    }
    
    /**
     * Método para modificar una práctica de la base de datos
     * @param pa Práctica a modificar
     * @throws SQLException 
     */
    public void updatePractica(Practica pa) throws SQLException{
        PreparedStatement ps = con.prepareStatement("UPDATE practica SET codigo = ?, titulo = ?, dificultad = ? WHERE codigo = ?");
	ps.setInt(1, pa.getCodPractica());
	ps.setString(2, pa.getTitulo());
        ps.setString(3, pa.getDificultad().toString());
        ps.setInt(4, pa.getCodPractica());
		
	ps.executeUpdate();
		
	ps.close(); 
    }
    
    /**
     * Método para modificar una nota de un alumno en una práctica de la base de datos
     * @param pa Práctica de la cual queremos modificar una nota
     * @param fecha Fecha de realización de la práctica
     * @param numMatr Número de matrícula del alumno
     * @throws SQLException 
     */
    public void updateNota(Practica pa, Date fecha, int numMatr) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE realiza_practica SET matric_alumno = ?, cod_pract = ?, fecha = ?, nota = ? WHERE fecha = ? AND matric_alumno = ?");
        ps.setInt(1, numMatr);
        ps.setInt(2, pa.getCodPractica());
        ps.setDate(3, fecha);
        ps.setDouble(4, pa.getNotasPractica().get(fecha).get(numMatr));
        ps.setDate(5, fecha);
        ps.setInt(6, numMatr);
                
        ps.executeUpdate();
        
        ps.close(); 
    }
        
    /**
     * Método para modificar un profesor que ha diseñado una práctica de la base de datos
     * @param pa Práctica a modificar
     * @param dni DNI del profesor diseñador
     * @throws SQLException 
     */
    public void updateDiseno(Practica pa, String dni) throws SQLException {
        PreparedStatement ps = con.prepareStatement("UPDATE disena SET dni_prof = ?, cod_pract = ?, fecha = ? WHERE cod_pract = ?");
        ps.setString(1, dni);
        ps.setInt(2, pa.getCodPractica());
        ps.setDate(3, pa.getProfDiseno().get(dni));
        ps.setInt(4, pa.getCodPractica());
            
        ps.executeUpdate();
        
        ps.close();
    }
}
