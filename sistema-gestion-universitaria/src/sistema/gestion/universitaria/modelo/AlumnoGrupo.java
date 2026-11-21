package sistema.gestion.universitaria.modelo;

import java.sql.Timestamp;

public class AlumnoGrupo {
    
    private Integer idAlumno;
    private Integer idGrupo;
    
    // Variables ajustadas a 2 parciales
    private Double parcial1;
    private Double parcial2;
    
    private Double calificacionFinal;
    private Timestamp fechaInscripcion;
    
    // Variables extra para la vista
    private String nombreCompletoAlumno;
    private String matricula;

    public AlumnoGrupo() {}

    public Integer getIdAlumno() { return idAlumno; }
    public void setIdAlumno(Integer idAlumno) { this.idAlumno = idAlumno; }

    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

    // --- Getters y Setters de los Parciales ---
    public Double getParcial1() { return parcial1; }
    public void setParcial1(Double parcial1) { this.parcial1 = parcial1; }

    public Double getParcial2() { return parcial2; }
    public void setParcial2(Double parcial2) { this.parcial2 = parcial2; }
    // ------------------------------------------

    public Double getCalificacionFinal() { return calificacionFinal; }
    public void setCalificacionFinal(Double calificacionFinal) { this.calificacionFinal = calificacionFinal; }

    public Timestamp getFechaInscripcion() { return fechaInscripcion; }
    public void setFechaInscripcion(Timestamp fechaInscripcion) { this.fechaInscripcion = fechaInscripcion; }

    public String getNombreCompletoAlumno() { return nombreCompletoAlumno; }
    public void setNombreCompletoAlumno(String nombreCompletoAlumno) { this.nombreCompletoAlumno = nombreCompletoAlumno; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
}