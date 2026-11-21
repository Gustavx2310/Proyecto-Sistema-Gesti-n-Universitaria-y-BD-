package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.AlumnoDAO;
import sistema.gestion.universitaria.modelo.Alumno;
import sistema.gestion.universitaria.vistas.FrmGestorAlumnos;
import java.io.FileOutputStream;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Element;

public class CtrlAlumno implements ActionListener {

    private final FrmGestorAlumnos vista;
    private final AlumnoDAO dao;
    private DefaultTableModel modeloTabla;

    public CtrlAlumno(FrmGestorAlumnos vista, AlumnoDAO dao) {
        this.vista = vista;
        this.dao = dao;

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        this.vista.btnReporte.addActionListener(this);
        
        this.vista.tablaAlumnos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Gestor de Alumnos");
        
        // Bloqueamos la edición del ID para proteger la llave primaria de la base de datos
        vista.txtIdPersona.setEditable(false);
        
        limpiarFormulario();
        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        // --- GUARDAR ---
        if (e.getSource() == vista.btnGuardar) {
            if (vista.txtNombre.getText().trim().isEmpty() || vista.txtMatricula.getText().trim().isEmpty() || vista.txtIdCarrera.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El nombre, matrícula y el ID de Carrera son obligatorios.");
                return;
            }

            try {
                Alumno alumno = new Alumno();
                alumno.setNombre(vista.txtNombre.getText().trim());
                alumno.setApellidoPaterno(vista.txtApellidoPaterno.getText().trim());
                alumno.setApellidoMaterno(vista.txtApellidoMaterno.getText().trim());
                alumno.setMatricula(vista.txtMatricula.getText().trim());
                alumno.setEstatusAcademico(vista.cbxEstatus.getSelectedItem().toString());
                
                // Captura dinámica de la carrera en lugar de usar 1 por defecto
                alumno.setIdCarreraActual(Integer.parseInt(vista.txtIdCarrera.getText().trim()));
                alumno.setSemestreActual(1); // Mantenemos semestre fijo por ahora para simplificar

                if (dao.registrar(alumno)) {
                    JOptionPane.showMessageDialog(vista, "Alumno registrado con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al registrar alumno.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El ID de Carrera debe ser un número entero.");
            }
        }

        // --- LIMPIAR ---
        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        }

        // --- MODIFICAR (AQUÍ SE DISPARA EL TRIGGER) ---
        if (e.getSource() == vista.btnModificar) {
            if (vista.txtIdPersona.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla primero.");
                return;
            }

            try {
                Alumno alumno = new Alumno();
                alumno.setIdPersona(Integer.parseInt(vista.txtIdPersona.getText()));
                alumno.setNombre(vista.txtNombre.getText().trim());
                alumno.setApellidoPaterno(vista.txtApellidoPaterno.getText().trim());
                alumno.setApellidoMaterno(vista.txtApellidoMaterno.getText().trim());
                alumno.setMatricula(vista.txtMatricula.getText().trim());
                alumno.setEstatusAcademico(vista.cbxEstatus.getSelectedItem().toString());
                
                // Captura dinámica de la nueva carrera
                alumno.setIdCarreraActual(Integer.parseInt(vista.txtIdCarrera.getText().trim()));
                alumno.setSemestreActual(1);

                if (dao.modificar(alumno)) {
                    JOptionPane.showMessageDialog(vista, "Alumno modificado con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar alumno.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "El ID de Carrera debe ser un número entero.");
            }
        }

        // --- ELIMINAR ---
        if (e.getSource() == vista.btnEliminar) {
            if (vista.txtIdPersona.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla primero.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este alumno?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtIdPersona.getText());
                if (dao.eliminar(id)) {
                    JOptionPane.showMessageDialog(vista, "Alumno eliminado con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar alumno.");
                }
            }
        }

        // --- REPORTE PDF ---
        if (e.getSource() == vista.btnReporte) {
            String ruta = System.getProperty("user.home") + "/Desktop/Reporte_Alumnos.pdf";
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
                    System.err.println("No se pudo cargar el logo: " + ex.getMessage());
                }

                Paragraph titulo = new Paragraph("UAEMEX");
                titulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(titulo);
                
                Paragraph subtitulo = new Paragraph("Reporte del Gestor de Alumnos - CU Atlacomulco");
                subtitulo.setAlignment(Element.ALIGN_CENTER);
                documento.add(subtitulo);
                
                documento.add(new Paragraph(" ")); 
                documento.add(new Paragraph(" "));
                
                PdfPTable tablaPdf = new PdfPTable(vista.tablaAlumnos.getColumnCount());
                
                for (int i = 0; i < vista.tablaAlumnos.getColumnCount(); i++) {
                    // Evitamos agregar el encabezado de la columna oculta (ID) al PDF (Opcional)
                    if (i != 0) {
                        tablaPdf.addCell(vista.tablaAlumnos.getColumnName(i));
                    }
                }
                
                for (int i = 0; i < vista.tablaAlumnos.getRowCount(); i++) {
                    for (int j = 0; j < vista.tablaAlumnos.getColumnCount(); j++) {
                        // Evitamos agregar los datos de la columna oculta al PDF
                        if (j != 0) {
                            Object valor = vista.tablaAlumnos.getValueAt(i, j);
                            tablaPdf.addCell(valor != null ? valor.toString() : "");
                        }
                    }
                }
                
                documento.add(tablaPdf);
                documento.close();
                JOptionPane.showMessageDialog(vista, "¡PDF generado en tu Escritorio con el logo de la UAEMEX!");
                
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "Error al crear PDF: " + ex.getMessage());
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtIdPersona.setText("");
        vista.txtNombre.setText("");
        vista.txtApellidoPaterno.setText("");
        vista.txtApellidoMaterno.setText("");
        vista.txtMatricula.setText("");
        if (vista.txtIdCarrera != null) vista.txtIdCarrera.setText("");
        if (vista.cbxEstatus.getItemCount() > 0) {
            vista.cbxEstatus.setSelectedIndex(0);
        }
    }

    private void cargarTabla() {
        List<Alumno> lista = dao.obtenerTodos();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID"); // Columna 0 (se ocultará visualmente)
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("A. Paterno");
        modeloTabla.addColumn("A. Materno");
        modeloTabla.addColumn("Matrícula");
        modeloTabla.addColumn("Estatus");
        modeloTabla.addColumn("IdCarrera");

        for (Alumno a : lista) {
            Object[] fila = new Object[7];
            fila[0] = a.getIdPersona();
            fila[1] = a.getNombre();
            fila[2] = a.getApellidoPaterno();
            fila[3] = a.getApellidoMaterno();
            fila[4] = a.getMatricula();
            fila[5] = a.getEstatusAcademico();
            fila[6] = a.getIdCarreraActual() != null ? a.getIdCarreraActual() : "N/A";
            modeloTabla.addRow(fila);
        }
        vista.tablaAlumnos.setModel(modeloTabla);
        
        // --- OCULTAR COLUMNA ID (Índice 0) ---
        vista.tablaAlumnos.getColumnModel().getColumn(0).setMinWidth(0);
        vista.tablaAlumnos.getColumnModel().getColumn(0).setMaxWidth(0);
        vista.tablaAlumnos.getColumnModel().getColumn(0).setWidth(0);
        vista.tablaAlumnos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    
    private void llenarCamposDesdeTabla() {
        int fila = vista.tablaAlumnos.getSelectedRow();
        if (fila != -1) {
            vista.txtIdPersona.setText(vista.tablaAlumnos.getValueAt(fila, 0).toString());
            vista.txtNombre.setText(vista.tablaAlumnos.getValueAt(fila, 1).toString());
            vista.txtApellidoPaterno.setText(vista.tablaAlumnos.getValueAt(fila, 2) != null ? vista.tablaAlumnos.getValueAt(fila, 2).toString() : "");
            vista.txtApellidoMaterno.setText(vista.tablaAlumnos.getValueAt(fila, 3) != null ? vista.tablaAlumnos.getValueAt(fila, 3).toString() : "");
            vista.txtMatricula.setText(vista.tablaAlumnos.getValueAt(fila, 4).toString());
            vista.cbxEstatus.setSelectedItem(vista.tablaAlumnos.getValueAt(fila, 5).toString());
            
            Object idCarreraObj = vista.tablaAlumnos.getValueAt(fila, 6);
            if(idCarreraObj != null && !idCarreraObj.toString().equals("N/A")){
                vista.txtIdCarrera.setText(idCarreraObj.toString());
            } else {
                vista.txtIdCarrera.setText("");
            }
        }
    }
}