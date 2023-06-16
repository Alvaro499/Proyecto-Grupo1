package ucr.proyecto.proyectogrupo1.Segurity;

public class Cryptographic {
    private static final int[][] key = new int[][]{{1, 2, 3}, {1, 1, 2}, {0, 1, 2}};//Matriz
    private static final int[][] keyI = new int[][]{{0, 1, -1}, {2, -2, -1}, {-1, 1, 1}};//Matriz inversa

    public static String codificar(String frase) {

        //Primero: Descomponemos el mensajo en unidades de igual longitud y cada caracter lo pasamos a su valor en ascii

        //el tamano de frase tiene que ser divisible por 3
        char[] caracteres = frase.toCharArray();
        while (caracteres.length % 3 != 0) {
            frase += " ";
            caracteres = frase.toCharArray();
        }

        int[] valoresAscii = new int[caracteres.length];
        frase = "";

        for (int i = 0; i < caracteres.length; i++) {
            valoresAscii[i] = caracteres[i];
        }

        //Segundo: Multiplicamos con el key los valores de valoresAscii, en 3 en 3
        for (int i = 0; i < valoresAscii.length; i += 3) {
            int[][] a = new int[3][1];
            a[0][0] = valoresAscii[i];
            a[1][0] = valoresAscii[i + 1];
            a[2][0] = valoresAscii[i + 2];

            a = multiplicarMatrices(key, a);

            frase += a[0][0] + " " + a[1][0] + " " + a[2][0] + " ";
        }

        return frase;
    }

    public static String descodificar(String fraseCodificada) {
        String frase = "";
        String[] valoresString = fraseCodificada.trim().split(" ");
        Integer[] valoresInteger = new Integer[valoresString.length];
        //Pasamos el array String a un array Integer
        for (int i = 0; i < valoresString.length; i++) {
            valoresInteger[i] = Integer.parseInt(valoresString[i]);
        }
        //obtenemos las letras
        for (int i = 0; i < valoresInteger.length; i += 3) {

            int valorReal[][] = new int[3][1];
            valorReal[0][0] = valoresInteger[i];
            valorReal[1][0] = valoresInteger[i + 1];
            valorReal[2][0] = valoresInteger[i + 2];

            valorReal = multiplicarMatrices(keyI, valorReal);

            frase += "" + (char) valorReal[0][0] + (char) valorReal[1][0] + (char) valorReal[2][0];
        }
        return frase;
    }

    private static int[][] multiplicarMatrices(int[][] matrizA, int[][] matrizB) {
        int filasA = matrizA.length;
        int columnasA = matrizA[0].length;
        int columnasB = matrizB[0].length;

        int[][] resultado = new int[filasA][columnasB];

        for (int i = 0; i < filasA; i++) {
            for (int j = 0; j < columnasB; j++) {
                for (int k = 0; k < columnasA; k++) {
                    resultado[i][j] += matrizA[i][k] * matrizB[k][j];
                }
            }
        }

        return resultado;
    }
}