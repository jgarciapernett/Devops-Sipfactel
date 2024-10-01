package co.com.periferia.alfa.core.model;

import java.util.List;
import org.apache.poi.ss.usermodel.IndexedColors;

import co.com.periferia.alfa.core.model.admin.Auditoria;
import lombok.Getter;
import lombok.Setter;

/***
 * @author Paula Moreno
 * Clase para el manejo de hoja de un archivo en Excel
 */
@Getter
@Setter
public class ObjetoHoja{

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
	private List<List<Auditoria>> datos;

	public ObjetoHoja(){}
	
	public ObjetoHoja(String nombre, List<String> titulos, List<IndexedColors> colores, List<List<Auditoria>> datos) {
		super();
		this.nombre = nombre;
		this.titulos = titulos;
		this.colores = colores;
		this.datos = datos;
	}
	
}
