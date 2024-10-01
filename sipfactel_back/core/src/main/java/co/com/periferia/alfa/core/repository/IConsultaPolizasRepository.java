package co.com.periferia.alfa.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.ConsultaPolizasEntity;

public interface IConsultaPolizasRepository extends JpaRepository<ConsultaPolizasEntity, Integer> {

	@Query(value = "select f.fact_fact dni, "
			+ "f.fact_per_per adquiriente, "
			+ "f.fact_numdoc numero_documento, "
			+ "f.fact_numeropoliza numero_poliza, "
			+ "cTipo.conf_valor tipo_documento, "
			+ "f.FACT_FECHAEMISION AS fecha_emision, "
			+ "to_date(substr(f.fact_fechora_emision, 1, 10), 'DD/MM/YY') fecha_envio, "
			+ "cEstado.conf_valor estado, "
			+ "case WHEN j.ENV_MENSAJE IS NULL THEN j.ENV_ERROR_MSG ELSE j.ENV_MENSAJE end causa "
			+ "from tbl_facturas f "
			+ "inner join tbl_configuracion cTipo on cTipo.conf_conf = f.fact_cod_tipodocumento "
			+ "inner join tbl_configuracion cEstado on cEstado.conf_conf = f.fact_cod_estado "
			+ "LEFT JOIN TBL_JSON_ENVIO j ON j.ENV_NUMDOCUMENTO = f.FACT_NUMDOC "
			+ "                    AND j.ENV_ENV = (SELECT MAX(j1.ENV_ENV)FROM TBL_JSON_ENVIO j1 "
			+ "                                       WHERE j1.ENV_NUMDOCUMENTO = f.FACT_NUMDOC) "
			+ "WHERE (f.FACT_COD_ESTADO IN (1256,1257,1258) or (f.FACT_COD_ESTADO = 1255 and f.FACT_NUMDOC IS NULL))"
			+ "AND (f.FACT_NUMEROPOLIZA = :numPoliza or :numPoliza is null) "
			+ "AND (f.FACT_NUMDOC = :numdoc or :numdoc is null) "
			+ "AND ((to_date(substr(f.fact_fechora_emision, 1, 10), 'DD/MM/YY') BETWEEN TO_DATE(:fIniEnvio,'DD/MM/YY') AND TO_DATE(:fFinEnvio,'DD/MM/YY')) or (:fIniEnvio is null or :fFinEnvio is null)) "
			+ "AND ((f.FACT_FECHAEMISION BETWEEN TO_DATE(:fIniEmision,'DD/MM/YY') AND TO_DATE(:fFinEmision,'DD/MM/YY')) or (:fIniEmision is null or :fFinEmision is null)) "
			+ "union all "
			+ "select n.not_not dni, "
			+ "n.not_per_per adquiriente, "
			+ "n.not_numdoc numero_documento, "
			+ "n.not_numeropoliza numero_poliza, "
			+ "cTipo.conf_valor tipo_documento, "
			+ "n.not_FECHAEMISION AS fecha_emision, "
			+ "to_date(substr(n.not_fechora_emision, 1, 10), 'DD/MM/YY') fecha_envio, "
			+ "cEstado.conf_valor estado, "
			+ "case WHEN j.ENV_MENSAJE IS NULL THEN j.ENV_ERROR_MSG ELSE j.ENV_MENSAJE end causa "
			+ "from tbl_notas_debitocredito n "
			+ "inner join tbl_configuracion cTipo on cTipo.conf_conf = n.not_cod_tipodocumento "
			+ "inner join tbl_configuracion cEstado on cEstado.conf_conf = n.not_cod_estado "
			+ "LEFT JOIN TBL_JSON_ENVIO j ON j.ENV_NUMDOCUMENTO = n.not_NUMDOC "
			+ "                    AND j.ENV_ENV = (SELECT MAX(j1.ENV_ENV)FROM TBL_JSON_ENVIO j1 "
			+ "                                       WHERE j1.ENV_NUMDOCUMENTO = n.not_NUMDOC) "
			+ "WHERE (n.not_COD_ESTADO IN (1256,1257,1258) or (n.not_COD_ESTADO = 1255 and n.not_NUMDOC IS NULL)) "
			+ "AND (n.NOT_NUMEROPOLIZA = :numPoliza or :numPoliza is null) "
			+ "AND (n.NOT_NUMDOC = :numdoc or :numdoc is null) "
			+ "AND ((to_date(substr(n.not_fechora_emision, 1, 10), 'DD/MM/YY') BETWEEN TO_DATE(:fIniEnvio,'DD/MM/YY') AND TO_DATE(:fFinEnvio,'DD/MM/YY')) or (:fIniEnvio is null or :fFinEnvio is null)) "
			+ "AND ((n.not_FECHAEMISION BETWEEN TO_DATE(:fIniEmision,'DD/MM/YY') AND TO_DATE(:fFinEmision,'DD/MM/YY')) or (:fIniEmision is null or :fFinEmision is null))", 
			countQuery = "select (select count(f.fact_fact) cont "
					+ "from tbl_facturas f "
					+ "WHERE (f.FACT_COD_ESTADO IN (1256,1257,1258) or (f.FACT_COD_ESTADO = 1255 and f.FACT_NUMDOC IS NULL)) "
					+ "AND (f.FACT_NUMEROPOLIZA = :numPoliza or :numPoliza is null) "
					+ "AND (f.FACT_NUMDOC = :numdoc or :numdoc is null) "
					+ "AND ((to_date(substr(f.fact_fechora_emision, 1, 10), 'DD/MM/YY') BETWEEN TO_DATE(:fIniEnvio,'DD/MM/YY') AND TO_DATE(:fFinEnvio,'DD/MM/YY')) or (:fIniEnvio is null or :fFinEnvio is null)) "
					+ "AND ((f.FACT_FECHAEMISION BETWEEN TO_DATE(:fIniEmision,'DD/MM/YY') AND TO_DATE(:fFinEmision,'DD/MM/YY')) or (:fIniEmision is null or :fFinEmision is null))) "
					+ "+ "
					+ "(select count(n.not_not) cont "
					+ "from tbl_notas_debitocredito n "
					+ "WHERE (n.not_COD_ESTADO IN (1256,1257,1258) or (n.not_COD_ESTADO = 1255 and n.not_NUMDOC IS NULL)) "
					+ "AND (n.NOT_NUMEROPOLIZA = :numPoliza or :numPoliza is null) "
					+ "AND (n.NOT_NUMDOC = :numdoc or :numdoc is null) "
					+ "AND ((to_date(substr(n.not_fechora_emision, 1, 10), 'DD/MM/YY') BETWEEN TO_DATE(:fIniEnvio,'DD/MM/YY') AND TO_DATE(:fFinEnvio,'DD/MM/YY')) or (:fIniEnvio is null or :fFinEnvio is null)) "
					+ "AND ((n.not_FECHAEMISION BETWEEN TO_DATE(:fIniEmision,'DD/MM/YY') AND TO_DATE(:fFinEmision,'DD/MM/YY')) or (:fIniEmision is null or :fFinEmision is null))) valor from dual",
			nativeQuery = true)
	public Page<ConsultaPolizasEntity> consulta(
			@Param("numPoliza") String numPoliza, 
			@Param("numdoc") String numdoc, 
			@Param("fIniEnvio") String fIniEnvio, 
			@Param("fFinEnvio") String fFinEnvio, 
			@Param("fIniEmision") String fIniEmision, 
			@Param("fFinEmision") String fFinEmision,
			Pageable page);
	
}
