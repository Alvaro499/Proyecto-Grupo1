package ucr.proyecto.proyectogrupo1.email;

import java.io.File;

public class EnvioCorreo {

    public static void main(String[] args) throws EnvioCorreos.EmailExcepcion {
        EnvioCorreos mEnvioCorreos = new EnvioCorreos();
        mEnvioCorreos.setEmailTo("zjeancarlo42@gmail.com");
        mEnvioCorreos.setSubject("Prueba");
        mEnvioCorreos.setContent("Este mensaje fue enviado por Java");

        String ruta = "C:/Users/qzuni/Desktop/envios/img.jpg";
        File archivo = new File(ruta);
        mEnvioCorreos.setAttachmentFile(archivo);

        mEnvioCorreos.createEmail();
        mEnvioCorreos.sendEmail();
    }
}
