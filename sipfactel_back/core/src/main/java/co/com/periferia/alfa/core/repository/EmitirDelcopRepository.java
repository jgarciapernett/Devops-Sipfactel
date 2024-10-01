package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.EmitirDelcopModel;
import co.com.periferia.alfa.core.model.JsonEnvioModel;

public interface EmitirDelcopRepository extends JpaRepository<EmitirDelcopModel, Integer> {

	@Modifying
	@Transactional
	@Query(value = "delete from tbl_json_envio where env_numdocumento = ?1 and env_error_id is null and env_intentos < 3", nativeQuery = true)
	public void delete(String numdoc);

	@Query(value = "select * from tbl_json_envio where env_env = ?1", nativeQuery = true)
	public JsonEnvioModel getOnes(Integer bigDecimal);
	
	@Query(value = "select * from tbl_json_envio where env_tr_id = ?1", nativeQuery = true)
	public EmitirDelcopModel busquedaPorTrid(Integer codigoTrid);
	
	@Query(value = "select env_json from tbl_json_envio where env_env >= :dni order by env_env asc FETCH FIRST 100 ROW ONLY", nativeQuery = true)
	public List<String> lista(@Param("dni") Integer dni);

}
