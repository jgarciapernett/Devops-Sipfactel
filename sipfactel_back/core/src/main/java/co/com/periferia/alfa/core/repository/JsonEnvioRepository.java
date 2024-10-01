package co.com.periferia.alfa.core.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.JsonEnvioModel;

public interface JsonEnvioRepository extends JpaRepository<JsonEnvioModel, Integer>{
	
	@Query(value = "select env_env, env_tr_id, env_numdocumento, env_cod_tipodocumento from tbl_json_envio where env_tr_id = :trId order by env_env desc", nativeQuery = true)
	public List<Object[]> busquedaPorTrId(@Param("trId") Integer codigoTrId);
	
	@Query(value = "select count(env_env) from tbl_json_envio where env_tr_id = :trId and env_id_documento = :documento", nativeQuery = true)
	public int busquedaPorTrIdAndDocumento(@Param("trId") Integer codigoTrId, @Param("documento") Integer documento);
	
	@Query(value = "select e from JsonEnvioModel e where id = :dni")
	public JsonEnvioModel busquedaPorId(@Param("dni") Integer dni);
	
	@Query(value = "select b.env_cod_tipodocumento, b.env_numdocumento from ( " + 
			"select max(e.env_env) dni from tbl_facturas f " + 
			"inner join tbl_json_envio e on e.env_numdocumento = f.fact_numdoc " + 
			"where f.fact_cod_Estado = 1257 " + 
			"and env_error_id <> 0 " + 
			"AND f.fact_fechaemision >= sysdate - (select valor from tbl_parametrosbck where nombre = 'DIAS_REENVIO') " + 
			"group by f.fact_numdoc) a inner join tbl_json_envio b on b.env_env = a.dni " + 
			"where(b.env_mensaje LIKE ( '%' || (select mensaje from tbl_errores_delcop where dni = :codigo) || '%' ) " + 
			"OR b.env_error_msg LIKE ( '%' || (select mensaje from tbl_errores_delcop where dni = :codigo) || '%' )) " + 
			"UNION ALL " + 
			"select b.env_cod_tipodocumento, b.env_numdocumento from ( " + 
			"select max(e.env_env) dni from tbl_notas_debitocredito n " + 
			"inner join tbl_json_envio e on e.env_numdocumento = n.not_numdoc " + 
			"where n.not_cod_Estado = 1257 " + 
			"and env_error_id <> 0 " + 
			"AND n.not_fechaemision >= sysdate - (select valor from tbl_parametrosbck where nombre = 'DIAS_REENVIO') " + 
			"group by n.not_numdoc) a inner join tbl_json_envio b on b.env_env = a.dni " + 
			"where(b.env_mensaje LIKE ( '%' || (select mensaje from tbl_errores_delcop where dni = :codigo) || '%' ) " + 
			"OR b.env_error_msg LIKE ( '%' || (select mensaje from tbl_errores_delcop where dni = :codigo) || '%' ))", nativeQuery = true)
	public List<Object[]> consultaReenvioFacturasNotas(@Param("codigo") int codigo);
	
	@Query(value = "select env_id_documento, env_cod_tipodocumento "
			+ "from tbl_json_envio "
			+ "where env_env = (select max(env_env) "
			+ "from tbl_json_envio where env_tr_id = :trid)", nativeQuery = true)
	public List<Object[]> getDocumento(@Param("trid") Integer trid);
	
}
