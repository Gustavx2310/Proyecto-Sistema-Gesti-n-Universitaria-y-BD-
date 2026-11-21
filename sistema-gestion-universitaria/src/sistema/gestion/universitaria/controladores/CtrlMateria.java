package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.CarreraDAO;
import sistema.gestion.universitaria.dao.MateriaDAO;
import sistema.gestion.universitaria.modelo.Carrera;
import sistema.gestion.universitaria.modelo.Materia;
import sistema.gestion.universitaria.vistas.FrmGestorMaterias;

public class CtrlMateria implements ActionListener {

    private final FrmGestorMaterias vista;
    private final MateriaDAO daoMateria;
    private final CarreraDAO daoCarrera;
    private DefaultTableModel modeloTabla;
    private List<Carrera> listaCarreras; 

    public CtrlMateria(FrmGestorMaterias vista, MateriaDAO daoMateria, CarreraDAO daoCarrera) {
        this.vista = vista;
        this.daoMateria = daoMateria;
        this.daoCarrera = daoCarrera;

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);
        
        this.vista.tablaMaterias.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Gestor de Materias");
        cargarCarrerasEnCombo();
        limpiarFormulario();
        cargarTabla();
    }
    
    private void cargarCarrerasEnCombo() {
        vista.cbxCarrera.removeAllItems();
        listaCarreras = daoCarrera.obtenerTodas();
        for (Carrera c : listaCarreras) {
            vista.cbxCarrera.addItem(c.getNombreCarrera());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- BOTÓN GUARDAR ---
        if (e.getSource() == vista.btnGuardar) {
            if (vista.txtNombreMateria.getText().trim().isEmpty() || vista.txtCreditos.getText().trim().isEmpty() || vista.cbxCarrera.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(vista, "Datos incompletos.");
                return;
            }

            try {
                int creditos = Integer.parseInt(vista.txtCreditos.getText().trim());
                int indexCarrera = vista.cbxCarrera.getSelectedIndex();
                int idCarreraSeleccionada = listaCarreras.get(indexCarrera).getIdCarrera();

                Materia materia = new Materia();
                materia.setIdCarrera(idCarreraSeleccionada);
                materia.setNombreMateria(vista.txtNombreMateria.getText().trim());
                materia.setCreditos(creditos);
                materia.setEstatus(vista.cbxEstatus.getSelectedItem().toString());

                if (daoMateria.registrar(materia)) {
                    JOptionPane.showMessageDialog(vista, "Materia registrada con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al registrar materia.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Los créditos deben ser un número válido.");
            }
        }

        // --- BOTÓN LIMPIAR ---
        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        }
        
        // --- BOTÓN MODIFICAR ---
        if (e.getSource() == vista.btnModificar) {
            if (vista.txtIdMateria.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione una materia de la tabla primero.");
                return;
            }

            try {
                int creditos = Integer.parseInt(vista.txtCreditos.getText().trim());
                int indexCarrera = vista.cbxCarrera.getSelectedIndex();
                int idCarreraSeleccionada = listaCarreras.get(indexCarrera).getIdCarrera();

                Materia materia = new Materia();
                materia.setIdMateria(Integer.parseInt(vista.txtIdMateria.getText()));
                materia.setIdCarrera(idCarreraSeleccionada);
                materia.setNombreMateria(vista.txtNombreMateria.getText().trim());
                materia.setCreditos(creditos);
                materia.setEstatus(vista.cbxEstatus.getSelectedItem().toString());

                if (daoMateria.modificar(materia)) {
                    JOptionPane.showMessageDialog(vista, "Materia modificada con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al modificar la materia.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Los créditos deben ser un número válido.");
            }
        }

        // --- BOTÓN ELIMINAR ---
        if (e.getSource() == vista.btnEliminar) {
            if (vista.txtIdMateria.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione una materia de la tabla primero.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar esta materia?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtIdMateria.getText());
                if (daoMateria.eliminar(id)) {
                    JOptionPane.showMessageDialog(vista, "Materia eliminada con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "No se puede eliminar la materia.");
                }
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtIdMateria.setText("");
        vista.txtNombreMateria.setText("");
        vista.txtCreditos.setText("");
        if (vista.cbxCarrera.getItemCount() > 0) {
            vista.cbxCarrera.setSelectedIndex(0);
        }
        vista.cbxEstatus.setSelectedIndex(0);
    }

    private void cargarTabla() {
        List<Materia> lista = daoMateria.obtenerTodas();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("ID Carrera");
        modeloTabla.addColumn("Nombre Materia");
        modeloTabla.addColumn("Créditos");
        modeloTabla.addColumn("Estatus");

        for (Materia m : lista) {
            Object[] fila = new Object[5];
            fila[0] = m.getIdMateria();
            fila[1] = m.getIdCarrera();
            fila[2] = m.getNombreMateria();
            fila[3] = m.getCreditos();
            fila[4] = m.getEstatus();
            modeloTabla.addRow(fila);
        }
        vista.tablaMaterias.setModel(modeloTabla);
    }
    
    private void llenarCamposDesdeTabla() {
        int fila = vista.tablaMaterias.getSelectedRow();
        if (fila != -1) {
            vista.txtIdMateria.setText(vista.tablaMaterias.getValueAt(fila, 0).toString());
            vista.txtNombreMateria.setText(vista.tablaMaterias.getValueAt(fila, 2).toString());
            vista.txtCreditos.setText(vista.tablaMaterias.getValueAt(fila, 3).toString());
            vista.cbxEstatus.setSelectedItem(vista.tablaMaterias.getValueAt(fila, 4).toString());
            
            int idCarreraTabla = Integer.parseInt(vista.tablaMaterias.getValueAt(fila, 1).toString());
            for (int i = 0; i < listaCarreras.size(); i++) {
                if (listaCarreras.get(i).getIdCarrera() == idCarreraTabla) {
                    vista.cbxCarrera.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}