/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.Serializable;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author gustavo
 */
@Named
@ViewScoped
public class AccessControl implements Serializable{
    
    public void verificarSesion(){
        try{
            String use=(String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            if(use!=null){
                FacesContext.getCurrentInstance().getExternalContext().redirect("/faces/login?faces-redirect=true");
            }
        }catch(Exception e){
            
        }
    }
}
