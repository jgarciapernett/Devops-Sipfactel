package co.com.periferia.alfa.core.model;

import java.util.List;

import org.apache.poi.ss.usermodel.IndexedColors;

import lombok.Getter;
import lombok.Setter;

/***
 * @author Paula Moreno Clase para el manejo de hoja de un archivo en Excel
 */
@Getter
@Setter
public class ObjetoHojaEstado {

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
	private List<TablaReporteEstadoModel> datos;

	public ObjetoHojaEstado() {
	}

	public ObjetoHojaEstado(String nombre, List<String> titulos, List<IndexedColors> colores,
			List<TablaReporteEstadoModel> datos) {
		super();
		this.nombre = nombre;
		this.titulos = titulos;
		this.colores = colores;
		this.datos = datos;
	}

}
