package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.Materia;

public class MateriaDAO {

    public boolean registrar(Materia materia) {
        String sql = "INSERT INTO Materia (IdCarrera, NombreMateria, Creditos, Estatus) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, materia.getIdCarrera());
            ps.setString(2, materia.getNombreMateria());
            ps.setInt(3, materia.getCreditos());
            ps.setString(4, materia.getEstatus());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al registrar materia: " + e.getMessage());
            return false;
        }
    }

    public List<Materia> obtenerTodas() {
        List<Materia> lista = new ArrayList<>();
        String sql = "SELECT * FROM Materia";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Materia m = new Materia();
                m.setIdMateria(rs.getInt("IdMateria"));
                m.setIdCarrera(rs.getInt("IdCarrera"));
                m.setNombreMateria(rs.getString("NombreMateria"));
                m.setCreditos(rs.getInt("Creditos"));
                m.setEstatus(rs.getString("Estatus"));
                lista.add(m);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar materias: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificar(Materia materia) {
        String sql = "UPDATE Materia SET IdCarrera = ?, NombreMateria = ?, Creditos = ?, Estatus = ? WHERE IdMateria = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, materia.getIdCarrera());
            ps.setString(2, materia.getNombreMateria());
            ps.setInt(3, materia.getCreditos());
            ps.setString(4, materia.getEstatus());
            ps.setInt(5, materia.getIdMateria());
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al modificar materia: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idMateria) {
        String sql = "DELETE FROM Materia WHERE IdMateria = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idMateria);
            ps.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar materia: " + e.getMessage());
            return false;
        }
    }
}