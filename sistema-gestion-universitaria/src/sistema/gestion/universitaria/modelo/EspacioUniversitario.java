package sistema.gestion.universitaria.modelo;

public class EspacioUniversitario {
    private Integer idEspacio;
    private String nombreOficial;
    private String estatus;

    public EspacioUniversitario() {}

    public EspacioUniversitario(Integer idEspacio, String nombreOficial, String estatus) {
        this.idEspacio = idEspacio;
        this.nombreOficial = nombreOficial;
        this.estatus = estatus;
    }

    public Integer getIdEspacio() { return idEspacio; }
    public void setIdEspacio(Integer idEspacio) { this.idEspacio = idEspacio; }

    public String getNombreOficial() { return nombreOficial; }
    public void setNombreOficial(String nombreOficial) { this.nombreOficial = nombreOficial; }

    public String getEstatus() { return estatus; }
    public void setEstatus(String estatus) { this.estatus = estatus; }
}