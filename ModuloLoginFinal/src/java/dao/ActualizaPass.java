/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import util.DataConnect;
import util.HashedPasswordGenerator;

/**
 *
 * @author gustavo
 */
public class ActualizaPass {
     Connection con = null;
    PreparedStatement ps = null;
    
    private String pass;
    private String newPass;

    public PreparedStatement getPs() {
        return ps;
    }

    public void setPs(PreparedStatement ps) {
        this.ps = ps;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    
    public ActualizaPass(){
        
    }
    
    public boolean actualizarPass(String usu,String newpass){
        boolean rsp=false;
      
            
            
         try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("update usuario set usu_password=? where usu_usuario=?;");
            ps.setString(1, newpass);
            ps.setString(2, usu);

            rsp = ps.execute();

        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return rsp = false;
        } finally {
            DataConnect.close(con);
        }
        return rsp;
    }
}
