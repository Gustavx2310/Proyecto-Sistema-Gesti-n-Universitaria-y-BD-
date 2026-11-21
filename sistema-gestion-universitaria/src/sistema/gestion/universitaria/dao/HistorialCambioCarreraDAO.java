package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.sql.SQLException;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.HistorialCambioCarrera;

public class HistorialCambioCarreraDAO {

    public boolean registrarCambio(HistorialCambioCarrera historial) {
        String sql = "INSERT INTO Historial_Cambios_Carrera (IdAlumno, IdCarreraAnterior, IdCarreraNueva, MotivoCambio) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, historial.getIdAlumno());
            
            // Manejo especial para IdCarreraAnterior que puede ser NULL
            if (historial.getIdCarreraAnterior() != null) {
                ps.setInt(2, historial.getIdCarreraAnterior());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            
            ps.setInt(3, historial.getIdCarreraNueva());
            ps.setString(4, historial.getMotivoCambio());
            
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar historial de cambio: " + e.getMessage());
            return false;
        }
    }
}