package sistema.gestion.universitaria.modelo;

import java.time.LocalDateTime;

public class HistorialCambioCarrera {
    private Integer idCambio;
    private Integer idAlumno;
    private Integer idCarreraAnterior;
    private Integer idCarreraNueva;
    private LocalDateTime fechaCambio;
    private String motivoCambio;

    public HistorialCambioCarrera() {}

    public HistorialCambioCarrera(Integer idCambio, Integer idAlumno, Integer idCarreraAnterior, Integer idCarreraNueva, LocalDateTime fechaCambio, String motivoCambio) {
        this.idCambio = idCambio;
        this.idAlumno = idAlumno;
        this.idCarreraAnterior = idCarreraAnterior;
        this.idCarreraNueva = idCarreraNueva;
        this.fechaCambio = fechaCambio;
        this.motivoCambio = motivoCambio;
    }

    public Integer getIdCambio() { return idCambio; }
    public void setIdCambio(Integer idCambio) { this.idCambio = idCambio; }

    public Integer getIdAlumno() { return idAlumno; }
    public void setIdAlumno(Integer idAlumno) { this.idAlumno = idAlumno; }

    public Integer getIdCarreraAnterior() { return idCarreraAnterior; }
    public void setIdCarreraAnterior(Integer idCarreraAnterior) { this.idCarreraAnterior = idCarreraAnterior; }

    public Integer getIdCarreraNueva() { return idCarreraNueva; }
    public void setIdCarreraNueva(Integer idCarreraNueva) { this.idCarreraNueva = idCarreraNueva; }

    public LocalDateTime getFechaCambio() { return fechaCambio; }
    public void setFechaCambio(LocalDateTime fechaCambio) { this.fechaCambio = fechaCambio; }

    public String getMotivoCambio() { return motivoCambio; }
    public void setMotivoCambio(String motivoCambio) { this.motivoCambio = motivoCambio; }
}