package sistema.gestion.universitaria.modelo;

import java.sql.Timestamp;

public class PagoColegiatura {
    private int idPago;
    private int idAlumno;
    private double monto;
    private String folioRecibo;
    private Timestamp fechaPago;

    public PagoColegiatura() {}

    public int getIdPago() { return idPago; }
    public void setIdPago(int idPago) { this.idPago = idPago; }

    public int getIdAlumno() { return idAlumno; }
    public void setIdAlumno(int idAlumno) { this.idAlumno = idAlumno; }

    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }

    public String getFolioRecibo() { return folioRecibo; }
    public void setFolioRecibo(String folioRecibo) { this.folioRecibo = folioRecibo; }

    public Timestamp getFechaPago() { return fechaPago; }
    public void setFechaPago(Timestamp fechaPago) { this.fechaPago = fechaPago; }
}