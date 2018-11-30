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
public class LoginDao {

    Connection con = null;
    PreparedStatement ps = null;
    private String usuario;
    private String contrasena;
    private String rol;
    private boolean estado;
    private boolean cambiarClave;
    private int bloqueoClave;

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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isCambiarClave() {
        return cambiarClave;
    }

    public void setCambiarClave(boolean cambiarClave) {
        this.cambiarClave = cambiarClave;
    }

    public int getBloqueoClave() {
        return bloqueoClave;
    }

    public void setBloqueoClave(int bloqueoClave) {
        this.bloqueoClave = bloqueoClave;
    }

    
    public boolean validate() {

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("Select usu_usuario, usu_password from usuario where usu_usuario = ? and usu_password = ?");
            ps.setString(1, this.usuario);
            ps.setString(2, this.contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return false;
        } finally {
            DataConnect.close(con);
        }
        return false;
    }

    public int getBloqueoClave(String usu) {
        int num = 0;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select usu_bloqueo_clave from usuario where usu_usuario=?;");
            ps.setString(1, usu);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                num = rs.getInt("usu_bloqueo_clave");//result found, means valid inputs
                return num;
            }

        } catch (SQLException e) {
            System.out.println("Login error -->" + e.getMessage());
        }finally {
            DataConnect.close(con);
        }
        return num;
    }

    public boolean actualizarBloqueo(int num, String usu) {
        boolean rsp = false;
       
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("update usuario set usu_bloqueo_clave=? where usu_usuario=?;");
            ps.setInt(1, num + 1);
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

    public LoginDao consultarRol() {
        LoginDao obj = new LoginDao();

        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select rol_rol,usu_cambiar_clave,usu_activo,usu_bloqueo_clave from usuario,rol where usu_usuario=? and usu_password=? and rol.rol_id_rol=usuario.usu_id_rol;");
            ps.setString(1, this.usuario);
            ps.setString(2, this.contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj.rol = rs.getString("rol_rol");//result found, means valid inputs
                obj.cambiarClave = rs.getBoolean("usu_cambiar_clave");
                obj.estado = rs.getBoolean("usu_activo");
                obj.bloqueoClave = rs.getInt("usu_bloqueo_clave");
                obj.usuario=this.usuario;
                obj.contrasena=this.contrasena;
                return obj;
            }
        } catch (SQLException ex) {
            System.out.println("Login error -->" + ex.getMessage());
            return obj = null;
        } finally {
            DataConnect.close(con);
        }
        return obj;

    }

    public void actualizarContrasena(String pass, String usu) {
        String contrasena = "";
        try {
            HashedPasswordGenerator cifrado = new HashedPasswordGenerator();
            contrasena = cifrado.generateHash(pass);
            con = DataConnect.getConnection();
            ps = con.prepareStatement("update usuario set usu_password=?, usu_bloqueo_clave=0 where usu_usuario=?;");
            ps.setString(1, contrasena);
            ps.setString(2, usu);

            ps.execute();

        } catch (SQLException e) {
            System.out.println("Actualizar contraseña error -->" + e.getMessage());
        }finally {
            DataConnect.close(con);
        }

    }

    public String recuperarContrasena(String usu, String email) {
        String resp = null;
        try {
            con = DataConnect.getConnection();
            ps = con.prepareStatement("select usu_usuario from usuario,empleado where usu_usuario=? and eml_correo=? and usuario.usu_id_usuario=empleado.eml_id_usuario;");
            ps.setString(1, usu);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                resp = GenerarContrasena.getPassword(GenerarContrasena.MAYUSCULAS + GenerarContrasena.MINUSCULAS + GenerarContrasena.NUMEROS, 10);
                System.out.println("nueva contraseña: " + resp);

                return resp;
            }
        } catch (SQLException ex) {
            System.out.println("Recuperar contraseña error -->" + ex.getMessage());
            return resp = null;
        } finally {
            DataConnect.close(con);
        }

        return resp;
    }

}
