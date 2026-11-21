package sistema.gestion.universitaria.dto;

public class UsuarioDTO {
    private int idUsuario;
    private int idPersona;
    private String username;
    private String nombreRol;
    
    public UsuarioDTO(int idUsuario, int idPersona, String username, String nombreRol) {
        this.idUsuario = idUsuario;
        this.idPersona = idPersona;
        this.username = username;
        this.nombreRol = nombreRol;
    }
    
    // --- Getters completos ---
    public int getIdUsuario() { return idUsuario; }
    public int getIdPersona() { return idPersona; }
    public String getUsername() { return username; }
    public String getNombreRol() { return nombreRol; }
}