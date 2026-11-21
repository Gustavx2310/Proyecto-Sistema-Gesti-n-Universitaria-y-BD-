package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.CarreraDAO;
import sistema.gestion.universitaria.dao.EspacioUniversitarioDAO;
import sistema.gestion.universitaria.modelo.Carrera;
import sistema.gestion.universitaria.modelo.EspacioUniversitario;
import sistema.gestion.universitaria.vistas.FrmGestorCarreras;

public class CtrlCarrera implements ActionListener {

    private final FrmGestorCarreras vista;
    private final CarreraDAO daoCarrera;
    private final EspacioUniversitarioDAO daoEspacio;
    private DefaultTableModel modeloTabla;
    private List<EspacioUniversitario> listaEspacios; // Para saber qué ID tiene el espacio seleccionado

    public CtrlCarrera(FrmGestorCarreras vista, CarreraDAO daoCarrera, EspacioUniversitarioDAO daoEspacio) {
        this.vista = vista;
        this.daoCarrera = daoCarrera;
        this.daoEspacio = daoEspacio;

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        
        this.vista.tablaCarreras.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Gestor de Carreras");
        cargarEspaciosEnCombo();
        limpiarFormulario();
        cargarTabla();
    }
    
    private void cargarEspaciosEnCombo() {
        vista.cbxEspacio.removeAllItems();
        listaEspacios = daoEspacio.obtenerTodos();
        for (EspacioUniversitario esp : listaEspacios) {
            // Mostramos el nombre en el combo
            vista.cbxEspacio.addItem(esp.getNombreOficial());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- BOTÓN GUARDAR ---
        if (e.getSource() == vista.btnGuardar) {
            if (vista.txtNombreCarrera.getText().trim().isEmpty() || vista.cbxEspacio.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(vista, "Datos incompletos.");
                return;
            }

            // Obtener el ID del espacio seleccionado
            int indexEspacio = vista.cbxEspacio.getSelectedIndex();
            int idEspacioSeleccionado = listaEspacios.get(indexEspacio).getIdEspacio();

            Carrera carrera = new Carrera();
            carrera.setIdEspacio(idEspacioSeleccionado);
            carrera.setNombreCarrera(vista.txtNombreCarrera.getText().trim());
            carrera.setEstatus(vista.cbxEstatus.getSelectedItem().toString());

            if (daoCarrera.registrar(carrera)) {
                JOptionPane.showMessageDialog(vista, "Carrera registrada con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar carrera.");
            }
        }

        // --- BOTÓN LIMPIAR ---
        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        }
        
        // --- BOTÓN MODIFICAR ---
        if (e.getSource() == vista.btnModificar) {
            if (vista.txtIdCarrera.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione una carrera de la tabla primero.");
                return;
            }

            int indexEspacio = vista.cbxEspacio.getSelectedIndex();
            int idEspacioSeleccionado = listaEspacios.get(indexEspacio).getIdEspacio();

            Carrera carrera = new Carrera();
            carrera.setIdCarrera(Integer.parseInt(vista.txtIdCarrera.getText()));
            carrera.setIdEspacio(idEspacioSeleccionado);
            carrera.setNombreCarrera(vista.txtNombreCarrera.getText().trim());
            carrera.setEstatus(vista.cbxEstatus.getSelectedItem().toString());

            if (daoCarrera.modificar(carrera)) {
                JOptionPane.showMessageDialog(vista, "Carrera modificada con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al modificar la carrera.");
            }
        }

        // --- BOTÓN ELIMINAR ---
        if (e.getSource() == vista.btnEliminar) {
            if (vista.txtIdCarrera.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione una carrera de la tabla primero.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar esta carrera?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtIdCarrera.getText());
                if (daoCarrera.eliminar(id)) {
                    JOptionPane.showMessageDialog(vista, "Carrera eliminada con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se puede eliminar porque tiene materias asociadas.");
                }
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtIdCarrera.setText("");
        vista.txtNombreCarrera.setText("");
        if (vista.cbxEspacio.getItemCount() > 0) {
            vista.cbxEspacio.setSelectedIndex(0);
        }
        vista.cbxEstatus.setSelectedIndex(0);
    }

    private void cargarTabla() {
        List<Carrera> lista = daoCarrera.obtenerTodas();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("ID Espacio");
        modeloTabla.addColumn("Nombre Carrera");
        modeloTabla.addColumn("Estatus");

        for (Carrera c : lista) {
            Object[] fila = new Object[4];
            fila[0] = c.getIdCarrera();
            fila[1] = c.getIdEspacio();
            fila[2] = c.getNombreCarrera();
            fila[3] = c.getEstatus();
            modeloTabla.addRow(fila);
        }
        vista.tablaCarreras.setModel(modeloTabla);
    }
    
    private void llenarCamposDesdeTabla() {
        int fila = vista.tablaCarreras.getSelectedRow();
        if (fila != -1) {
            vista.txtIdCarrera.setText(vista.tablaCarreras.getValueAt(fila, 0).toString());
            vista.txtNombreCarrera.setText(vista.tablaCarreras.getValueAt(fila, 2).toString());
            vista.cbxEstatus.setSelectedItem(vista.tablaCarreras.getValueAt(fila, 3).toString());
            
            // Lógica para seleccionar el espacio correcto en el combo
            int idEspacioTabla = Integer.parseInt(vista.tablaCarreras.getValueAt(fila, 1).toString());
            for (int i = 0; i < listaEspacios.size(); i++) {
                if (listaEspacios.get(i).getIdEspacio() == idEspacioTabla) {
                    vista.cbxEspacio.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}