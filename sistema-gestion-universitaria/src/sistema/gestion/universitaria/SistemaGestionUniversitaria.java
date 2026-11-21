package sistema.gestion.universitaria;

import sistema.gestion.universitaria.controladores.CtrlLogin;
import sistema.gestion.universitaria.dao.UsuarioDAO;
import sistema.gestion.universitaria.vistas.FrmLogin;

public class SistemaGestionUniversitaria {

    public static void main(String[] args) {
        // Inicializamos el patrón MVC exclusivamente para el Login
        sistema.gestion.universitaria.vistas.FrmLogin vistaLogin = new sistema.gestion.universitaria.vistas.FrmLogin();
        sistema.gestion.universitaria.dao.UsuarioDAO daoLogin = new sistema.gestion.universitaria.dao.UsuarioDAO();
        sistema.gestion.universitaria.controladores.CtrlLogin ctrl = new sistema.gestion.universitaria.controladores.CtrlLogin(vistaLogin, daoLogin);
        
        // Arrancamos la aplicación
        ctrl.iniciar();
        vistaLogin.setVisible(true);
    }
}