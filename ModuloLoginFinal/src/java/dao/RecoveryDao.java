/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import util.DataConnect;
import util.GenerarContrasena;
import util.HashedPasswordGenerator;

/**
 *
 * @author gustavo
 */
public class RecoveryDao {
    
    Connection con = null;
    PreparedStatement ps = null;
    
     public void actualizarContrasena(String pass,String usu){
        String cont="";
        try{
             HashedPasswordGenerator cifrado = new HashedPasswordGenerator();
             cont=cifrado.generateHash(pass);
         con = DataConnect.getConnection();
            ps = con.prepareStatement("update usuario set usu_password=?, usu_bloqueo_clave=0 where usu_usuario=?;");
            ps.setString(1,cont);
            ps.setString(2, usu);
            
            ps.execute();
            
        }catch (SQLException e){
             System.out.println("Actualizar contraseña error -->" + e.getMessage());
        }
        
    }
    public String recuperarContrasena(String usu,String email){
        String resp=null;
          try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select usu_usuario from usuario,empleado where usu_usuario=? and eml_correo=? and usuario.usu_id_usuario=empleado.eml_id_usuario;");
            ps.setString(1,usu);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
               resp=GenerarContrasena.getPassword(GenerarContrasena.MAYUSCULAS+GenerarContrasena.MINUSCULAS+GenerarContrasena.NUMEROS,10);
                System.out.println("nueva contraseña: "+resp);
              
                return resp;
            }
        } catch (SQLException ex) {
            System.out.println("Recuperar contraseña error -->" + ex.getMessage());
            return resp=null;
        } finally {
            DataConnect.close(con);
        }

        return resp;
    }
}
