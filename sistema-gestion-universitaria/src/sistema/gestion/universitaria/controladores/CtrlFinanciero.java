package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import sistema.gestion.universitaria.dao.FinancieroDAO;
import sistema.gestion.universitaria.vistas.FrmPagos;

// Librerías para el PDF
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class CtrlFinanciero implements ActionListener {

    private final FrmPagos vista;
    private final FinancieroDAO dao;
    
    private int idAlumnoSeleccionado = -1; 

    public CtrlFinanciero(FrmPagos vista, FinancieroDAO dao) {
        this.vista = vista;
        this.dao = dao;
        
        this.vista.btnProcesarPago.addActionListener(this);
        this.vista.btnBuscarAlumno.addActionListener(this);
    }

    public void iniciar() {
        vista.setTitle("Módulo de Pagos - Colegiatura");
        
        vista.txtNombreAlumno.setEditable(false);
        vista.txtCarrera.setEditable(false);
        vista.txtCentroUniversitario.setEditable(false);
        
        // --- RESTRICCIONES DE TECLADO ---
        vista.txtMatriculaAlumno.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c)) {
                    evt.consume(); 
                }
            }
        });

        vista.txtMonto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent evt) {
                char c = evt.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    evt.consume();
                }
                if (c == '.' && vista.txtMonto.getText().contains(".")) {
                    evt.consume();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // --- BÚSQUEDA POR MATRÍCULA ---
        if (e.getSource() == vista.btnBuscarAlumno) {
            String matriculaIngresada = vista.txtMatriculaAlumno.getText().trim();
            
            if (matriculaIngresada.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Primero ingrese la matrícula del alumno a buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Object[] datos = dao.buscarDetallesAlumnoPorMatricula(matriculaIngresada);

            if (datos != null) {
                idAlumnoSeleccionado = (int) datos[0]; 
                vista.txtNombreAlumno.setText((String) datos[1]);
                vista.txtCarrera.setText((String) datos[2]);
                vista.txtCentroUniversitario.setText((String) datos[3]);
            } else {
                JOptionPane.showMessageDialog(vista, "No se encontró ningún alumno con la matrícula: " + matriculaIngresada, "No encontrado", JOptionPane.ERROR_MESSAGE);
                limpiarCamposDescriptivos();
                idAlumnoSeleccionado = -1;
            }
        }

        // --- TRANSACCIÓN, CANDADO Y GENERACIÓN DE PDF ---
        if (e.getSource() == vista.btnProcesarPago) {
            if (vista.txtMatriculaAlumno.getText().trim().isEmpty() || vista.txtMonto.getText().trim().isEmpty() || vista.txtRecibo.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos de pago son obligatorios.", "Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            if (idAlumnoSeleccionado == -1) {
                JOptionPane.showMessageDialog(vista, "Busque y verifique una matrícula válida antes de procesar el pago.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                double monto = Double.parseDouble(vista.txtMonto.getText().trim());
                String recibo = vista.txtRecibo.getText().trim();

                // 1. Candado Lógico: Verificamos cuánto debe el alumno llamando al método del DAO
                double deudaActual = dao.obtenerSaldoDeudor(idAlumnoSeleccionado);
                
                if (monto <= 0) {
                    JOptionPane.showMessageDialog(vista, "El monto del pago debe ser mayor a cero.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // AQUÍ ESTÁ EL CANDADO FUNCIONANDO
                if (monto > deudaActual) {
                    JOptionPane.showMessageDialog(vista, "Operación denegada. El monto a pagar ($" + monto + ") supera la deuda actual del alumno ($" + deudaActual + ").", "Pago Inválido", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                // 2. Ejecutar transacción ACID
                if (dao.procesarPagoColegiatura(idAlumnoSeleccionado, monto, recibo)) {
                    // SI TIENE ÉXITO, MOSTRARÁ ESTE NUEVO MENSAJE
                    JOptionPane.showMessageDialog(vista, "Transacción exitosa. Pago registrado. Deuda restante: $" + (deudaActual - monto));
                    
                    String matricula = vista.txtMatriculaAlumno.getText().trim();
                    String nombre = vista.txtNombreAlumno.getText();
                    String carrera = vista.txtCarrera.getText();
                    
                    generarReciboPDF(matricula, nombre, carrera, monto, recibo);
                    limpiarFormularioCompleto();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error en la transacción. Se aplicó Rollback de seguridad.", "Fallo ACID", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Verifique que el Monto tenga formato numérico válido.", "Error de Sintaxis", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generarReciboPDF(String matricula, String nombre, String carrera, double monto, String recibo) {
        String ruta = System.getProperty("user.home") + "/Desktop/Recibo_" + recibo + ".pdf";
        Document documento = new Document();
        
        try {
            PdfWriter.getInstance(documento, new FileOutputStream(ruta));
            documento.open();
            
            String rutaLogo = "C:\\Users\\UAEM\\Documents\\NetBeansProjects\\sistema-gestion-universitaria\\LogoUAEMEX.png";
            try {
                Image imagenLogo = Image.getInstance(rutaLogo);
                imagenLogo.scaleToFit(100, 100); 
                imagenLogo.setAlignment(Element.ALIGN_CENTER); 
                documento.add(imagenLogo);
            } catch (Exception ex) {
                System.err.println("No se pudo cargar el logo del recibo.");
            }

            Paragraph titulo = new Paragraph("UNIVERSIDAD AUTÓNOMA DEL ESTADO DE MÉXICO");
            titulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(titulo);
            
            Paragraph subtitulo = new Paragraph("Recibo de Pago - Sistema de Gestión Universitaria");
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            documento.add(subtitulo);
            
            documento.add(new Paragraph(" "));
            documento.add(new Paragraph("========================================================"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            documento.add(new Paragraph("Folio de Recibo: " + recibo));
            documento.add(new Paragraph("Fecha y Hora: " + sdf.format(new Date())));
            documento.add(new Paragraph("Matrícula: " + matricula));
            documento.add(new Paragraph("Nombre del Alumno: " + nombre));
            documento.add(new Paragraph("Carrera: " + carrera));
            documento.add(new Paragraph("Monto Pagado: $" + monto + " MXN"));
            
            documento.add(new Paragraph("========================================================"));
            documento.add(new Paragraph(" "));
            
            Paragraph piePagina = new Paragraph("Este documento es un comprobante de pago oficial.");
            piePagina.setAlignment(Element.ALIGN_CENTER);
            documento.add(piePagina);
            
            documento.close();
            JOptionPane.showMessageDialog(vista, "Recibo generado en el Escritorio: Recibo_" + recibo + ".pdf");
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(vista, "Error al generar el recibo PDF: " + ex.getMessage(), "Error PDF", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCamposDescriptivos() {
        vista.txtNombreAlumno.setText("");
        vista.txtCarrera.setText("");
        vista.txtCentroUniversitario.setText("");
    }

    private void limpiarFormularioCompleto() {
        vista.txtMatriculaAlumno.setText("");
        vista.txtMonto.setText("");
        vista.txtRecibo.setText("");
        limpiarCamposDescriptivos();
        idAlumnoSeleccionado = -1; 
    }
}