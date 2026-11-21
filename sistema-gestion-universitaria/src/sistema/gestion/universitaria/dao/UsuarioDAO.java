package sistema.gestion.universitaria.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;
import sistema.gestion.universitaria.conexion.ConexionDB;
import sistema.gestion.universitaria.dto.UsuarioDTO;

public class UsuarioDAO {

    public UsuarioDTO login(String username, String plainPassword) {
        UsuarioDTO usuarioValido = null;
        // Se ejecuta un JOIN estricto para extraer el Rol al mismo tiempo
        String sql = "SELECT u.IdUsuario, u.IdPersona, u.Username, u.PasswordHash, r.NombreRol " +
                     "FROM Usuario u " +
                     "INNER JOIN Rol r ON u.IdRol = r.IdRol " +
                     "WHERE u.Username = ? AND u.Activo = TRUE";

        try (Connection conn = ConexionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
             
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashEnBD = rs.getString("PasswordHash");
                    
                    // Validación del Hash Criptográfico
                    if (BCrypt.checkpw(plainPassword, hashEnBD)) {
                        usuarioValido = new UsuarioDTO(
                            rs.getInt("IdUsuario"),
                            rs.getInt("IdPersona"),
                            rs.getString("Username"),
                            rs.getString("NombreRol")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error en autenticación: " + e.getMessage());
        }
        return usuarioValido; // Retorna null si las credenciales son incorrectas
    }
}