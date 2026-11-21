package sistema.gestion.universitaria.modelo;

public class Carrera {
    private Integer idCarrera;
    private Integer idEspacio;
    private String nombreCarrera;
    private String estatus;

    public Carrera() {}

    public Carrera(Integer idCarrera, Integer idEspacio, String nombreCarrera, String estatus) {
        this.idCarrera = idCarrera;
        this.idEspacio = idEspacio;
        this.nombreCarrera = nombreCarrera;
        this.estatus = estatus;
    }

    public Integer getIdCarrera() { return idCarrera; }
    public void setIdCarrera(Integer idCarrera) { this.idCarrera = idCarrera; }

    public Integer getIdEspacio() { return idEspacio; }
    public void setIdEspacio(Integer idEspacio) { this.idEspacio = idEspacio; }

    public String getNombreCarrera() { return nombreCarrera; }
    public void setNombreCarrera(String nombreCarrera) { this.nombreCarrera = nombreCarrera; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}