package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.AlumnoGrupo;

public class AlumnoGrupoDAO {

    public boolean registrar(AlumnoGrupo ag) {
        // Los parciales inician en 0.00 por defecto según la base de datos, la fecha se toma del servidor
        String sql = "INSERT INTO alumno_grupo (IdAlumno, IdGrupo, FechaInscripcion) VALUES (?, ?, NOW())";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, ag.getIdAlumno());
            ps.setInt(2, ag.getIdGrupo());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al inscribir alumno: " + e.getMessage());
            return false;
        }
    }

    public List<AlumnoGrupo> obtenerPorGrupo(int idGrupo) {
        List<AlumnoGrupo> lista = new ArrayList<>();
        // Se eliminó Parcial3 de la consulta SQL
        String sql = "SELECT ag.IdAlumno, ag.IdGrupo, ag.Parcial1, ag.Parcial2, ag.CalificacionFinal, ag.FechaInscripcion, " +
                     "a.Matricula, p.Nombre, p.ApellidoPaterno " +
                     "FROM alumno_grupo ag " +
                     "INNER JOIN Alumno a ON ag.IdAlumno = a.IdAlumno " +
                     "INNER JOIN Persona p ON a.IdAlumno = p.IdPersona " +
                     "WHERE ag.IdGrupo = ?";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    AlumnoGrupo ag = new AlumnoGrupo();
                    ag.setIdAlumno(rs.getInt("IdAlumno"));
                    ag.setIdGrupo(rs.getInt("IdGrupo"));
                    
                    // Extracción de las columnas de parciales (solo 1 y 2)
                    ag.setParcial1(rs.getDouble("Parcial1"));
                    ag.setParcial2(rs.getDouble("Parcial2"));
                    
                    // Corrección de casting: de BigDecimal a Double para la calificación final (Calculada por MySQL)
                    java.math.BigDecimal calif = rs.getBigDecimal("CalificacionFinal");
                    ag.setCalificacionFinal(calif != null ? calif.doubleValue() : null); 
                    
                    ag.setFechaInscripcion(rs.getTimestamp("FechaInscripcion"));
                    ag.setMatricula(rs.getString("Matricula"));
                    ag.setNombreCompletoAlumno(rs.getString("Nombre") + " " + rs.getString("ApellidoPaterno"));
                    lista.add(ag);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar inscripciones: " + e.getMessage());
        }
        return lista;
    }

    // Método ajustado para recibir y actualizar únicamente 2 parciales
    public boolean actualizarCalificaciones(int idAlumno, int idGrupo, double p1, double p2) {
        String sql = "UPDATE alumno_grupo SET Parcial1 = ?, Parcial2 = ? WHERE IdAlumno = ? AND IdGrupo = ?";
        
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setDouble(1, p1);
            ps.setDouble(2, p2);
            // Índices recorridos tras eliminar p3
            ps.setInt(3, idAlumno);
            ps.setInt(4, idGrupo);
            
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar calificaciones: " + e.getMessage());
            return false;
        }
    }

    public boolean eliminar(int idAlumno, int idGrupo) {
        String sql = "DELETE FROM alumno_grupo WHERE IdAlumno = ? AND IdGrupo = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, idAlumno);
            ps.setInt(2, idGrupo);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al dar de baja: " + e.getMessage());
            return false;
        }
    }
}