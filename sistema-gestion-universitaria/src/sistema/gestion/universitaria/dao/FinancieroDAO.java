package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import sistema.gestion.universitaria.conexion.ConexionDB;

public class FinancieroDAO {

    public boolean procesarPagoColegiatura(int idAlumno, double monto, String recibo) {
        String sqlInsertarPago = "INSERT INTO pagos_colegiatura (IdAlumno, Monto, Recibo, FechaPago) VALUES (?, ?, ?, NOW())";
        String sqlActualizarSaldo = "UPDATE Alumno SET SaldoDeudor = SaldoDeudor - ? WHERE IdAlumno = ?";
        
        Connection conn = null;

        try {
            conn = ConexionDB.getConnection();
            conn.setAutoCommit(false); 

            try (PreparedStatement psPago = conn.prepareStatement(sqlInsertarPago)) {
                psPago.setInt(1, idAlumno);
                psPago.setDouble(2, monto);
                psPago.setString(3, recibo);
                psPago.executeUpdate();
            }

            try (PreparedStatement psSaldo = conn.prepareStatement(sqlActualizarSaldo)) {
                psSaldo.setDouble(1, monto);
                psSaldo.setInt(2, idAlumno);
                psSaldo.executeUpdate();
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Error en transacción ACID. Aplicando Rollback: " + e.getMessage());
            try {
                if (conn != null) conn.rollback(); 
            } catch (SQLException ex) { }
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) { }
        }
    }

    public Object[] buscarDetallesAlumnoPorMatricula(String matricula) {
        String sql = "SELECT a.IdAlumno, p.Nombre, p.ApellidoPaterno, " +
                     "COALESCE(c.NombreCarrera, 'Sin Carrera') AS NombreCarrera, " +
                     "COALESCE(eu.NombreOficial, 'Sin Centro') AS NombreOficial " +
                     "FROM Alumno a " +
                     "INNER JOIN Persona p ON a.IdAlumno = p.IdPersona " +
                     "LEFT JOIN carrera c ON a.IdCarreraActual = c.IdCarrera " +
                     "LEFT JOIN espaciouniversitario eu ON c.IdEspacio = eu.IdEspacio " +
                     "WHERE TRIM(a.Matricula) = TRIM(?)";
                     
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, matricula);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int idInterno = rs.getInt("IdAlumno");
                    String nombreCompleto = rs.getString("Nombre") + " " + rs.getString("ApellidoPaterno");
                    String carrera = rs.getString("NombreCarrera");
                    String centro = rs.getString("NombreOficial");
                    
                    return new Object[]{idInterno, nombreCompleto, carrera, centro};
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar detalles por matrícula: " + e.getMessage());
        }
        return null;
    }

    // --- NUEVO MÉTODO AGREGADO ---
    public double obtenerSaldoDeudor(int idAlumno) {
        String sql = "SELECT SaldoDeudor FROM alumno WHERE IdAlumno = ?";
        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setInt(1, idAlumno);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("SaldoDeudor");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener saldo deudor: " + e.getMessage());
        }
        return 0.0;
    }
}