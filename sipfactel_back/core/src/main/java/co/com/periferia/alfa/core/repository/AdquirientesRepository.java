package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.AdquirientesModel;

public interface AdquirientesRepository extends JpaRepository <AdquirientesModel, Integer>{
	
	/**
	 * Consulta que identifica que adquirientes iran al excel
	 */
	
	@Query(value = "SELECT " + 
			"per_per, " + 
			"per_cod_tipopersona, " + 
			"per_nombrerazonsocial, " + 
			"per_nombrecomercial, " + 
			"per_cod_tipoidentificacion, " + 
			"per_numidentificacion, " + 
			"per_dv, " + 
			"per_telefono, " + 
			"per_direccionfisica, " + 
			"per_correo, " + 
			"per_direccionfiscal, " + 
			"per_cod_pais, " + 
			"per_cod_departamento, " + 
			"per_cod_ciudad, " + 
			"per_contacto, " + 
			"per_areapertenece, " + 
			"per_facturadorelectronico, " + 
			"per_cod_enviofe, " + 
			"per_recibir_xml, " + 
			"per_deshabilitado, " + 
			"per_cod_tipotributo, " + 
			"per_fechainsercion, " + 
			"per_fechaactualizacion, " + 
			"per_usuario, " + 
			"per_cod_regimen, " + 
			"per_post_post, " + 
			"per_calidadcod, " + 
			"per_calidadmensaje, " + 
			"per_cod_fuente " + 
			"from( " + 
			"SELECT " + 
			"per_per, " + 
			"ROW_NUMBER() OVER(ORDER BY per_per) as row_number, " + 
			"tipoPersona.conf_llave as per_cod_tipopersona, " + 
			"per_nombrerazonsocial, " + 
			"per_nombrecomercial, " + 
			"tipoIdentificacion.conf_llave as per_cod_tipoidentificacion, " + 
			"per_numidentificacion, " + 
			"LISTAGG(C.CONF_LLAVE,';') WITHIN GROUP (ORDER BY C.CONF_LLAVE) as per_dv, " + 
			"per_telefono, " + 
			"per_direccionfisica, " + 
			"per_correo, " + 
			"per_direccionfiscal, " + 
			"pais.conf_llave as per_cod_pais, " + 
			"depto.conf_llave as per_cod_departamento, " + 
			"ciudad.conf_llave as per_cod_ciudad, " +
			"per_contacto, " + 
			"per_areapertenece, " + 
			"per_facturadorelectronico, " + 
			"envioFe.conf_llave as per_cod_enviofe, " + 
			"per_recibir_xml, " + 
			"per_deshabilitado, " + 
			"tributo.conf_llave as per_cod_tipotributo, " + 
			"per_fechainsercion, " + 
			"per_fechaactualizacion, " + 
			"per_usuario, " + 
			"regimen.conf_llave as per_cod_regimen, " + 
			"postal.post_codpostal as per_post_post, " + 
			"per_calidadcod, " + 
			"per_calidadmensaje, " + 
			"fuente.conf_llave as per_cod_fuente " + 
			"FROM TBL_PERSONAS p " + 
			"LEFT JOIN TBL_CONFIGURACION tipoPersona ON p.per_cod_tipopersona = tipoPersona.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION tipoIdentificacion ON p.PER_COD_TIPOIDENTIFICACION = tipoIdentificacion.conf_conf " + 
			"LEFT JOIN TBL_OBL_FISCALES_PER op ON p.PER_PER = op.OBL_PER_PER " + 
			"LEFT JOIN TBL_CONFIGURACION c ON op.obl_cod_fiscales = c.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION pais ON p.per_cod_pais = pais.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION depto ON p.per_cod_departamento = depto.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION ciudad ON p.per_cod_ciudad = ciudad.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION envioFe ON p.per_cod_enviofe = envioFe.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION tributo ON p.per_cod_tipotributo = tributo.conf_conf " + 
			"LEFT JOIN TBL_CODIGO_POSTAL postal ON p.per_post_post = postal.post_post " + 
			"LEFT JOIN TBL_CONFIGURACION fuente ON p.per_cod_fuente = fuente.conf_conf " + 
			"LEFT JOIN TBL_CONFIGURACION regimen ON p.per_cod_regimen = regimen.conf_conf " +
			"where per_calidadcod = 3 and exists (select fact_fact from tbl_facturas where fact_per_per = p.per_per) " + 
			"GROUP BY PER_PER,tipoPersona.conf_llave,per_nombrerazonsocial, " + 
			"per_nombrecomercial,tipoIdentificacion.conf_llave,per_numidentificacion, " + 
			"per_dv,per_telefono,per_direccionfisica,per_correo,per_direccionfiscal,pais.conf_llave, " + 
			"depto.conf_llave,ciudad.conf_llave,per_contacto,per_areapertenece,per_facturadorelectronico, " + 
			"envioFe.conf_llave,per_recibir_xml,per_deshabilitado,tributo.conf_llave,per_fechainsercion, " + 
			"per_fechaactualizacion,per_usuario,regimen.conf_llave, postal.post_codpostal,per_calidadcod,per_calidadmensaje, " + 
			"fuente.conf_llave " +
			") where row_number <= :limit", nativeQuery = true)
	public List<AdquirientesModel> enviarAdquirientes(@Param("limit") Integer limit);

	
	/**
	 * consulta para traer la cantidad de adquirientes que faktan
	 */
	
	@Query(value = "select distinct count(*) from (select distinct per_per from tbl_personas p LEFT JOIN TBL_OBL_FISCALES_PER op ON p.PER_PER = op.OBL_PER_PER " + 
			"LEFT JOIN TBL_CONFIGURACION c ON op.obl_cod_fiscales = c.conf_conf " + 
			"INNER JOIN TBL_FACTURAS f on f.fact_per_per = p.per_per where per_calidadcod = 3 and per_per >= :dni)", nativeQuery = true)
	public Integer cantidadFaltanteAdquirientes(@Param("dni") Integer dni);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_personas set per_calidadcod = 1 where per_per = :dni", nativeQuery = true)
    public void calidad1Adquiriente(@Param("dni") Integer dni);
	
}