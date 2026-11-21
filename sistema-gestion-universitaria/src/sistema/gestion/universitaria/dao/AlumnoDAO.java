package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.modelo.Alumno;

public class AlumnoDAO {

    public boolean registrar(Alumno alumno) {
        String sqlPersona = "INSERT INTO Persona (Nombre, ApellidoPaterno, ApellidoMaterno) VALUES (?, ?, ?)";
        String sqlAlumno = "INSERT INTO Alumno (IdAlumno, Matricula, IdCarreraActual, SemestreActual, EstatusAcademico) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona, Statement.RETURN_GENERATED_KEYS)) {
                psPersona.setString(1, alumno.getNombre());
                psPersona.setString(2, alumno.getApellidoPaterno());
                psPersona.setString(3, alumno.getApellidoMaterno());
                psPersona.executeUpdate();

                ResultSet rs = psPersona.getGeneratedKeys();
                int idGenerado = 0;
                if (rs.next()) {
                    idGenerado = rs.getInt(1);
                }

                try (PreparedStatement psAlumno = conn.prepareStatement(sqlAlumno)) {
                    psAlumno.setInt(1, idGenerado);
                    psAlumno.setString(2, alumno.getMatricula());
                    
                    if(alumno.getIdCarreraActual() != null) {
                        psAlumno.setInt(3, alumno.getIdCarreraActual());
                    } else {
                        psAlumno.setNull(3, Types.INTEGER);
                    }
                    
                    if(alumno.getSemestreActual() != null) {
                        psAlumno.setInt(4, alumno.getSemestreActual());
                    } else {
                        psAlumno.setNull(4, Types.INTEGER);
                    }
                    
                    psAlumno.setString(5, alumno.getEstatusAcademico());
                    psAlumno.executeUpdate();
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) { }
            System.err.println("Error al registrar alumno: " + e.getMessage());
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

    public List<Alumno> obtenerTodos() {
        List<Alumno> lista = new ArrayList<>();
        String sql = "SELECT p.IdPersona, p.Nombre, p.ApellidoPaterno, p.ApellidoMaterno, " +
                     "a.Matricula, a.IdCarreraActual, a.SemestreActual, a.EstatusAcademico " +
                     "FROM Persona p INNER JOIN Alumno a ON p.IdPersona = a.IdAlumno";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Alumno a = new Alumno();
                a.setIdPersona(rs.getInt("IdPersona"));
                a.setNombre(rs.getString("Nombre"));
                a.setApellidoPaterno(rs.getString("ApellidoPaterno"));
                a.setApellidoMaterno(rs.getString("ApellidoMaterno"));
                a.setMatricula(rs.getString("Matricula"));
                
                a.setIdCarreraActual((Integer) rs.getObject("IdCarreraActual"));
                a.setSemestreActual((Integer) rs.getObject("SemestreActual"));
                a.setEstatusAcademico(rs.getString("EstatusAcademico"));
                
                lista.add(a);
            }
        } catch (SQLException e) {
            System.err.println("Error al consultar alumnos: " + e.getMessage());
        }
        return lista;
    }

    public boolean modificar(Alumno alumno) {
        String sqlPersona = "UPDATE Persona SET Nombre = ?, ApellidoPaterno = ?, ApellidoMaterno = ? WHERE IdPersona = ?";
        String sqlAlumno = "UPDATE Alumno SET Matricula = ?, IdCarreraActual = ?, SemestreActual = ?, EstatusAcademico = ? WHERE IdAlumno = ?";

        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psPersona = conn.prepareStatement(sqlPersona)) {
                psPersona.setString(1, alumno.getNombre());
                psPersona.setString(2, alumno.getApellidoPaterno());
                psPersona.setString(3, alumno.getApellidoMaterno());
                psPersona.setInt(4, alumno.getIdPersona());
                psPersona.executeUpdate();
            }

            try (PreparedStatement psAlumno = conn.prepareStatement(sqlAlumno)) {
                psAlumno.setString(1, alumno.getMatricula());
                
                if(alumno.getIdCarreraActual() != null) psAlumno.setInt(2, alumno.getIdCarreraActual());
                else psAlumno.setNull(2, Types.INTEGER);
                
                if(alumno.getSemestreActual() != null) psAlumno.setInt(3, alumno.getSemestreActual());
                else psAlumno.setNull(3, Types.INTEGER);
                
                psAlumno.setString(4, alumno.getEstatusAcademico());
                psAlumno.setInt(5, alumno.getIdPersona());
                psAlumno.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) { }
            System.err.println("Error al modificar alumno: " + e.getMessage());
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
        String sqlAlumno = "DELETE FROM Alumno WHERE IdAlumno = ?";
        String sqlPersona = "DELETE FROM Persona WHERE IdPersona = ?";

        Connection conn = null;
        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false);

            try (PreparedStatement psAlumno = conn.prepareStatement(sqlAlumno)) {
                psAlumno.setInt(1, idPersona);
                psAlumno.executeUpdate();
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
            System.err.println("Error al eliminar alumno: " + e.getMessage());
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