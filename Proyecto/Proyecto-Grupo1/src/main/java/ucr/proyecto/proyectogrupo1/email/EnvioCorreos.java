package ucr.proyecto.proyectogrupo1.email;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import java.io.File;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvioCorreos extends javax.swing.JFrame {

    private static final String emailFrom = "tiendalaberintodelibros@gmail.com";
    private static final String passwordFrom = "nqqazzxyxhrvyitn";
    private String emailTo;
    private String subject;
    private String content;
    private File attachmentFile;

    private Properties mProperties;
    private Session mSession;
    private MimeMessage mCorreo;

    public EnvioCorreos() {
        // initComponents();
        mProperties = new Properties();
    }

    class EmailExcepcion extends Exception {
        public EmailExcepcion(String mensaje) {
            super(mensaje);
        }
    }

    public void createEmail() throws EmailExcepcion {
        if (emailTo == null) {
            throw new EmailExcepcion("Correo sin destinatario");
        }
        if (subject == null) {
            throw new EmailExcepcion("Correo sin motivo");
        }
        if (content == null) {
            throw new EmailExcepcion("Correo sin contenido");
        }

        mProperties.put("mail.smtp.host", "smtp.gmail.com");
        mProperties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        mProperties.setProperty("mail.smtp.starttls.enable", "true");
        mProperties.setProperty("mail.smtp.port", "587");
        mProperties.setProperty("mail.smtp.user", emailFrom);
        mProperties.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        mProperties.setProperty("mail.smtp.auth", "true");

        mSession = Session.getDefaultInstance(mProperties);

        try {
            mCorreo = new MimeMessage(mSession);
            mCorreo.setFrom(new InternetAddress(emailFrom));
            mCorreo.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mCorreo.setSubject(subject);
            mCorreo.setText(content, "ISO-8859-1", "html");

            // Adjuntar el archivo
            if (attachmentFile != null) {
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.attachFile(attachmentFile);
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                mCorreo.setContent(multipart);
            }

        } catch (AddressException ex) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendEmail() {
        try {
            Transport mTransport = mSession.getTransport("smtp");
            mTransport.connect(emailFrom, passwordFrom);
            mTransport.sendMessage(mCorreo, mCorreo.getRecipients(Message.RecipientType.TO));
            mTransport.close();

            JOptionPane.showMessageDialog(null, "Correo enviado");
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(EnvioCorreos.class.getName()).log(Level.SEVERE, null, ex);
        }

        emailTo = null;
        subject = null;
        content = null;
        attachmentFile = null;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAttachmentFile(File attachmentFile) {
        this.attachmentFile = attachmentFile;
    }
}
