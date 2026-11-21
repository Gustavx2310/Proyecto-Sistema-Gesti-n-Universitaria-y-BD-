package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.DocenteDAO;
import sistema.gestion.universitaria.modelo.Docente;
import sistema.gestion.universitaria.vistas.FrmGestorDocentes;

public class CtrlDocente implements ActionListener {

    private final FrmGestorDocentes vista;
    private final DocenteDAO dao;
    private DefaultTableModel modeloTabla;

    public CtrlDocente(FrmGestorDocentes vista, DocenteDAO dao) {
        this.vista = vista;
        this.dao = dao;

        // Escuchadores de clics para los botones
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        
        // Escuchador para cuando den clic en una fila de la tabla
        this.vista.tablaDocentes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Gestor de Docentes");
        limpiarFormulario();
        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- BOTÓN GUARDAR ---
        if (e.getSource() == vista.btnGuardar) {
            if (vista.txtNombre.getText().trim().isEmpty() || vista.txtNumeroEmpleado.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El nombre y número de empleado son obligatorios.");
                return;
            }

            Docente docente = new Docente();
            docente.setNombre(vista.txtNombre.getText().trim());
            docente.setApellidoPaterno(vista.txtApellidoPaterno.getText().trim());
            docente.setApellidoMaterno(vista.txtApellidoMaterno.getText().trim());
            docente.setNumeroEmpleado(vista.txtNumeroEmpleado.getText().trim());
            docente.setEstatusLaboral(vista.cbxEstatusLaboral.getSelectedItem().toString());

            if (dao.registrar(docente)) {
                JOptionPane.showMessageDialog(vista, "Docente registrado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar docente.");
            }
        }

        // --- BOTÓN LIMPIAR ---
        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        }

        // --- BOTÓN MODIFICAR ---
        if (e.getSource() == vista.btnModificar) {
            if (vista.txtIdPersona.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un docente de la tabla primero.");
                return;
            }

            Docente docente = new Docente();
            docente.setIdPersona(Integer.parseInt(vista.txtIdPersona.getText()));
            docente.setNombre(vista.txtNombre.getText().trim());
            docente.setApellidoPaterno(vista.txtApellidoPaterno.getText().trim());
            docente.setApellidoMaterno(vista.txtApellidoMaterno.getText().trim());
            docente.setNumeroEmpleado(vista.txtNumeroEmpleado.getText().trim());
            docente.setEstatusLaboral(vista.cbxEstatusLaboral.getSelectedItem().toString());

            if (dao.modificar(docente)) {
                JOptionPane.showMessageDialog(vista, "Docente modificado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al modificar docente.");
            }
        }

        // --- BOTÓN ELIMINAR ---
        if (e.getSource() == vista.btnEliminar) {
            if (vista.txtIdPersona.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un docente de la tabla primero.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este docente?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtIdPersona.getText());
                if (dao.eliminar(id)) {
                    JOptionPane.showMessageDialog(vista, "Docente eliminado con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar docente. Puede que tenga grupos asignados.");
                }
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtIdPersona.setText("");
        vista.txtNombre.setText("");
        vista.txtApellidoPaterno.setText("");
        vista.txtApellidoMaterno.setText("");
        vista.txtNumeroEmpleado.setText("");
        if (vista.cbxEstatusLaboral.getItemCount() > 0) {
            vista.cbxEstatusLaboral.setSelectedIndex(0);
        }
    }

    private void cargarTabla() {
        List<Docente> lista = dao.obtenerTodos();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("A. Paterno");
        modeloTabla.addColumn("A. Materno");
        modeloTabla.addColumn("No. Empleado");
        modeloTabla.addColumn("Estatus");

        for (Docente d : lista) {
            Object[] fila = new Object[6];
            fila[0] = d.getIdPersona();
            fila[1] = d.getNombre();
            fila[2] = d.getApellidoPaterno();
            fila[3] = d.getApellidoMaterno();
            fila[4] = d.getNumeroEmpleado();
            fila[5] = d.getEstatusLaboral();
            modeloTabla.addRow(fila);
        }
        vista.tablaDocentes.setModel(modeloTabla);
    }
    
    private void llenarCamposDesdeTabla() {
        int fila = vista.tablaDocentes.getSelectedRow();
        if (fila != -1) {
            vista.txtIdPersona.setText(vista.tablaDocentes.getValueAt(fila, 0).toString());
            vista.txtNombre.setText(vista.tablaDocentes.getValueAt(fila, 1).toString());
            
            // Validamos nulos por si alguien no tiene apellido materno o paterno en la base de datos
            vista.txtApellidoPaterno.setText(vista.tablaDocentes.getValueAt(fila, 2) != null ? vista.tablaDocentes.getValueAt(fila, 2).toString() : "");
            vista.txtApellidoMaterno.setText(vista.tablaDocentes.getValueAt(fila, 3) != null ? vista.tablaDocentes.getValueAt(fila, 3).toString() : "");
            
            vista.txtNumeroEmpleado.setText(vista.tablaDocentes.getValueAt(fila, 4).toString());
            vista.cbxEstatusLaboral.setSelectedItem(vista.tablaDocentes.getValueAt(fila, 5).toString());
        }
    }
}