package sistema.gestion.universitaria.modelo;

import java.time.LocalDateTime;

public class Persona {
    
    // Atributos protected para que Alumno y Docente puedan heredarlos
    protected Integer idPersona; 
    protected String nombre;
    protected String apellidoPaterno;
    protected String apellidoMaterno;
    protected LocalDateTime fechaRegistro;

    public Persona() {
    }

    public Persona(Integer idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDateTime fechaRegistro) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.fechaRegistro = fechaRegistro;
    }

    public Integer getIdPersona() { return idPersona; }
    public void setIdPersona(Integer idPersona) { this.idPersona = idPersona; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidoPaterno() { return apellidoPaterno; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }

    public String getApellidoMaterno() { return apellidoMaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }

    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}