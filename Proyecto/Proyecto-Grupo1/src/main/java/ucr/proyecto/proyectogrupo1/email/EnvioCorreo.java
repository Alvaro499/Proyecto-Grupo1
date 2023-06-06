package ucr.proyecto.proyectogrupo1.email;

public class EnvioCorreo {

    public static void main(String[] args) {
        EnvioCorreos mEnvioCorreos = new EnvioCorreos();
        mEnvioCorreos.createEmail();
        mEnvioCorreos.sendEmail();
    }
}
