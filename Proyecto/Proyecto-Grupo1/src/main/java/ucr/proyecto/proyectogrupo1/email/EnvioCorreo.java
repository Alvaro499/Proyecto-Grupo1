package ucr.proyecto.proyectogrupo1.email;

import ucr.proyecto.proyectogrupo1.Segurity.Cryptographic;

import java.io.File;

public class EnvioCorreo {
//Codigo de ejemplo
    public static void main(String[] args) throws EnvioCorreos.EmailExcepcion {
        EnvioCorreos mEnvioCorreos = new EnvioCorreos();
        mEnvioCorreos.setEmailTo("nicolemontero202@gmail.com");
        mEnvioCorreos.setSubject("Prueba");
        mEnvioCorreos.setContent("Este mensaje fue enviado por Java");

   /*     String ruta = "C:/Users/qzuni/Desktop/envios/img.jpg";
        File archivo = new File(ruta);
        mEnvioCorreos.setAttachmentFile(archivo);*/

        mEnvioCorreos.createEmail();
        mEnvioCorreos.sendEmail();

       // System.out.println(Cryptographic.descodificar("618 399 327 488 347 250 637 427 310 271 207 96"));
    }
}
