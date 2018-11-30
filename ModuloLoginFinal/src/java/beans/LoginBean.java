/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.LoginDao;
import javax.inject.Named;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import util.HashedPasswordGenerator;



/**
 *
 * @author gustavo
 */
@Named(value = "loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    /**
     * Creates a new instance of LoginBean
     */
    private String usuario;
    private String contrasena;
    private String rol;
    private LoginDao login;
    private String email;
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

        public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public LoginDao getLogin() {
        return login;
    }

    public void setLogin(LoginDao login) {
        this.login = login;
    }

    public LoginBean() {
        this.usuario="";
    this.contrasena="";
    this.rol="";
    this.login=new LoginDao();
    this.email="";
    }
    

    public String validateUsernamePassword() {
        String redireccion = null;
       FacesContext context = FacesContext.getCurrentInstance();
        login = new LoginDao();
        login.setUsuario(usuario);
        login.setContrasena(contrasena);
        HashedPasswordGenerator cifrado = new HashedPasswordGenerator();
        login.setContrasena(cifrado.generateHash(login.getContrasena()));
        boolean valid = login.validate();
        login = login.consultarRol();
                
        try {
            
            if (valid && login.isEstado()==true && login.getBloqueoClave()<3) {

                switch (login.getRol()) {
                    case "admin":
                        login.actualizarBloqueo(0,login.getUsuario());
                        context.getExternalContext().getSessionMap().put("usuario", login);
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", login.getUsuario()));
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        redireccion = "/admin/admin?faces-redirect=true";
                        break;

                    case "user":
                        login.actualizarBloqueo(0,login.getUsuario());
                        context.getExternalContext().getSessionMap().put("usuario", login);
                        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", login.getUsuario()));
                        context.getExternalContext().getFlash().setKeepMessages(true);
                        redireccion = "/user/user?faces-redirect=true";
                        break;

                }
            } else {
                if(valid && login.isEstado()==false || login.getBloqueoClave()==3){
                    
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de Inicio de sesión", "Usuario se encuentra Bloqueado, comuniquese con el administrador"));
                context.getExternalContext().getFlash().setKeepMessages(true);
                redireccion = "/recovery?faces-redirect=true";
            }else{
                    
                    login.actualizarBloqueo(login.getBloqueoClave(usuario),usuario);
                context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error de Inicio de sesión", "Usuario y/o contraseña incorrecta"));
                context.getExternalContext().getFlash().setKeepMessages(true);
                redireccion = "/login?faces-redirect=true";
                
            }
            }
        } catch (Exception e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "aviso", "error: " + e));
        }
                return redireccion;

    }

    public boolean verificarSesion() {
        boolean estado;
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario") == null) {
            estado = false;
        } else {
            estado = true;
        }
        return estado;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index?faces-redirect=true";
    }

   

}
