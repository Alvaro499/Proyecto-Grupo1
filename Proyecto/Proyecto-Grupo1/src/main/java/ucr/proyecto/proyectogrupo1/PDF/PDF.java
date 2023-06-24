package ucr.proyecto.proyectogrupo1.PDF;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

public class PDF {
    private static LocalDate hoy;
    private static Document document = new Document();
    private static String documento;
    public PDF(){
        documento = "";
    }

    static {
        hoy = LocalDate.now();
    }

    public static void crearPDF(String nombrePDF, String titulo, Integer nColumnas, ArrayList contenido) {
        try {
            // Verificar si el documento anterior está abierto y cerrarlo si es necesario
            if (document != null && document.isOpen()) {
                document.close();
            }

            document = new Document();
            documento = "Reporte/" + nombrePDF + "_" + hoy + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(documento));
            document.open();

            // Este codigo genera una tabla de 3 columnas
            PdfPTable table = new PdfPTable(nColumnas);

            // Si desea crear una celda de mas de una columna
            // Cree un objecto Cell y cambie su propiedad span

            PdfPCell celdaTitulo = new PdfPCell(new Paragraph(titulo));
            // Indicamos cuantas columnas ocupa la celda
            celdaTitulo.setColspan(nColumnas);
            table.addCell(celdaTitulo);

            // addCell() agrega una celda a la tabla, el cambio de fila
            // ocurre automaticamente al llenar la fila
            Integer n = contenido.size();
            for (int i = 0; i < n; i++) {
                table.addCell((String) contenido.get(i));
            }


            PdfPCell celdaFinal = new PdfPCell(new Paragraph("Date: " + hoy));
            // Indicamos cuantas columnas ocupa la celda
            celdaFinal.setColspan(nColumnas);
            table.addCell(celdaFinal);


            // Agregamos la tabla al documento
            document.add(table);

            document.close();

        } catch (Exception e) {
            if (document != null && document.isOpen()) {
                document.close();
            }
            System.err.println("Ocurrió un error al crear el archivo");
            e.printStackTrace();
        }
    }

    public static String getDocumento() {
        return documento;
    }
}
//http://tiburondealambre.blogspot.com/2013/07/generando-tablas-en-archivos-pdf-con.html