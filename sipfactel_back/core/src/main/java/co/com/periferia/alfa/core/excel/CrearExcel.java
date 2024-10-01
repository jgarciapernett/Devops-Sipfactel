package co.com.periferia.alfa.core.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.ObjetoHoja;
import co.com.periferia.alfa.core.model.admin.Auditoria;

public class CrearExcel {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrearExcel.class);

	@Autowired
	static UtilExcecion utilException;
	
	private CrearExcel() {
		super();
	}

	public static ByteArrayInputStream generar(List<String> titulos, List<List<Auditoria>> datos, String nombreHoja)
			throws ExcepcionSegAlfa {
		ObjetoHoja objetoHoja = new ObjetoHoja();
		objetoHoja.setDatos(datos);
		objetoHoja.setNombre(nombreHoja);
		objetoHoja.setTitulos(titulos);
		return generar(objetoHoja);
	}

	public static ByteArrayInputStream generar(ObjetoHoja informacion) throws ExcepcionSegAlfa {

		LOGGER.debug("Se inicia la escritura del Excel con el objeto {}", informacion);

		try (XSSFWorkbook workbook = new XSSFWorkbook()) {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			Sheet sheet = workbook.createSheet(informacion.getNombre());

			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);

			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			int rowNum = 0;
			Row rowsito = sheet.createRow(rowNum++);
			LOGGER.debug("Se inicia la iteracion de los titulos");
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

			LOGGER.debug("Inicia la iteracion de los datos");
			for (List<Auditoria> data : informacion.getDatos()) {
				int contador = 0;
				DateFormat format = new SimpleDateFormat("dd/MM/yyyy h:mm a");
				Row fila = sheet.createRow(rowNum++);
				LOGGER.debug("Escritura de los datos en la fila --> {}", contador);
				for (Auditoria data2 : data) {
					fila.createCell(contador++).setCellValue(data2.getAudiAudi());
					fila.createCell(contador++).setCellValue(data2.getAudiDescripcion());
					fila.createCell(contador++).setCellValue(format.format(new Date(data2.getAudiFecha().getTime())));
					fila.createCell(contador++).setCellValue(data2.getAudiFuncionalidad());
					fila.createCell(contador++).setCellValue(data2.getAudiIp());
					fila.createCell(contador++).setCellValue(data2.getAudiUsuario());
					fila.createCell(contador++).setCellValue(data2.getAudiNombre());
					fila.createCell(contador++).setCellValue(data2.getAudiRol());
					fila.createCell(contador++).setCellValue(data2.getAudiDetalle());

				}
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
}
