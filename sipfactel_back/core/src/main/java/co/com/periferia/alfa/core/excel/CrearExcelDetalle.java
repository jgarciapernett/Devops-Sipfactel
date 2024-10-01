package co.com.periferia.alfa.core.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.ObjetoHojaDetalle;
import co.com.periferia.alfa.core.model.ReporteContableModel;

@Component
public class CrearExcelDetalle {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrearExcelDetalle.class);

	@Autowired
	static UtilExcecion utilException;

	public ByteArrayInputStream generar(List<String> titulos, List<ReporteContableModel> datos, String nombreHoja)
			throws ExcepcionSegAlfa {
		ObjetoHojaDetalle objetoHoja = new ObjetoHojaDetalle();
		objetoHoja.setDatos(datos);
		objetoHoja.setNombre(nombreHoja);
		objetoHoja.setTitulos(titulos);
		return generar(objetoHoja);
	}

	public ByteArrayInputStream generar(ObjetoHojaDetalle informacion) throws ExcepcionSegAlfa {

		LOGGER.info("Se inicia la escritura del Excel con el objeto {}", informacion);

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Sheet sheet = workbook.createSheet(informacion.getNombre());

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			InputStream inputStream = getClass().getResourceAsStream("/static/Imagenes/LogoSegAlfa.png");
			// Get the contents of an InputStream as a byte[].
			byte[] bytes = IOUtils.toByteArray(inputStream);
			// Adds a picture to the workbook
			int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
			// close the input stream
			inputStream.close();

			// Returns an object that handles instantiating concrete classes
			CreationHelper helper = workbook.getCreationHelper();

			// Creates the top-level drawing patriarch.
			Drawing<?> drawing = sheet.createDrawingPatriarch();

			// Create an anchor that is attached to the worksheet
			ClientAnchor anchor = helper.createClientAnchor();
			// set top-left corner for the image
			anchor.setCol1(0);
			anchor.setRow1(0);

			// Creates a picture
			Picture pict = drawing.createPicture(anchor, pictureIdx);
			// Reset the image to the original size
			pict.resize();
			
			int rowNum = 7;
			Row rowsito = sheet.createRow(rowNum++);
			LOGGER.info("Se inicia la iteracion de los titulos");
			for (int i = 0; i < informacion.getTitulos().size(); i++) {
				CellStyle accountingStyle = workbook.createCellStyle();
				Cell cell = rowsito.createCell(i);
				String valorCelda = informacion.getTitulos().get(i);
				cell.setCellValue(valorCelda);
				if (informacion.getColores().get(i) != null) {
					IndexedColors back = informacion.getColores().get(i);
					accountingStyle.setFillForegroundColor(back.getIndex());
				}
				accountingStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
				accountingStyle.setFont(headerFont);
				cell.setCellStyle(accountingStyle);
				LOGGER.debug("Escribiendo el titulo : {}", valorCelda);
			}
			
			LOGGER.info("Inicia la iteracion de los datos");
			int contador = 0;
			Row fila = sheet.createRow(rowNum++);
			LOGGER.info("Escritura de los datos en la fila --> {}", contador);
			
			DecimalFormat formateo = new DecimalFormat("###,###,###.###");//formato establecido para el resultado de las formulas
			
			for (ReporteContableModel data2 : informacion.getDatos()) {
				fila.createCell(contador++).setCellValue(data2.getCompania());
				fila.createCell(contador++).setCellValue(data2.getFecha().concat(" ").concat(data2.getHora()));
				fila.createCell(contador++).setCellValue(data2.getFechaTransaccion());
				fila.createCell(contador++).setCellValue(data2.getTipoTransaccion());
				fila.createCell(contador++).setCellValue(nulo(data2.getNumTransaccion()));
				fila.createCell(contador++).setCellValue(nulo(data2.getNumReferencia()));
				fila.createCell(contador++).setCellValue(data2.getNumPoliza());
				fila.createCell(contador++).setCellValue(formateo.format(data2.getValorPrima()));
				fila.createCell(contador++).setCellValue(formateo.format(data2.getValorIva()));
				fila.createCell(contador++).setCellValue(formateo.format(data2.getTotal()));
				fila.createCell(contador++).setCellValue(data2.getTipoDocumento());
				fila.createCell(contador++).setCellValue(data2.getNumeroIdentificacion());
				fila.createCell(contador++).setCellValue(data2.getRazonSocial());
				fila.createCell(contador++).setCellValue(data2.getSucursal());
				fila.createCell(contador++).setCellValue(data2.getEstado());
				fila.createCell(contador++).setCellValue(data2.getMensaje());
				contador = 0;
				fila = sheet.createRow(rowNum++);
			}

			workbook.write(out);
			out.flush();
			out.close();
			LOGGER.debug("Finaliza de forma correcta el proceso");
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw utilException.getById(12).crearExcepcion();
		}
	}

	public String nulo(String dato) {

		if (dato == null) {
			dato = "";
		}
		return dato;
	}
}
