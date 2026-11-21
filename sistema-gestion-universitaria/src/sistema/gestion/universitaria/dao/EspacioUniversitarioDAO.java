package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.EspacioUniversitario;

public class EspacioUniversitarioDAO {

    // 1. CREATE - Insertar un nuevo espacio
    public boolean registrar(EspacioUniversitario espacio) {
        String sql = "INSERT INTO EspacioUniversitario (NombreOficial, Estatus) VALUES (?, ?)";
        
        // El bloque try-with-resources cierra la conexión automáticamente
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, espacio.getNombreOficial());
            ps.setString(2, espacio.getEstatus());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar espacio: " + e.getMessage());
            return false;
        }
    }

    // 2. READ - Obtener todos los espacios (Para mostrarlos en un JTable más adelante)
    public List<EspacioUniversitario> obtenerTodos() {
        List<EspacioUniversitario> lista = new ArrayList<>();
        String sql = "SELECT * FROM EspacioUniversitario";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                EspacioUniversitario esp = new EspacioUniversitario();
                esp.setIdEspacio(rs.getInt("IdEspacio"));
                esp.setNombreOficial(rs.getString("NombreOficial"));
                esp.setEstatus(rs.getString("Estatus"));
                lista.add(esp);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar espacios: " + e.getMessage());
        }
        return lista;
    }

    // 3. UPDATE - Modificar un espacio existente
    public boolean modificar(EspacioUniversitario espacio) {
        String sql = "UPDATE EspacioUniversitario SET NombreOficial = ?, Estatus = ? WHERE IdEspacio = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, espacio.getNombreOficial());
            ps.setString(2, espacio.getEstatus());
            ps.setInt(3, espacio.getIdEspacio());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al modificar espacio: " + e.getMessage());
            return false;
        }
    }

    // 4. DELETE - Eliminar un espacio (Nota: Fallará si tiene carreras asociadas por la restricción RESTRICT de tu BD)
    public boolean eliminar(int idEspacio) {
        String sql = "DELETE FROM EspacioUniversitario WHERE IdEspacio = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idEspacio);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar espacio (¿Tiene carreras asignadas?): " + e.getMessage());
            return false;
        }
    }
}