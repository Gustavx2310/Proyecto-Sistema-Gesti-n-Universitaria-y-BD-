package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import sistema.gestion.universitaria.dao.AlumnoDAO;
import sistema.gestion.universitaria.dao.AlumnoGrupoDAO;
import sistema.gestion.universitaria.dao.GrupoDAO;
import sistema.gestion.universitaria.modelo.Alumno;
import sistema.gestion.universitaria.modelo.AlumnoGrupo;
import sistema.gestion.universitaria.modelo.Grupo;
import sistema.gestion.universitaria.vistas.FrmInscripciones;

public class CtrlInscripcion implements ActionListener {

    private final FrmInscripciones vista;
    private final AlumnoGrupoDAO daoInscripcion;
    private final GrupoDAO daoGrupo;
    private final AlumnoDAO daoAlumno;

    private List<Grupo> listaGrupos;
    private List<Alumno> listaAlumnos;
    private DefaultTableModel modeloTabla;

    public CtrlInscripcion(FrmInscripciones vista, AlumnoGrupoDAO daoInscripcion) {
        this.vista = vista;
        this.daoInscripcion = daoInscripcion;
        this.daoGrupo = new GrupoDAO();
        this.daoAlumno = new AlumnoDAO();

        this.vista.btnInscribir.addActionListener(this);
        this.vista.btnDarDeBaja.addActionListener(this);
        
        // Descomenta esta línea después de agregar el botón btnGuardarCalificacion en el FrmInscripciones
        // this.vista.btnGuardarCalificacion.addActionListener(this);

        this.vista.cbxGrupo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vista.cbxGrupo.getSelectedIndex() > 0) {
                    int idGrupo = listaGrupos.get(vista.cbxGrupo.getSelectedIndex() - 1).getIdGrupo();
                    cargarTabla(idGrupo);
                } else {
                    limpiarTabla();
                }
            }
        });
    }

    public void iniciar() {
        vista.setTitle("Inscripciones y Calificaciones");
        cargarComboBoxes();
        limpiarTabla();
    }

    private void cargarComboBoxes() {
        vista.cbxGrupo.removeAllItems();
        vista.cbxAlumno.removeAllItems();

        vista.cbxGrupo.addItem("Seleccione un Grupo...");
        vista.cbxAlumno.addItem("Seleccione un Alumno...");

        listaGrupos = daoGrupo.obtenerTodos();
        listaAlumnos = daoAlumno.obtenerTodos();

        for (Grupo g : listaGrupos) {
            vista.cbxGrupo.addItem(g.getClaveGrupo() + " - " + g.getNombreMateria());
        }

        for (Alumno a : listaAlumnos) {
            vista.cbxAlumno.addItem(a.getMatricula() + " - " + a.getNombre() + " " + a.getApellidoPaterno());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnInscribir) {
            if (vista.cbxGrupo.getSelectedIndex() == 0 || vista.cbxAlumno.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un Grupo y un Alumno.");
                return;
            }

            AlumnoGrupo ag = new AlumnoGrupo();
            int idGrupo = listaGrupos.get(vista.cbxGrupo.getSelectedIndex() - 1).getIdGrupo();
            ag.setIdGrupo(idGrupo);
            ag.setIdAlumno(listaAlumnos.get(vista.cbxAlumno.getSelectedIndex() - 1).getIdPersona());

            if (daoInscripcion.registrar(ag)) {
                JOptionPane.showMessageDialog(vista, "Alumno inscrito correctamente.");
                cargarTabla(idGrupo);
                vista.cbxAlumno.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(vista, "Error al inscribir. Posiblemente el alumno ya está en este grupo.");
            }
        }

        if (e.getSource() == vista.btnDarDeBaja) {
            int fila = vista.tablaInscripciones.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla para darlo de baja.");
                return;
            }

            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Desea dar de baja al alumno de este grupo?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirmacion == JOptionPane.YES_OPTION) {
                int idAlumno = Integer.parseInt(vista.tablaInscripciones.getValueAt(fila, 0).toString());
                int idGrupo = listaGrupos.get(vista.cbxGrupo.getSelectedIndex() - 1).getIdGrupo();

                if (daoInscripcion.eliminar(idAlumno, idGrupo)) {
                    JOptionPane.showMessageDialog(vista, "Baja procesada correctamente.");
                    cargarTabla(idGrupo);
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al dar de baja.");
                }
            }
        }

        // Bloque ajustado para 2 parciales (según la BD)
        /*
        if (e.getSource() == vista.btnGuardarCalificacion) {
            int fila = vista.tablaInscripciones.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Seleccione un alumno de la tabla para calificar.");
                return;
            }

            try {
                int idAlumno = Integer.parseInt(vista.tablaInscripciones.getValueAt(fila, 0).toString());
                int idGrupo = listaGrupos.get(vista.cbxGrupo.getSelectedIndex() - 1).getIdGrupo();
                
                double p1 = Double.parseDouble(vista.txtParcial1.getText().trim());
                double p2 = Double.parseDouble(vista.txtParcial2.getText().trim());

                if (daoInscripcion.actualizarCalificaciones(idAlumno, idGrupo, p1, p2)) {
                    JOptionPane.showMessageDialog(vista, "Calificaciones actualizadas. Promedio calculado por BD.");
                    cargarTabla(idGrupo);
                    vista.txtParcial1.setText("");
                    vista.txtParcial2.setText("");
                } else {
                    JOptionPane.showMessageDialog(vista, "Error al guardar calificaciones.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar números válidos para los parciales.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        }
        */
    }

    private void cargarTabla(int idGrupo) {
        List<AlumnoGrupo> lista = daoInscripcion.obtenerPorGrupo(idGrupo);
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Alumno"); // Índice 0
        modeloTabla.addColumn("Matrícula");
        modeloTabla.addColumn("Nombre del Alumno");
        modeloTabla.addColumn("Parcial 1");
        modeloTabla.addColumn("Parcial 2");
        modeloTabla.addColumn("Promedio Final");
        modeloTabla.addColumn("Fecha Inscripción"); // Índice 6

        for (AlumnoGrupo ag : lista) {
            Object[] fila = new Object[7]; 
            fila[0] = ag.getIdAlumno();
            fila[1] = ag.getMatricula();
            fila[2] = ag.getNombreCompletoAlumno();
            fila[3] = (ag.getParcial1() == null) ? "0.0" : ag.getParcial1();
            fila[4] = (ag.getParcial2() == null) ? "0.0" : ag.getParcial2();
            fila[5] = (ag.getCalificacionFinal() == null) ? "Sin calificar" : ag.getCalificacionFinal();
            fila[6] = ag.getFechaInscripcion();
            modeloTabla.addRow(fila);
        }
        vista.tablaInscripciones.setModel(modeloTabla);
        ocultarColumnaID();
    }
    
    private void limpiarTabla() {
        modeloTabla = new DefaultTableModel();
        modeloTabla.addColumn("ID Alumno");
        modeloTabla.addColumn("Matrícula");
        modeloTabla.addColumn("Nombre del Alumno");
        modeloTabla.addColumn("Parcial 1");
        modeloTabla.addColumn("Parcial 2");
        modeloTabla.addColumn("Promedio Final");
        modeloTabla.addColumn("Fecha Inscripción");
        vista.tablaInscripciones.setModel(modeloTabla);
        ocultarColumnaID();
    }

    // Método auxiliar para no repetir código de diseño
    private void ocultarColumnaID() {
        if (vista.tablaInscripciones.getColumnCount() > 0) {
            vista.tablaInscripciones.getColumnModel().getColumn(0).setMinWidth(0);
            vista.tablaInscripciones.getColumnModel().getColumn(0).setMaxWidth(0);
            vista.tablaInscripciones.getColumnModel().getColumn(0).setWidth(0);
            vista.tablaInscripciones.getColumnModel().getColumn(0).setPreferredWidth(0);
        }
    }
}