package co.com.periferia.alfa.core.dto;

import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que sirve de objeto para la hos del reporte excel de adquirientes
 * @author Duvan Rodriguez
 */

@Getter
@Setter
public class HojaAdquirientesExcel {

	/***
	 * Nombre de la hoja en el archivo destino
	 */
	private String nombre;
	/**
	 * Titulos en la primera fila
	 */
	private List<String> titulos;
	/**
	 * Colores que utilizaran los titulos
	 */
	private List<IndexedColors> colores;
	/**
	 * Datos que se escribiran en la hoja
	 */
	private List<ReporteAdquirienteDto> datos;
	
	public HojaAdquirientesExcel() {
		
	}
	
	public HojaAdquirientesExcel(String nombre, List<String> titulos, List<IndexedColors> colores,
			List<ReporteAdquirienteDto> datos) {
		super();
		this.nombre = nombre;
		this.titulos = titulos;
		this.colores = colores;
		this.datos = datos;
	}
	
}
