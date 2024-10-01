package co.com.periferia.alfa.core.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.ResolucionModel;

public interface ResolucionRepository extends JpaRepository<ResolucionModel, Integer>{
	
	@Query(value = "select * from tbl_resolucion where res_suc_suc = ?1 and res_comp_comp = ?2 and res_estado = 'TRUE'", nativeQuery = true)
	public ResolucionModel findByRes(Integer suc, Integer comp);

	public List<ResolucionModel> findAllByFffinGreaterThanEqualAndFfiniLessThanEqual(Date date, Date dateTwo);
	
	public List<ResolucionModel> findAllByEstado(String estado);
	
	@Query(value = "WITH t AS (select " + 
			"res_res, " + 
			"res_suc_suc, " + 
			"res_comp_comp, " + 
			"res_fnresol, " + 
			"res_fprefijo, " + 
			"res_ffini, " + 
			"res_fffin, " + 
			"res_fnumini, " + 
			"res_fnumfin, " + 
			"res_fcreacion, " + 
			"res_ucreacion, " + 
			"res_factura_cont, " +
			"res_factualiza, " +
			"res_uactualiza, " +
			"res_estado, " + 
			"res_trtipoid, " + 
			"auditoria, " + 
			"res_producto " +
			"from tbl_resolucion where res_suc_suc = :sucursal and res_comp_comp = :compania and res_producto = :producto " + 
			"and auditoria = 'FALSE' AND (res_ffini >= sysdate or to_date(sysdate, 'DD/MM/YY') between res_ffini and res_fffin) " + 
			"order by res_estado desc, res_ffini asc ) " + 
			"select * " + 
			"From t " + 
			"union all " + 
			"select " + 
			"res_res, " + 
			"res_suc_suc, " + 
			"res_comp_comp, " + 
			"res_fnresol, " + 
			"res_fprefijo, " + 
			"res_ffini, " + 
			"res_fffin, " + 
			"res_fnumini, " + 
			"res_fnumfin, " + 
			"res_fcreacion, " + 
			"res_ucreacion, " + 
			"res_factura_cont, " + 
			"res_factualiza, " +
			"res_uactualiza, " +
			"res_estado, " + 
			"res_trtipoid, " + 
			"auditoria, " + 
			"res_producto " +
			"From tbl_resolucion where res_suc_suc = :sucursal and res_comp_comp = :compania and res_producto = :producto " + 
			"and auditoria = 'FALSE' AND res_fffin < to_date(sysdate, 'DD/MM/YY')", nativeQuery = true)
	public List<ResolucionModel> findBySucursalAndCompaniaAndProducto(@Param("sucursal") Integer sucursal, @Param("compania") Integer compania, @Param("producto") String producto);
	
	@Query(value = "select " +
			"fecha_fin + 2, " +
			"numero_fin + 1 " +
			"from (" + 
			"select ROW_NUMBER() OVER(ORDER BY res_fffin desc) as row_number, res_fffin as fecha_fin, res_fnumfin as numero_fin " + 
			"from tbl_resolucion where res_suc_suc = :sucursal and res_comp_comp = :compania and res_producto = :product and auditoria = 'FALSE'" + 
			") where row_number = 1", nativeQuery = true)
	public List<String[]> findByLastResolucion(@Param("sucursal") Integer sucursal, @Param("compania") Integer compania, @Param("product") String product);
	
	@Query(value = "select res_res from tbl_resolucion where res_suc_suc = :sucursal and res_comp_comp = :compania and res_producto = :product and res_fnresol = :resolucion and auditoria = 'FALSE' and rownum = 1", nativeQuery = true)
    public Integer buscarCoincidencias(@Param("sucursal") Integer sucursal, @Param("compania") Integer compania, @Param("product") String product, @Param("resolucion") String resolucion);
	
	@Query(value = "select res_res, to_char(res_ffini, 'DD/MM/YYYY'), to_char(res_fffin, 'DD/MM/YYYY'), res_fnumfin, res_factura_cont " + 
			"from tbl_resolucion " + 
			"where auditoria = 'FALSE' " + 
			"and to_date(res_fffin) = to_date(sysdate - 1) " + 
			"or to_date(res_ffini) = to_date(sysdate)", nativeQuery = true)
	public List<String[]> busquedaParaHablitarVigencia();
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_resolucion set res_estado = 'FALSE' " + 
			"where res_estado = 'TRUE' " + 
			"and auditoria = 'FALSE' " + 
			"and to_date(res_fffin) < to_date(sysdate) " + 
			"or res_fnumfin = res_factura_cont", nativeQuery = true)
	public void inactivarResolucionesVencidas();
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_resolucion set res_estado = 'TRUE' " + 
			"where res_estado = 'FALSE' " + 
			"and auditoria = 'FALSE' " + 
			"and to_date(sysdate) between to_date(res_ffini) and to_date(res_fffin)", nativeQuery = true)
	public void activarResolucionesVigentes();
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_resolucion set res_estado = :estado where res_res = :codigo", nativeQuery = true)
	public void actualizarVigencia(@Param("estado") String estado, @Param("codigo") Integer codigo);
	
	@Query(value = "select count(*) from tbl_facturas f " + 
			"inner join tbl_resolucion r " + 
			"on f.fact_comp_comp = r.res_comp_comp " + 
			"and f.fact_suc_suc = r.res_suc_suc " + 
			"where f.fact_fact = :dni " + 
			"and res_estado = 'TRUE' " +
			"and SYSDATE between r.res_ffini and r.res_fffin " + 
			"and regexp_replace(fact_numdoc, '[^0-9]') between r.res_fnumini and r.res_fnumfin", nativeQuery = true)
    public Integer validarVigenciaEmisionFacturas (@Param("dni") Integer dni);
	
	@Query(value = "select count(*) from tbl_resolucion where res_trtipoid = :trId", nativeQuery = true)
	public Integer validarExistenciaTrIdFactura(@Param("trId") Integer trId);
	
	@Query(value = "select  * from tbl_resolucion where res_estado = 'TRUE' and to_date(res_fffin) between to_date(sysdate) and to_date(sysdate) + :diasLimite", nativeQuery = true)
	public List<ResolucionModel> buscarPorEstadoYVigencia(@Param("diasLimite") int diasLimite);
	
	@Transactional
	@Modifying
	@Query(value = "update tbl_resolucion set res_estado = 'ELI', auditoria = 'TRUE' where res_res = :llave", nativeQuery = true)
	public void eliminarResolucion(@Param("llave") int llave);
}








