/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package sistema.gestion.universitaria.vistas;

/**
 *
 * @author UAEM
 */
public class FrmMenuPrincipal extends javax.swing.JFrame {
    
    private final sistema.gestion.universitaria.dto.UsuarioDTO sesionActual;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FrmMenuPrincipal.class.getName());

    public FrmMenuPrincipal(sistema.gestion.universitaria.dto.UsuarioDTO sesionActual) {
        this.sesionActual = sesionActual; 
        initComponents(); 
        aplicarReglasDeAcceso(); 
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem7 = new javax.swing.JMenuItem();
        escritorio = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        menuEspacios = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenu3 = new javax.swing.JMenu();
        menuPagos = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenu1 = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();

        jMenuItem7.setText("jMenuItem7");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        escritorio.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 949, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        jMenuBar1.setBackground(new java.awt.Color(255, 255, 255));
        jMenuBar1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 3, 0, new java.awt.Color(44, 82, 52)));

        jMenu2.setBorder(null);
        jMenu2.setText("Catalogos          ");
        jMenu2.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N

        menuEspacios.setText("Gestionar Espacios");
        menuEspacios.addActionListener(this::menuEspaciosActionPerformed);
        jMenu2.add(menuEspacios);

        jMenuItem1.setText("Gestionar Carreras");
        jMenuItem1.addActionListener(this::jMenuItem1ActionPerformed);
        jMenu2.add(jMenuItem1);

        jMenuItem2.setText("Gestionar Materias");
        jMenuItem2.addActionListener(this::jMenuItem2ActionPerformed);
        jMenu2.add(jMenuItem2);

        jMenuItem3.setText("Gestionar Docentes");
        jMenuItem3.addActionListener(this::jMenuItem3ActionPerformed);
        jMenu2.add(jMenuItem3);

        jMenuItem4.setText("Gestionar Alumnos");
        jMenuItem4.addActionListener(this::jMenuItem4ActionPerformed);
        jMenu2.add(jMenuItem4);

        jMenuItem5.setText("Gestionar Grupos");
        jMenuItem5.addActionListener(this::jMenuItem5ActionPerformed);
        jMenu2.add(jMenuItem5);

        jMenuItem6.setText("Inscripciones");
        jMenuItem6.addActionListener(this::jMenuItem6ActionPerformed);
        jMenu2.add(jMenuItem6);
        jMenu2.add(jSeparator1);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Finanzas           ");
        jMenu3.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N

        menuPagos.setText("Pagos");
        menuPagos.addActionListener(this::menuPagosActionPerformed);
        jMenu3.add(menuPagos);
        jMenu3.add(jSeparator2);

        jMenuBar1.add(jMenu3);

        jMenu1.setBorder(null);
        jMenu1.setText("File");
        jMenu1.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        jMenu1.add(jSeparator3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(escritorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(escritorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuEspaciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuEspaciosActionPerformed
                                          
    // 1. Instanciar la vista y el DAO
    sistema.gestion.universitaria.vistas.FrmGestorEspacios ventanaEspacios = new sistema.gestion.universitaria.vistas.FrmGestorEspacios();
    sistema.gestion.universitaria.dao.EspacioUniversitarioDAO dao = new sistema.gestion.universitaria.dao.EspacioUniversitarioDAO();
    
    // 2. Instanciar el controlador pasándole la vista y el dao
    sistema.gestion.universitaria.controladores.CtrlEspacioUniversitario ctrl = new sistema.gestion.universitaria.controladores.CtrlEspacioUniversitario(ventanaEspacios, dao);
    
    // 3. Iniciar el controlador (carga la tabla y limpia campos)
    ctrl.iniciar();
    
    // 4. Agregarla al escritorio y mostrar
    escritorio.add(ventanaEspacios);
    ventanaEspacios.setVisible(true);

    }//GEN-LAST:event_menuEspaciosActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
// Instanciar vista y DAOs
sistema.gestion.universitaria.vistas.FrmGestorCarreras ventana = new sistema.gestion.universitaria.vistas.FrmGestorCarreras();
sistema.gestion.universitaria.dao.CarreraDAO daoC = new sistema.gestion.universitaria.dao.CarreraDAO();
sistema.gestion.universitaria.dao.EspacioUniversitarioDAO daoE = new sistema.gestion.universitaria.dao.EspacioUniversitarioDAO();

// Iniciar controlador
sistema.gestion.universitaria.controladores.CtrlCarrera ctrl = new sistema.gestion.universitaria.controladores.CtrlCarrera(ventana, daoC, daoE);
ctrl.iniciar();

// Mostrar
escritorio.add(ventana);
ventana.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
   // 1. Instanciar vista y DAOs
        sistema.gestion.universitaria.vistas.FrmGestorMaterias ventana = new sistema.gestion.universitaria.vistas.FrmGestorMaterias();
        sistema.gestion.universitaria.dao.MateriaDAO daoM = new sistema.gestion.universitaria.dao.MateriaDAO();
        sistema.gestion.universitaria.dao.CarreraDAO daoC = new sistema.gestion.universitaria.dao.CarreraDAO();

        // 2. Iniciar controlador
        sistema.gestion.universitaria.controladores.CtrlMateria ctrl = new sistema.gestion.universitaria.controladores.CtrlMateria(ventana, daoM, daoC);
        ctrl.iniciar();

        // 3. Mostrar la ventana en el escritorio
        escritorio.add(ventana);
        ventana.setVisible(true);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // 1. Instanciar vista y DAO
        sistema.gestion.universitaria.vistas.FrmGestorDocentes ventana = new sistema.gestion.universitaria.vistas.FrmGestorDocentes();
        sistema.gestion.universitaria.dao.DocenteDAO daoD = new sistema.gestion.universitaria.dao.DocenteDAO();

        // 2. Iniciar controlador
        sistema.gestion.universitaria.controladores.CtrlDocente ctrl = new sistema.gestion.universitaria.controladores.CtrlDocente(ventana, daoD);
        ctrl.iniciar();

        // 3. Mostrar la ventana en el escritorio
        escritorio.add(ventana);
        ventana.setVisible(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // 1. Instanciar vista y DAO
        sistema.gestion.universitaria.vistas.FrmGestorAlumnos ventana = new sistema.gestion.universitaria.vistas.FrmGestorAlumnos();
        sistema.gestion.universitaria.dao.AlumnoDAO daoA = new sistema.gestion.universitaria.dao.AlumnoDAO();

        // 2. Iniciar controlador
        sistema.gestion.universitaria.controladores.CtrlAlumno ctrl = new sistema.gestion.universitaria.controladores.CtrlAlumno(ventana, daoA);
        ctrl.iniciar();

        // 3. Mostrar la ventana en el escritorio
        escritorio.add(ventana);
        ventana.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        sistema.gestion.universitaria.vistas.FrmInscripciones ventana = new sistema.gestion.universitaria.vistas.FrmInscripciones();
sistema.gestion.universitaria.dao.AlumnoGrupoDAO dao = new sistema.gestion.universitaria.dao.AlumnoGrupoDAO();
sistema.gestion.universitaria.controladores.CtrlInscripcion ctrl = new sistema.gestion.universitaria.controladores.CtrlInscripcion(ventana, dao);
ctrl.iniciar();
escritorio.add(ventana);
ventana.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
sistema.gestion.universitaria.vistas.FrmGestorGrupos ventanaGrupos = new sistema.gestion.universitaria.vistas.FrmGestorGrupos();
sistema.gestion.universitaria.dao.GrupoDAO daoGrupos = new sistema.gestion.universitaria.dao.GrupoDAO();
sistema.gestion.universitaria.controladores.CtrlGrupo ctrlGrupos = new sistema.gestion.universitaria.controladores.CtrlGrupo(ventanaGrupos, daoGrupos);

ctrlGrupos.iniciar();
escritorio.add(ventanaGrupos); // Asegúrate de que tu JDesktopPane se llame "escritorio"
ventanaGrupos.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void menuPagosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuPagosActionPerformed
sistema.gestion.universitaria.vistas.FrmPagos ventanaPagos = new sistema.gestion.universitaria.vistas.FrmPagos();
sistema.gestion.universitaria.dao.FinancieroDAO daoFinanciero = new sistema.gestion.universitaria.dao.FinancieroDAO();
sistema.gestion.universitaria.controladores.CtrlFinanciero ctrl = new sistema.gestion.universitaria.controladores.CtrlFinanciero(ventanaPagos, daoFinanciero);

escritorio.add(ventanaPagos);
ventanaPagos.setVisible(true);
    }//GEN-LAST:event_menuPagosActionPerformed
private void aplicarReglasDeAcceso() {
        // 1. Ocultar todos los módulos por defecto (Fail-Safe)
        menuEspacios.setVisible(false);
        jMenuItem1.setVisible(false); // Carreras
        jMenuItem2.setVisible(false); // Materias
        jMenuItem3.setVisible(false); // Docentes
        jMenuItem4.setVisible(false); // Alumnos
        jMenuItem5.setVisible(false); // Grupos
        jMenuItem6.setVisible(false); // Inscripciones
        menuPagos.setVisible(false);  // Pagos

        // 2. Habilitar solo lo correspondiente al rol
        String rol = sesionActual.getNombreRol();

        if (rol.equals("SUPER_GESTOR")) {
            // Acceso total: Ve todo
            menuEspacios.setVisible(true);
            jMenuItem1.setVisible(true);
            jMenuItem2.setVisible(true);
            jMenuItem3.setVisible(true);
            jMenuItem4.setVisible(true);
            jMenuItem5.setVisible(true);
            jMenuItem6.setVisible(true);
            menuPagos.setVisible(true);
            
        } else if (rol.equals("DOCENTE")) {
            // Gestión Académica: Solo ve Grupos e Inscripciones/Calificaciones
            jMenuItem5.setVisible(true); 
            jMenuItem6.setVisible(true); 
            
        } else if (rol.equals("ALUMNO")) {
            // Autogestión: Solo ve Pagos y sus Calificaciones
            jMenuItem6.setVisible(true);
            menuPagos.setVisible(true);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel escritorio;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenuItem menuEspacios;
    private javax.swing.JMenuItem menuPagos;
    // End of variables declaration//GEN-END:variables
public sistema.gestion.universitaria.dto.UsuarioDTO getSesionActual() {
    return sesionActual;
}
}
