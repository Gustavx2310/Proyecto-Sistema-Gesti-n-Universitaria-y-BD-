package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.Carrera;

public class CarreraDAO {

    public boolean registrar(Carrera carrera) {
        String sql = "INSERT INTO Carrera (IdEspacio, NombreCarrera, Estatus) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, carrera.getIdEspacio());
            ps.setString(2, carrera.getNombreCarrera());
            ps.setString(3, carrera.getEstatus());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar carrera: " + e.getMessage());
            return false;
        }
    }

    public List<Carrera> obtenerTodas() {
        List<Carrera> lista = new ArrayList<>();
        String sql = "SELECT * FROM Carrera";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Carrera c = new Carrera();
                c.setIdCarrera(rs.getInt("IdCarrera"));
                c.setIdEspacio(rs.getInt("IdEspacio"));
                c.setNombreCarrera(rs.getString("NombreCarrera"));
                c.setEstatus(rs.getString("Estatus"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar carreras: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificar(Carrera carrera) {
        String sql = "UPDATE Carrera SET IdEspacio = ?, NombreCarrera = ?, Estatus = ? WHERE IdCarrera = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, carrera.getIdEspacio());
            ps.setString(2, carrera.getNombreCarrera());
            ps.setString(3, carrera.getEstatus());
            ps.setInt(4, carrera.getIdCarrera());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al modificar carrera: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminar(int idCarrera) {
        String sql = "DELETE FROM Carrera WHERE IdCarrera = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idCarrera);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar carrera: " + e.getMessage());
            return false;
        }
    }
}