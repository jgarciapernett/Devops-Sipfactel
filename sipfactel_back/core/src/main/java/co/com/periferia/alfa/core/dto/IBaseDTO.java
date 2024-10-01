package co.com.periferia.alfa.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
 * Clase para los servicios basicos del DTO
 * 
 * @param <T> Clase DTO
 * @param <K> Modelo
 */
public interface IBaseDTO<T, K> {

	/**
	 * Transformacion del modelo a DTO
	 * 
	 * @param modelo
	 * @return
	 */
	@JsonIgnore
	T modeloAdto(K modelo);

	/***
	 * Transformacion del DTO al modelo
	 * 
	 * @param dto
	 * @return
	 */
	@JsonIgnore
	K dtoAModelo(T dto);

}
