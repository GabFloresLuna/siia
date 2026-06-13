package cl.duocuc.siia.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import cl.duocuc.siia.dto.PdfVehiculoRequestDTO;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PdfGeneradorService {

    public byte[] generarFormularioVehiculo(PdfVehiculoRequestDTO request) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Paragraph title = new Paragraph("FORMULARIO DE ADMISIÓN TEMPORAL DE VEHÍCULOS", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Subtítulo
            Font subFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Paragraph subTitle = new Paragraph("Acuerdo Chile - Argentina", subFont);
            subTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subTitle);
            document.add(Chunk.NEWLINE);

            // Tabla de datos
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Estilo de celdas
            Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

            addCell(table, "Folio:", labelFont);
            addCell(table, request.getFolio() != null ? request.getFolio() : "No generado", valueFont);

            addCell(table, "Fecha de emisión:", labelFont);
            addCell(table, LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")), valueFont);

            addCell(table, "Patente:", labelFont);
            addCell(table, request.getPatente(), valueFont);

            addCell(table, "Tipo de vehículo:", labelFont);
            addCell(table, request.getTipoVehiculo(), valueFont);

            addCell(table, "Titular:", labelFont);
            addCell(table, request.getTitular(), valueFont);

            document.add(table);

            // Firmas
            document.add(Chunk.NEWLINE);
            Paragraph firmas = new Paragraph("_________________________\nFirma del funcionario\n\n_________________________\nFirma del titular", FontFactory.getFont(FontFactory.HELVETICA, 10));
            firmas.setAlignment(Element.ALIGN_CENTER);
            document.add(firmas);

            // Pie de página
            document.add(Chunk.NEWLINE);
            Paragraph footer = new Paragraph("Documento válido para tramitación ante aduanas chilenas y argentinas.", FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 8));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    private void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(5);
        cell.setBorder(Rectangle.BOX);
        table.addCell(cell);
    }
}