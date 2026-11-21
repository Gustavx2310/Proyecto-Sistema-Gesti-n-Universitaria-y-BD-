package sistema.gestion.universitaria.modelo;

import java.time.LocalDateTime;

public class Alumno extends Persona {
    
    private String matricula;
    private Integer idCarreraActual;
    private Integer semestreActual;
    private String estatusAcademico;

    public Alumno() {
        super(); // Llama al constructor vacío de Persona
    }

    public Alumno(Integer idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDateTime fechaRegistro, 
                  String matricula, Integer idCarreraActual, Integer semestreActual, String estatusAcademico) {
        
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, fechaRegistro); // Envía datos a Persona
        this.matricula = matricula;
        this.idCarreraActual = idCarreraActual;
        this.semestreActual = semestreActual;
        this.estatusAcademico = estatusAcademico;
    }

    // Getters y Setters específicos de Alumno
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public Integer getIdCarreraActual() { return idCarreraActual; }
    public void setIdCarreraActual(Integer idCarreraActual) { this.idCarreraActual = idCarreraActual; }

    public Integer getSemestreActual() { return semestreActual; }
    public void setSemestreActual(Integer semestreActual) { this.semestreActual = semestreActual; }

    public String getEstatusAcademico() { return estatusAcademico; }
    public void setEstatusAcademico(String estatusAcademico) { this.estatusAcademico = estatusAcademico; }
}