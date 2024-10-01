package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.dto.PolizasDTO;
import co.com.periferia.alfa.core.model.PolizasModel;

public interface PolizasRepository  extends JpaRepository <PolizasModel, Integer>{

	@Query(value = "select * from tbl_polizas where pol_numeropoliza = ?1", nativeQuery = true)
	public List<PolizasDTO> findByPolizas(String numeropoliza);
	
	@Query(value = "select * from tbl_polizas where pol_numeropoliza = ?1", nativeQuery = true)
	public List<PolizasModel> findByPoliza(String numeropoliza);
	
	@Query(value = "select p from PolizasModel p where p.id = :poliza")
	public PolizasModel findPolizaById(@Param("poliza") Integer poliza);
	
	@Query(value = "select to_char(p.pol_pol) dni, to_char(p.pol_numeropoliza) poliza, to_char(p.pol_prima) prima, to_char(p.pol_cod_ramo) ramo, to_char(c.conf_valor) nombreRamo, to_char(p.pol_iva_primatotal) from tbl_polizas p "
			+ "inner join tbl_configuracion c on c.conf_conf = p.pol_cod_ramo "
			+ "where (pol_not_not = to_number(:nota) or to_number(:nota) is null) "
			+ "and (pol_fact_fact = to_number(:factura) or to_number(:factura) is null)", nativeQuery = true)
	public List<Object[]> findByNotaOrFactura(@Param("nota") Integer nota, @Param("factura") Integer factura);
	
	@Query(value = "select c.conf_valor tipo_documento, "
			+ "f.fact_numdoc numero_documento, "
			+ "e.env_tr_id transaccion_id, "
			+ "case when f.fact_cod_estado = 1260 "
			+ "then fact_error_1260 "
			+ "else case WHEN e.ENV_MENSAJE IS NULL THEN e.ENV_ERROR_MSG ELSE e.ENV_MENSAJE END end mensaje "
			+ "from tbl_facturas f "
			+ "inner join tbl_configuracion c on c.conf_conf = f.fact_cod_tipodocumento "
			+ "left join TBL_JSON_ENVIO e ON e.ENV_NUMDOCUMENTO = f.FACT_NUMDOC "
			+ "AND e.ENV_ENV = (SELECT MAX(e1.ENV_ENV)FROM TBL_JSON_ENVIO e1 "
			+ "WHERE e1.ENV_NUMDOCUMENTO = f.FACT_NUMDOC) "
			+ "where f.fact_fact = :dni", nativeQuery = true)
	public List<Object[]> consultaDetalleTransaccionFactura(@Param("dni") Integer dni);
	
	@Query(value = "select c.conf_valor tipo_documento, "
			+ "n.not_numdoc numero_documento, "
			+ "e.env_tr_id transaccion_id, "
			+ "case when n.not_cod_estado = 1260 "
			+ "then not_error_1260 "
			+ "else case WHEN e.ENV_MENSAJE IS NULL THEN e.ENV_ERROR_MSG ELSE e.ENV_MENSAJE END end mensaje "
			+ "from tbl_notas_debitocredito n "
			+ "inner join tbl_configuracion c on c.conf_conf = n.not_cod_tipodocumento "
			+ "left join TBL_JSON_ENVIO e ON e.ENV_NUMDOCUMENTO = n.not_NUMDOC "
			+ "AND e.ENV_ENV = (SELECT MAX(e1.ENV_ENV)FROM TBL_JSON_ENVIO e1 "
			+ "WHERE e1.ENV_NUMDOCUMENTO = n.not_NUMDOC) "
			+ "where n.not_not = :dni", nativeQuery = true)
	public List<Object[]> consultaDetalleTransaccionNota(@Param("dni") Integer dni);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_polizas set pol_calidadcod = 1 where pol_not_not = :dni", nativeQuery = true)
    public void encolarNotaPorId(@Param("dni") Integer dni);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_polizas set pol_calidadcod = 1 where pol_fact_fact = :dni", nativeQuery = true)
    public void encolarFacturaPorId(@Param("dni") Integer dni);

}
