package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.EspacioUniversitarioDAO;
import sistema.gestion.universitaria.modelo.EspacioUniversitario;
import sistema.gestion.universitaria.vistas.FrmGestorEspacios;

public class CtrlEspacioUniversitario implements ActionListener {

    private final FrmGestorEspacios vista;
    private final EspacioUniversitarioDAO dao;
    private DefaultTableModel modeloTabla;

    public CtrlEspacioUniversitario(FrmGestorEspacios vista, EspacioUniversitarioDAO dao) {
        this.vista = vista;
        this.dao = dao;

        // Agregar los ActionListeners a los botones de la vista
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        
        // Agregar listener para cuando hagan clic en una fila de la tabla
        this.vista.tablaEspacios.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Gestor de Espacios Universitarios");
        limpiarFormulario();
        cargarTabla();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- BOTÓN GUARDAR ---
        if (e.getSource() == vista.btnGuardar) {
            if (vista.txtNombre.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "El nombre es obligatorio.");
                return;
            }

            EspacioUniversitario espacio = new EspacioUniversitario();
            espacio.setNombreOficial(vista.txtNombre.getText().trim());
            espacio.setEstatus(vista.cbxEstatus.getSelectedItem().toString());

            if (dao.registrar(espacio)) {
                JOptionPane.showMessageDialog(vista, "Espacio registrado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar.");
            }
        }

        // --- BOTÓN LIMPIAR ---
        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        }

        // --- BOTÓN MODIFICAR ---
        if (e.getSource() == vista.btnModificar) {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un registro de la tabla primero.");
                return;
            }

            EspacioUniversitario espacio = new EspacioUniversitario();
            espacio.setIdEspacio(Integer.parseInt(vista.txtId.getText()));
            espacio.setNombreOficial(vista.txtNombre.getText().trim());
            espacio.setEstatus(vista.cbxEstatus.getSelectedItem().toString());

            if (dao.modificar(espacio)) {
                JOptionPane.showMessageDialog(vista, "Espacio modificado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al modificar.");
            }
        }

        // --- BOTÓN ELIMINAR ---
        if (e.getSource() == vista.btnEliminar) {
            if (vista.txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un registro de la tabla primero.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este registro?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if(confirmacion == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtId.getText());
                if (dao.eliminar(id)) {
                    JOptionPane.showMessageDialog(vista, "Espacio eliminado con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se puede eliminar porque tiene carreras asociadas.");
                }
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtId.setText("");
        vista.txtNombre.setText("");
        vista.cbxEstatus.setSelectedIndex(0);
    }

    private void cargarTabla() {
        List<EspacioUniversitario> lista = dao.obtenerTodos();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre Oficial");
        modeloTabla.addColumn("Estatus");

        for (EspacioUniversitario esp : lista) {
            Object[] fila = new Object[3];
            fila[0] = esp.getIdEspacio();
            fila[1] = esp.getNombreOficial();
            fila[2] = esp.getEstatus();
            modeloTabla.addRow(fila);
        }
        vista.tablaEspacios.setModel(modeloTabla);
    }
    
    private void llenarCamposDesdeTabla() {
        int fila = vista.tablaEspacios.getSelectedRow();
        if (fila != -1) {
            vista.txtId.setText(vista.tablaEspacios.getValueAt(fila, 0).toString());
            vista.txtNombre.setText(vista.tablaEspacios.getValueAt(fila, 1).toString());
            vista.cbxEstatus.setSelectedItem(vista.tablaEspacios.getValueAt(fila, 2).toString());
        }
    }
}