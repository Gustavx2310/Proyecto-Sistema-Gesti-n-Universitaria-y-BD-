package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.DocenteDAO;
import sistema.gestion.universitaria.dao.GrupoDAO;
import sistema.gestion.universitaria.dao.MateriaDAO;
import sistema.gestion.universitaria.modelo.Docente;
import sistema.gestion.universitaria.modelo.Grupo;
import sistema.gestion.universitaria.modelo.Materia;
import sistema.gestion.universitaria.vistas.FrmGestorGrupos;

public class CtrlGrupo implements ActionListener {

    private final FrmGestorGrupos vista;
    private final GrupoDAO daoGrupo;
    private final MateriaDAO daoMateria;
    private final DocenteDAO daoDocente;
    private DefaultTableModel modeloTabla;

    private List<Materia> listaMaterias;
    private List<Docente> listaDocentes;

    public CtrlGrupo(FrmGestorGrupos vista, GrupoDAO daoGrupo) {
        this.vista = vista;
        this.daoGrupo = daoGrupo;
        
        this.daoMateria = new MateriaDAO();
        this.daoDocente = new DocenteDAO();

        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnLimpiar.addActionListener(this);

        this.vista.tablaGrupos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                llenarCamposDesdeTabla();
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Gestor de Grupos");
        cargarComboBoxes();
        limpiarFormulario();
        cargarTabla();
    }

    private void cargarComboBoxes() {
        vista.cbxMateria.removeAllItems();
        vista.cbxDocente.removeAllItems();

        vista.cbxMateria.addItem("Seleccione una Materia...");
        vista.cbxDocente.addItem("Seleccione un Docente...");

        listaMaterias = daoMateria.obtenerTodas();
        listaDocentes = daoDocente.obtenerTodos();

        for (Materia m : listaMaterias) {
            vista.cbxMateria.addItem(m.getNombreMateria());
        }

        for (Docente d : listaDocentes) {
            vista.cbxDocente.addItem(d.getNombre() + " " + d.getApellidoPaterno());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnGuardar) {
            if (vista.cbxMateria.getSelectedIndex() == 0 || vista.cbxDocente.getSelectedIndex() == 0 ||
                vista.txtPeriodo.getText().trim().isEmpty() || vista.txtClave.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios. Seleccione Materia y Docente.");
                return;
            }

            Grupo grupo = new Grupo();
            grupo.setClaveGrupo(vista.txtClave.getText().trim());
            grupo.setIdMateria(listaMaterias.get(vista.cbxMateria.getSelectedIndex() - 1).getIdMateria());
            grupo.setIdDocente(listaDocentes.get(vista.cbxDocente.getSelectedIndex() - 1).getIdPersona());
            grupo.setPeriodoEscolar(vista.txtPeriodo.getText().trim());

            if (daoGrupo.registrar(grupo)) {
                JOptionPane.showMessageDialog(vista, "Grupo registrado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar grupo.");
            }
        }

        if (e.getSource() == vista.btnLimpiar) {
            limpiarFormulario();
        }

        if (e.getSource() == vista.btnModificar) {
            if (vista.txtIdGrupo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un grupo de la tabla primero.");
                return;
            }
            if (vista.cbxMateria.getSelectedIndex() == 0 || vista.cbxDocente.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una Materia y un Docente válidos.");
                return;
            }

            Grupo grupo = new Grupo();
            grupo.setIdGrupo(Integer.parseInt(vista.txtIdGrupo.getText()));
            grupo.setClaveGrupo(vista.txtClave.getText().trim());
            grupo.setIdMateria(listaMaterias.get(vista.cbxMateria.getSelectedIndex() - 1).getIdMateria());
            grupo.setIdDocente(listaDocentes.get(vista.cbxDocente.getSelectedIndex() - 1).getIdPersona());
            grupo.setPeriodoEscolar(vista.txtPeriodo.getText().trim());

            if (daoGrupo.modificar(grupo)) {
                JOptionPane.showMessageDialog(vista, "Grupo modificado con éxito.");
                limpiarFormulario();
                cargarTabla();
            } else {
                JOptionPane.showMessageDialog(vista, "Error al modificar grupo.");
            }
        }

        if (e.getSource() == vista.btnEliminar) {
            if (vista.txtIdGrupo.getText().isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Seleccione un grupo de la tabla primero.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Está seguro de eliminar este grupo?", "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                int id = Integer.parseInt(vista.txtIdGrupo.getText());
                if (daoGrupo.eliminar(id)) {
                    JOptionPane.showMessageDialog(vista, "Grupo eliminado con éxito.");
                    limpiarFormulario();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al eliminar grupo.");
                }
            }
        }
    }

    private void limpiarFormulario() {
        vista.txtIdGrupo.setText("");
        vista.txtClave.setText("");
        vista.txtPeriodo.setText("");
        if (vista.cbxMateria.getItemCount() > 0) vista.cbxMateria.setSelectedIndex(0);
        if (vista.cbxDocente.getItemCount() > 0) vista.cbxDocente.setSelectedIndex(0);
    }

    private void cargarTabla() {
        List<Grupo> lista = daoGrupo.obtenerTodos();
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Clave");
        modeloTabla.addColumn("Materia");
        modeloTabla.addColumn("Docente");
        modeloTabla.addColumn("Periodo");

        for (Grupo g : lista) {
            Object[] fila = new Object[5];
            fila[0] = g.getIdGrupo();
            fila[1] = g.getClaveGrupo();
            fila[2] = g.getNombreMateria(); 
            fila[3] = g.getNombreDocente();
            fila[4] = g.getPeriodoEscolar();
            modeloTabla.addRow(fila);
        }
        vista.tablaGrupos.setModel(modeloTabla);
    }
    
    private void llenarCamposDesdeTabla() {
        int fila = vista.tablaGrupos.getSelectedRow();
        if (fila != -1) {
            vista.txtIdGrupo.setText(vista.tablaGrupos.getValueAt(fila, 0).toString());
            vista.txtClave.setText(vista.tablaGrupos.getValueAt(fila, 1).toString());
            
            String materiaTabla = vista.tablaGrupos.getValueAt(fila, 2).toString();
            vista.cbxMateria.setSelectedItem(materiaTabla);
            
            String docenteTabla = vista.tablaGrupos.getValueAt(fila, 3).toString();
            vista.cbxDocente.setSelectedItem(docenteTabla);
            
            vista.txtPeriodo.setText(vista.tablaGrupos.getValueAt(fila, 4).toString());
        }
    }
}