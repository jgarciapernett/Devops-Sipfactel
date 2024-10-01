package co.com.periferia.alfa.core.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
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

import co.com.periferia.alfa.core.dto.HojaAdquirientesExcel;
import co.com.periferia.alfa.core.dto.ReporteAdquirienteDto;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;

/**
 * Clase que realiza lo conserniente al excel de reporte de adquirientes
 * @author Duvan Rodriguez
 */

@Component
public class ExcelAdqurientes {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExcelAdqurientes.class);
	
	@Autowired
	static UtilExcecion utilException;

	/**
	 * Metodo que recoplia los datos y procede armar el objeto de la hoja excel
	 * @param List<ReporteAdquirienteDto> datos - lista con los datos de adquirientes
	 * @return ByteArrayInputStream
	 */
	
	public ByteArrayInputStream armarDatosExcel(List<ReporteAdquirienteDto> datos)
			throws ExcepcionSegAlfa {
		LOGGER.info("Ingreso a metodo armar datos del excel de adquirientes");
		List<String> titulos = new ArrayList<>();	
		
		titulos.add("PER_COD_TIPOPERSONA");
		titulos.add("PER_NOMBRERAZONSOCIAL");
		titulos.add("PER_NOMBRECOMERCIAL");
		titulos.add("PER_COD_TIPOIDENTIFICACION");
		titulos.add("PER_NUMIDENTIFICACION");
		titulos.add("PER_OBL_CONTRIBUYENTE");
		titulos.add("PER_ TIP_REGIMEN");
		titulos.add("PER_COD_POSTAL");
		titulos.add("PER_TELEFONO");
		titulos.add("PER_DIRECCION");
		titulos.add("PER_DIRECCIONFISCAL");
		titulos.add("PER_CORREO");
		titulos.add("PER_COD_PAIS");
		titulos.add("PER_COD_DEPARTAMENTO");
		titulos.add("PER_COD_CIUDAD");
		titulos.add("PER_CONTACTO");
		titulos.add("PER_AREAPERTENECE");
		titulos.add("PER_FACTURADORELECTRONICO");
		titulos.add("PER_COD_ENVIOFE");
		titulos.add("PER_RECIBIR_XML");
		titulos.add("PER_DESHABILITADO");
		titulos.add("PER_COD_TRIBUTO");
		titulos.add("PER_FECHAINSERCION");
		titulos.add("PER_FECHAACTUALIZACION");
		titulos.add("PER_USUARIO");
		titulos.add("PER_COD_FUENTE");

		
		String nombreHoja = "ReporteAdquirientes";
		
		List<IndexedColors> coloresAdquirientes = Arrays.asList(
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT,
				IndexedColors.GREY_40_PERCENT, IndexedColors.GREY_40_PERCENT);
		
		HojaAdquirientesExcel objetoHoja = new HojaAdquirientesExcel();
		objetoHoja.setDatos(datos);
		objetoHoja.setNombre(nombreHoja);
		objetoHoja.setTitulos(titulos);
		objetoHoja.setColores(coloresAdquirientes);
		return generarExcel(objetoHoja);
	}

	/**
	 * Metodo que recibe el objeto hoja para armar y retorna el excel
	 * @param HojaAdquirientesExcel informacion - objeto con la informacion necesaria para armar el excel
	 * @return ByteArrayInputStream
	 */
	
	private ByteArrayInputStream generarExcel(HojaAdquirientesExcel informacion) throws ExcepcionSegAlfa {

		LOGGER.info("Se inicia la escritura del Excel de adquirientes con el objeto {}", informacion);

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
			LOGGER.info("Se inicia la iteracion de los titulos: {} ", informacion.getTitulos().size());
			for (int i = 0; i <= informacion.getTitulos().size()-1; i++) {
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
			}

			LOGGER.info("Inicia la iteracion de los datos");
			int contador = 0;
			Row fila = sheet.createRow(rowNum++);
			
			DataFormat formatoTexto = workbook.createDataFormat();
			CellStyle cellStyleTexto = workbook.createCellStyle();
			cellStyleTexto.setDataFormat(formatoTexto.getFormat("@"));
			
			DataFormat formatoNumerico = workbook.createDataFormat();
			CellStyle cellStyleNumerico = workbook.createCellStyle();
			cellStyleNumerico.setDataFormat(formatoNumerico.getFormat("0"));
			
			for (ReporteAdquirienteDto adquirientes : informacion.getDatos()) {
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getTipoPersona()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getRazonSocialAdquriente()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getNombreComercial()));
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getTipoDocumentoAdquiriente()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getNitAdquiriente()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getObligacionesFiscales()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getTipoRegimen()));
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getCodigoPostal()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getTelefono()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getDireccionFisica()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getDireccionFiscal()));
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getCorreo()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getPais()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getDepartamento()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getCiudad()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getNombreContacto()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getAreaPertenece()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getFacturadorElectronico()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getAutorizaEntregaElectronicaCorreoElectronico()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getRecibirXml()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getDeshabilitado()));
				fila.getCell(contador - 1).setCellStyle(cellStyleNumerico);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getTributo()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(adquirientes.getFechaInsercion());
				fila.createCell(contador++).setCellValue(adquirientes.getFechaActualizacion());
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getRegimen()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				fila.createCell(contador++).setCellValue(nulo(adquirientes.getCodigoFuente()));
				fila.getCell(contador - 1).setCellStyle(cellStyleTexto);
				contador = 0;
				fila = sheet.createRow(rowNum++);
			}

			workbook.write(out);
			out.flush();
			out.close();
			LOGGER.info("Finaliza de forma correcta el proceso del excel de adquirientes");
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw utilException.getById(12).crearExcepcion();
		}
	}

	/**
	 * Metodo que valida si un dato es null
	 * @param String dato - Objeto string a validar
	 * @return retorna un string dependiendo si es nulo o no
	 */
	
	public String nulo(String dato) {

		if (dato == null || dato.equals("null")) {
			dato = "";
		}
		return dato;
	}
	
}
