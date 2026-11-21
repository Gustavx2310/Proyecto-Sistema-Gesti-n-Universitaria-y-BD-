package sistema.gestion.universitaria.modelo;

import java.time.LocalDateTime;

public class Docente extends Persona {
    
    private String numeroEmpleado;
    private String estatusLaboral;

    public Docente() {
        super();
    }

    public Docente(Integer idPersona, String nombre, String apellidoPaterno, String apellidoMaterno, LocalDateTime fechaRegistro,
                   String numeroEmpleado, String estatusLaboral) {
        
        super(idPersona, nombre, apellidoPaterno, apellidoMaterno, fechaRegistro);
        this.numeroEmpleado = numeroEmpleado;
        this.estatusLaboral = estatusLaboral;
    }

    // Getters y Setters específicos de Docente
    public String getNumeroEmpleado() { return numeroEmpleado; }
    public void setNumeroEmpleado(String numeroEmpleado) { this.numeroEmpleado = numeroEmpleado; }

    public String getEstatusLaboral() { return estatusLaboral; }
    public void setEstatusLaboral(String estatusLaboral) { this.estatusLaboral = estatusLaboral; }
}