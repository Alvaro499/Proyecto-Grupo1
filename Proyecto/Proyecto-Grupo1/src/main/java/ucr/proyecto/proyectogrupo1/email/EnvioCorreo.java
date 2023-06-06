package ucr.proyecto.proyectogrupo1.email;

public class EnvioCorreo {

    public static void main(String[] args) {
        EnvioCorreos mEnvioCorreos = new EnvioCorreos();
        mEnvioCorreos.setEmailTo("zjeancarlo42@gmail.com");
        mEnvioCorreos.setSubject("Prueba");
        mEnvioCorreos.setContent("Este mensaje fue enviado por Java");
        mEnvioCorreos.createEmail();
        mEnvioCorreos.sendEmail();
    }
}
