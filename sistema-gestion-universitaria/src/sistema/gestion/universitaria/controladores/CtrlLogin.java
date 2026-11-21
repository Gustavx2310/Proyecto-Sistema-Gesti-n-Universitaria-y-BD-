package sistema.gestion.universitaria.controladores;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import sistema.gestion.universitaria.dao.UsuarioDAO;
import sistema.gestion.universitaria.dto.UsuarioDTO;
import sistema.gestion.universitaria.vistas.FrmLogin;
import sistema.gestion.universitaria.vistas.FrmMenuPrincipal;

public class CtrlLogin implements ActionListener {

    private final FrmLogin vista;
    private final UsuarioDAO dao;

    public CtrlLogin(FrmLogin vista, UsuarioDAO dao) {
        this.vista = vista;
        this.dao = dao;
        
        // Pon aquí el nombre exacto de tu botón de Iniciar Sesión
        this.vista.btnIngresar.addActionListener(this); 
    }

    public void iniciar() {
        vista.setTitle("Acceso - Sistema de Gestión Universitaria");
        vista.setLocationRelativeTo(null); // Centra la ventana en la pantalla
        vista.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.btnIngresar) {
            
            // Pon aquí los nombres exactos de tus cajas de texto
            String username = vista.txtUsuario.getText().trim();
            String password = new String(vista.txtPassword.getPassword()); // getPassword() extrae el texto del JPasswordField

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Debe ingresar usuario y contraseña.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Llamamos al DAO que me compartiste
            UsuarioDTO usuarioLogueado = dao.login(username, password);

            if (usuarioLogueado != null) {
                JOptionPane.showMessageDialog(vista, "Bienvenido " + usuarioLogueado.getUsername() + "\nAcceso concedido como: " + usuarioLogueado.getNombreRol(), "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                
                // CORRECCIÓN: Se inyecta el usuarioLogueado al constructor del menú
                FrmMenuPrincipal menu = new FrmMenuPrincipal(usuarioLogueado);
                menu.setLocationRelativeTo(null);
                menu.setVisible(true);
                
                // Cierra y destruye la ventana de login
                vista.dispose(); 
            } else {
                JOptionPane.showMessageDialog(vista, "Usuario o contraseña incorrectos. Intente de nuevo.", "Acceso Denegado", JOptionPane.ERROR_MESSAGE);
                vista.txtPassword.setText(""); // Limpiamos la contraseña por seguridad
            }
        }
    }
}