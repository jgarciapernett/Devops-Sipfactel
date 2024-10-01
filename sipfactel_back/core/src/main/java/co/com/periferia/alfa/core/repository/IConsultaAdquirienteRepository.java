package co.com.periferia.alfa.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.ConsultaAdquirientesModel;

public interface IConsultaAdquirienteRepository extends JpaRepository<ConsultaAdquirientesModel, Integer> {

	@Query(value = "select p.per_per dni, "
			+ "tipoPersona.conf_valor tipo_persona, "
			+ "p.per_nombrerazonsocial razon_social, "
			+ "p.per_numidentificacion identificacion, "
			+ "tipoIdentificacion.conf_valor tipo_identificacion, "
			+ "p.per_cod_pais pais, "
			+ "p.per_cod_ciudad ciudad, "
			+ "p.per_cod_departamento departamento, "
			+ "p.per_direccionfisica direccion, "
			+ "p.per_post_post codigo_postal, "
			+ "p.per_contacto contacto, "
			+ "p.per_correo correo, "
			+ "p.per_telefono telefono, "
			+ "p.per_areapertenece area_pertenece, "
			+ "p.per_facturadorelectronico facturador_electronico, "
			+ "p.per_cod_enviofe autoriza_entrega, "
			+ "p.per_recibir_xml recibe_xml, "
			+ "p.per_deshabilitado deshabilitado, "
			+ "p.per_cod_tipotributo tributo, "
			+ "p.per_cod_regimen regimen "
			+ "from tbl_personas p "
			+ "inner join tbl_configuracion tipoPersona on tipoPersona.conf_conf = p.per_cod_tipopersona "
			+ "inner join tbl_configuracion tipoIdentificacion on tipoIdentificacion.conf_conf = p.per_cod_tipoidentificacion "
			+ "where p.per_per = :dni", nativeQuery = true)
	public ConsultaAdquirientesModel consulta(@Param("dni") Integer dni);
	
}
