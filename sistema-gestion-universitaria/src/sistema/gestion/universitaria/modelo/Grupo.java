package sistema.gestion.universitaria.modelo;

public class Grupo {
    
    private Integer idGrupo;
    private String claveGrupo; // Agregada según tu diagrama
    private Integer idMateria;
    private Integer idDocente;
    private String periodoEscolar; // Corregido según tu diagrama
    
    // Variables extra para mostrar nombres en la tabla
    private String nombreMateria;
    private String nombreDocente;

    public Grupo() {
    }

    public Grupo(Integer idGrupo, String claveGrupo, Integer idMateria, Integer idDocente, String periodoEscolar) {
        this.idGrupo = idGrupo;
        this.claveGrupo = claveGrupo;
        this.idMateria = idMateria;
        this.idDocente = idDocente;
        this.periodoEscolar = periodoEscolar;
    }

    public Integer getIdGrupo() { return idGrupo; }
    public void setIdGrupo(Integer idGrupo) { this.idGrupo = idGrupo; }

    public String getClaveGrupo() { return claveGrupo; }
    public void setClaveGrupo(String claveGrupo) { this.claveGrupo = claveGrupo; }

    public Integer getIdMateria() { return idMateria; }
    public void setIdMateria(Integer idMateria) { this.idMateria = idMateria; }

    public Integer getIdDocente() { return idDocente; }
    public void setIdDocente(Integer idDocente) { this.idDocente = idDocente; }

    public String getPeriodoEscolar() { return periodoEscolar; }
    public void setPeriodoEscolar(String periodoEscolar) { this.periodoEscolar = periodoEscolar; }

    public String getNombreMateria() { return nombreMateria; }
    public void setNombreMateria(String nombreMateria) { this.nombreMateria = nombreMateria; }

    public String getNombreDocente() { return nombreDocente; }
    public void setNombreDocente(String nombreDocente) { this.nombreDocente = nombreDocente; }
}