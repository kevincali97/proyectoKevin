/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import dao.ActualizaPass;
import dao.LoginDao;

import javax.faces.application.FacesMessage;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import util.HashedPasswordGenerator;

/**
 *
 * @author gustavo
 */
@Named(value = "actualizapassBean")
@RequestScoped 
public class ActualizarPassBean {
    private String pass;
    private String newPass;
    private ActualizaPass update;

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }
    
    public ActualizarPassBean(){
        
    }
    
    public String actualiza(){
        String msg="";
        FacesContext context = FacesContext.getCurrentInstance();
        LoginDao login=(LoginDao) context.getExternalContext().getSessionMap().get("usuario");
        HashedPasswordGenerator encrip=new HashedPasswordGenerator();
        pass=encrip.generateHash(pass);
        System.out.println("valor de sql:"+login.getContrasena()+" valor vista: "+pass);
        update=new ActualizaPass();
        if(login.getContrasena().equals(pass)){
            if(login.getRol().equals("admin")){
                update.actualizarPass(login.getUsuario(), encrip.generateHash(newPass));
                 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Importante", "se ha actualizado con exito"));
        context.getExternalContext().getFlash().setKeepMessages(true);
                msg="/admin/admin?faces-redirect=true";
            }else{
                update.actualizarPass(login.getUsuario(), encrip.generateHash(newPass));
                 context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Importante", "se ha actualizado con exito"));
        context.getExternalContext().getFlash().setKeepMessages(true);
                msg="/user/user?faces-redirect=true";
            }
            
        }else{
               context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Importante", "La contraseña original no coincide"));
        context.getExternalContext().getFlash().setKeepMessages(true);
         if(login.getRol().equalsIgnoreCase("admin")){
        msg="/admin/actualizarpassadmin?faces-redirect=true";
         }else{
             msg="/user/actualizarpassuser?faces-redirect=true";
         }
         
        }
        
        return msg;
    }
    
}
