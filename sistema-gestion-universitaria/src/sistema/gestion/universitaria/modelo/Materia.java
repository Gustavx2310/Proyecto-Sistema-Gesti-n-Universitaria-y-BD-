package sistema.gestion.universitaria.modelo;

public class Materia {
    private Integer idMateria;
    private Integer idCarrera;
    private String nombreMateria;
    private Integer creditos;
    private String estatus;

    public Materia() {}

    public Materia(Integer idMateria, Integer idCarrera, String nombreMateria, Integer creditos, String estatus) {
        this.idMateria = idMateria;
        this.idCarrera = idCarrera;
        this.nombreMateria = nombreMateria;
        this.creditos = creditos;
        this.estatus = estatus;
    }

    public Integer getIdMateria() { return idMateria; }
    public void setIdMateria(Integer idMateria) { this.idMateria = idMateria; }

    public Integer getIdCarrera() { return idCarrera; }
    public void setIdCarrera(Integer idCarrera) { this.idCarrera = idCarrera; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}