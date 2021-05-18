package conexion;

import java.sql.*;
import java.util.Properties;

/**
 * Clase ConexionDB, clase donde realizamos la conexión con la base de datos "controles" en un servidor mysql
 * 
 * @author Lourdes Navarro Tocón
 */
public class ConexionDB {
    /**
     * Atributo donde guardamos la url de la base de datos local (tipo String)
     */
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/controles?zeroDateTimeBehavior=convertToNull";
    /**
     * Atributo donde declaramos la instancia del singleton (tipo Connection)
     */
    private static Connection instance = null;
	
    /**
     * Constructor predeterminado
     */
    private ConexionDB() { }
    
    /**
     * Método getConnection donde se devuelve la instancia. Si no existe, nos la crea 
     * @return instance
     * @throws SQLException 
     */
    public static Connection getConnection() throws SQLException {
        if (instance == null) {
            Properties props = new Properties();
            props.put("user", "lnavtoc");
            props.put("password", "root");
            instance = DriverManager.getConnection(JDBC_URL, props);
        }
	return instance;
    }
}
