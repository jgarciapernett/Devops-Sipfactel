package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.ResolucionNotasModel;

public interface IResolucionNotasRepository extends JpaRepository<ResolucionNotasModel, Integer> {

	@Query(value = "select r " +
			  "from ResolucionNotasModel r " +
			  "where r.sucursal = :sucursal " +
			  "and r.compania = :compania " +
			  "and r.producto = :producto " +
			  "and r.estado = 'TRUE'")
	public List<ResolucionNotasModel> consultarResolucionNotaVigente(@Param("sucursal") Integer sucursal, 
			@Param("compania") Integer compania,
			@Param("producto") String producto);
	
	@Query(value = "select r " +
			  "from ResolucionNotasModel r " +
			  "where r.sucursal = :sucursal " +
			  "and r.compania = :compania " +
			  "and r.producto = :producto " +
			  "and r.estado = 'FALSE' " +
			  "and r.auditoria = 'FALSE' " +
			  "and r.contador = 0 " +
			  "order by r.dni")
	public List<ResolucionNotasModel> consultarResolucionNotaFutura(@Param("sucursal") Integer sucursal, 
			@Param("compania") Integer compania,
			@Param("producto") String producto);
	
	@Query(value = "select r " +
			  "from ResolucionNotasModel r " +
			  "where r.estado = 'FALSE' " +
			  "and r.auditoria = 'FALSE' " +
			  "and r.contador = 0 " +
			  "order by r.dni")
	public List<ResolucionNotasModel> consultarTodasResolucionNotaFutura();
	
	@Query(value = "select * from tbl_resolucion_notas where resnot_dni = ( " + 
			"select min(resnot_dni) from tbl_resolucion_notas " + 
			"where resnot_compania = :compania " + 
			"and resnot_sucursal = :sucursal " + 
			"and resnot_producto = :producto " + 
			"and resnot_estado = 'FALSE' " + 
			"and resnot_auditoria = 'FALSE' " + 
			"and resnot_contador = 0 " + 
			"and resnot_tipo_nota = :tipoNota)", nativeQuery = true)
	public ResolucionNotasModel consultarResolucionNotaFuturaPorTipoNota(@Param("sucursal") Integer sucursal, 
			@Param("compania") Integer compania,
			@Param("producto") String producto,
			@Param("tipoNota") String tipoNota);
	
	@Query(value = "select r " +
			  "from ResolucionNotasModel r " +
			  "where r.sucursal = :sucursal " +
			  "and r.compania = :compania " +
			  "and r.producto = :producto " +
			  "and r.estado = 'FALSE' " +
			  "and r.auditoria = 'FALSE' " +
			  "and r.numeroFinal = r.contador " +
			  "order by r.dni")
	public List<ResolucionNotasModel> consultarResolucionNotaVencida(@Param("sucursal") Integer sucursal, 
			@Param("compania") Integer compania,
			@Param("producto") String producto);
	
	@Query(value = "select count(resnot_dni) from TBL_RESOLUCION_NOTAS where RESNOT_TRTIPOID = :trId and resnot_tipo_nota = :tipoNota", nativeQuery = true)
	public Integer consultarTrIdExistente(@Param("trId") Integer trId, @Param("tipoNota") String tipoNota);
	
	@Query(value = "select count(resnot_dni) from TBL_RESOLUCION_NOTAS where RESNOT_TRTIPOID = :trId", nativeQuery = true)
	public Integer consultarTrIdExistenteNotas(@Param("trId") Integer trId);
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_resolucion_notas set resnot_estado = 'ELI', resnot_auditoria = 'TRUE' where resnot_dni = :idResolucion", nativeQuery = true)
	public void eliminarResolucionNotas(@Param("idResolucion") int idResolucion);
	
    public List<ResolucionNotasModel> findAllByEstado(String estado);
    
	/**
	 * Actualizacion de resolciones vencidas a estado false
	 */
	
    @Transactional
	@Modifying
	@Query(value = "update tbl_resolucion_notas " +
			"set resnot_estado = 'FALSE', " +
			"resnot_auditoria = 'FALSE' " +
			"where resnot_estado = 'TRUE' " +
			"and resnot_numero_final = resnot_contador", nativeQuery = true)
	public void actualizarEstadosVencidos();
	
	/**
	 * Consulta que busca las resoluciones que esten activas para impedir que otra sea activada
	 */
	
	@Query(value = "select count(r) from ResolucionNotasModel r " + 
			"where r.sucursal = :sucursal " +
			"and r.compania = :compania " +
			"and r.producto = :producto " +
			"and r.estado = 'TRUE' " +
			"and r.tipoNota = :tipoNota")
	public int consultarVigenciaExistente(@Param("sucursal") int sucursal, 
			@Param("compania") int compania, 
			@Param("producto") String producto,
			@Param("tipoNota") String tipoNota);
	
	
}
