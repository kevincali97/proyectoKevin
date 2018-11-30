/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;


import dao.RecoveryDao;

import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import util.SendMail;

/**
 *
 * @author gustavo
 */
@Named(value = "recoveryBean")
@RequestScoped 
public class RecoveryBean {

    private String usuario;
    private String email;
    private RecoveryDao recovery;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public RecoveryDao getRecovery() {
        return recovery;
    }

    public void setRecovery(RecoveryDao recovery) {
        this.recovery = recovery;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RecoveryBean() {
    }

    public String recuperarContrasena() {
        FacesContext context = FacesContext.getCurrentInstance();
        recovery = new RecoveryDao();
        System.out.println("valor de usuario: " + usuario + " valor email: " + email);
        String resp = recovery.recuperarContrasena(usuario, email);

        recovery.actualizarContrasena(resp, usuario);
        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Importante", "Se ha enviado a la cuenta de correo asociada la nueva contraseña."));
        context.getExternalContext().getFlash().setKeepMessages(true);
        SendMail mail = new SendMail();
        mail.mailRecovery(email, resp);

        return "/login?faces-redirect=true";
    }

}
