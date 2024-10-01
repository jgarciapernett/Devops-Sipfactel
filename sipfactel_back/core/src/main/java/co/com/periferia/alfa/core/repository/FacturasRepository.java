package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.FacturasModel;


public interface FacturasRepository extends JpaRepository <FacturasModel, Integer>{

	@Query(value = "select * from tbl_facturas where fact_numdoc = ?1", nativeQuery = true)
	public FacturasModel findByFacturas(String numdoc);
	
	@Query(value = "select f from FacturasModel f where f.id = :factura")
	public FacturasModel findFacturaById(@Param("factura") Integer factura);
	
	@Query(value = "SELECT * FROM (SELECT TO_NUMBER(REPLACE(regexp_replace(FACT_NUMDOC, '[a-zA-Z]' ,''),chr(10),'')) Changes "
		     + "FROM TBL_FACTURAS where FACT_SUC_SUC = ?1 and FACT_COMP_COMP = ?2 "
		     + "and FACT_NUMDOC like %?3% ORDER BY Changes desc) WHERE ROWNUM =1", nativeQuery = true)
	public Integer findFactNum(Integer suc, Integer comp, String prefijo);
	
	@Query(value = "select f.fact_fact, f.fact_baseimponible, f.fact_numdoc, f.fact_totalfactura, f.fact_valortributo, f.fact_valorbrutoantesimpuesto, "
			+ "p.porc_porc, p.porc_tarifa "
			+ "from tbl_facturas f inner join tbl_porcentajes p on p.porc_porc = f.fact_porc_porc1 "
			+ "where f.fact_fact = :dni", nativeQuery = true)
	public List<Object[]> findFactByPoliza(@Param("dni") Integer dni);

	@Modifying
	@Transactional
    @Query(value = "update tbl_facturas set fact_numdoc = null, fact_cod_estado = 1255 where fact_numdoc = :numDoc", nativeQuery = true)
    public void encolarNumeracion(@Param("numDoc") String numdoc);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_facturas set FACT_ERROR_1260 = :error where fact_numdoc = :factura", nativeQuery = true)
    public void guardarError1260(@Param("error") String error, @Param("factura") String factura);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_facturas set fact_cod_estado = 1255 where fact_numdoc = :numDoc", nativeQuery = true)
    public void encolarFactura(@Param("numDoc") String numdoc);
	
	@Modifying
	@Transactional
    @Query(value = "update tbl_facturas set fact_cod_estado = 1255, fact_calidadcod = 1 where fact_fact = :dni", nativeQuery = true)
    public void encolarFacturaPorId(@Param("dni") Integer dni);
    
	
}

