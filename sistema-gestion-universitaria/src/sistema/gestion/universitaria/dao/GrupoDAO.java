package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.Grupo;

public class GrupoDAO {

    public boolean registrar(Grupo grupo) {
        // SQL ajustado a tus columnas exactas
        String sql = "INSERT INTO Grupo (ClaveGrupo, IdMateria, IdDocente, PeriodoEscolar) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, grupo.getClaveGrupo());
            ps.setInt(2, grupo.getIdMateria());
            ps.setInt(3, grupo.getIdDocente());
            ps.setString(4, grupo.getPeriodoEscolar());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar grupo: " + e.getMessage());
            return false;
        }
    }

    public List<Grupo> obtenerTodos() {
        List<Grupo> lista = new ArrayList<>();
        // Consulta ajustada a tus columnas exactas
        String sql = "SELECT g.IdGrupo, g.ClaveGrupo, g.PeriodoEscolar, " +
                     "m.IdMateria, m.NombreMateria AS NombreMateria, " +
                     "p.IdPersona AS IdDocente, p.Nombre AS NombreDocente, p.ApellidoPaterno " +
                     "FROM Grupo g " +
                     "INNER JOIN Materia m ON g.IdMateria = m.IdMateria " +
                     "INNER JOIN Docente d ON g.IdDocente = d.IdDocente " +
                     "INNER JOIN Persona p ON d.IdDocente = p.IdPersona";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Grupo g = new Grupo();
                g.setIdGrupo(rs.getInt("IdGrupo"));
                g.setClaveGrupo(rs.getString("ClaveGrupo"));
                g.setIdMateria(rs.getInt("IdMateria"));
                g.setIdDocente(rs.getInt("IdDocente"));
                g.setPeriodoEscolar(rs.getString("PeriodoEscolar"));
                
                g.setNombreMateria(rs.getString("NombreMateria"));
                g.setNombreDocente(rs.getString("NombreDocente") + " " + rs.getString("ApellidoPaterno"));
                
                lista.add(g);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar grupos: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificar(Grupo grupo) {
        // SQL ajustado a tus columnas exactas
        String sql = "UPDATE Grupo SET ClaveGrupo = ?, IdMateria = ?, IdDocente = ?, PeriodoEscolar = ? WHERE IdGrupo = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, grupo.getClaveGrupo());
            ps.setInt(2, grupo.getIdMateria());
            ps.setInt(3, grupo.getIdDocente());
            ps.setString(4, grupo.getPeriodoEscolar());
            ps.setInt(5, grupo.getIdGrupo());
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar grupo: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idGrupo) {
        String sql = "DELETE FROM Grupo WHERE IdGrupo = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idGrupo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar grupo: " + e.getMessage());
            return false;
        }
    }
}