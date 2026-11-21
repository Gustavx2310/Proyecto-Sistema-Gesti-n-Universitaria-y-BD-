package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.Docente;

public class DocenteDAO {

    public boolean registrar(Docente docente) {
        String sqlPersona = "INSERT INTO Persona (Nombre, ApellidoPaterno, ApellidoMaterno) VALUES (?, ?, ?)";
        // Corregido: Usamos IdDocente
        String sqlDocente = "INSERT INTO Docente (IdDocente, NumeroEmpleado, EstatusLaboral) VALUES (?, ?, ?)";

        Connection conn = null;
        
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                psPersona.setString(1, docente.getNombre());
                psPersona.setString(2, docente.getApellidoPaterno());
                psPersona.setString(3, docente.getApellidoMaterno());
                psPersona.executeUpdate();

                ResultSet rs = psPersona.getGeneratedKeys();
                int idGenerado = 0;
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }

                try (PreparedStatement psDocente = conn.prepareStatement(sqlDocente)) {
                    psDocente.setInt(1, idGenerado);
                    psDocente.setString(2, docente.getNumeroEmpleado());
                    psDocente.setString(3, docente.getEstatusLaboral());
                    psDocente.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                System.err.println("Error en el rollback: " + ex.getMessage());
            }
            System.err.println("Error al registrar docente: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar conexión: " + ex.getMessage());
            }
        }
    }

    public List<Docente> obtenerTodos() {
        List<Docente> lista = new ArrayList<>();
        // Corregido: La unión se hace con d.IdDocente
        String sql = "SELECT p.IdPersona, p.Nombre, p.ApellidoPaterno, p.ApellidoMaterno, d.NumeroEmpleado, d.EstatusLaboral " +
                     "FROM Persona p INNER JOIN Docente d ON p.IdPersona = d.IdDocente";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Docente d = new Docente();
                d.setIdPersona(rs.getInt("IdPersona"));
                d.setNombre(rs.getString("Nombre"));
                d.setApellidoPaterno(rs.getString("ApellidoPaterno"));
                d.setApellidoMaterno(rs.getString("ApellidoMaterno"));
                d.setNumeroEmpleado(rs.getString("NumeroEmpleado"));
                d.setEstatusLaboral(rs.getString("EstatusLaboral"));
                lista.add(d);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar docentes: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificar(Docente docente) {
        String sqlPersona = "UPDATE Persona SET Nombre = ?, ApellidoPaterno = ?, ApellidoMaterno = ? WHERE IdPersona = ?";
        // Corregido: Usamos IdDocente
        String sqlDocente = "UPDATE Docente SET NumeroEmpleado = ?, EstatusLaboral = ? WHERE IdDocente = ?";

        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setString(1, docente.getNombre());
                psPersona.setString(2, docente.getApellidoPaterno());
                psPersona.setString(3, docente.getApellidoMaterno());
                psPersona.setInt(4, docente.getIdPersona());
                psPersona.executeUpdate();
            }

            try (PreparedStatement psDocente = conn.prepareStatement(sqlDocente)) {
                psDocente.setString(1, docente.getNumeroEmpleado());
                psDocente.setString(2, docente.getEstatusLaboral());
                psDocente.setInt(3, docente.getIdPersona());
                psDocente.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) { }
            System.err.println("Error al modificar docente: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) { }
        }
    }

    public boolean eliminar(int idPersona) {
        // Corregido: Usamos IdDocente
        String sqlDocente = "DELETE FROM Docente WHERE IdDocente = ?";
        String sqlPersona = "DELETE FROM Persona WHERE IdPersona = ?";

        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psDocente = conn.prepareStatement(sqlDocente)) {
                psDocente.setInt(1, idPersona);
                psDocente.executeUpdate();
            }

            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setInt(1, idPersona);
                psPersona.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) { }
            System.err.println("Error al eliminar docente: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException ex) { }
        }
    }
}