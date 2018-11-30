/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

/**
 *
 * @author gustavo
 */
public class SendMail {

    private String header;
    private String name;
    private String mailAddress;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public final Session sendMail() {
        final String username = "";
        final String password = "";
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties,
                new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        return session;
    }

    public void mailRecovery(String mail, String pass) {

        try {
            Message message = new MimeMessage(sendMail());
            message.setFrom(new InternetAddress("gusbasti2005@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
            message.setSubject("Recuperar Contraseña");
            message.setText("Buenas, \na continuacion encontrara la nueva contraseña generada para su cuenta:\n " + pass + "\nHasta luego.");
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("error mail-->" + e);
        }
    }

}
