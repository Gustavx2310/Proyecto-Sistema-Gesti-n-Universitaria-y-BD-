package sistema.gestion.universitaria.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    
    // Al apuntar a la IP de VirtualBox, funciona tanto en el anfitrión (Windows) como en Fedora
    private static final String URL = "jdbc:mysql://192.168.56.1:3306/gestion_universitaria?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "tester1";
    private static final String PASSWORD = "gustavx2310."; 
    
    public static Connection getConnection() throws SQLException {
        // El usuario tester1@'%' será aceptado sin importar desde qué pantalla ejecutes esto
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}