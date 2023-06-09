package ucr.proyecto.proyectogrupo1.PDF;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.IOException;

public class CrearPDF {
    public static void main(String[] args) {
        try {
            // Ruta de la carpeta donde se guardará el archivo PDF
            String rutaCarpeta = "C:/Users/qzuni/Desktop/Proy/Documentos";

            // Crear un nuevo documento PDF
            PDDocument document = new PDDocument();

            // Agregar una página al documento
            PDPage page = new PDPage();
            document.addPage(page);

            // Crear un objeto PDPageContentStream para escribir contenido en la página
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Establecer la fuente y el tamaño de la fuente
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Escribir texto en la página
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("¡Hola, mundo!");
            contentStream.endText();

            // Cerrar el objeto PDPageContentStream
            contentStream.close();

            // Guardar el documento PDF en un archivo
            document.save(rutaCarpeta + File.separator + "documento.pdf");

            // Cerrar el documento
            document.close();

            System.out.println("Documento PDF creado correctamente.");
        } catch (IOException e) {
            System.err.println("Error al crear el documento PDF: " + e.getMessage());
        }
    }
}
