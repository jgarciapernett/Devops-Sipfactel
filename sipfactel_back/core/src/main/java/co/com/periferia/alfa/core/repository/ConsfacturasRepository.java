package co.com.periferia.alfa.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.ConsFacturasModel;

public interface ConsfacturasRepository extends JpaRepository<ConsFacturasModel, Integer> {
	
	@Query(value = "select * from tbl_facturas where fact_numdoc = ?1", nativeQuery = true)
	public ConsFacturasModel findbyfactura(String numdoc);	
	
	@Query(value = "select * from tbl_facturas f inner join tbl_notas_debitocredito dc on dc.not_fact_fact = f.fact_fact where dc.not_numdoc = ?1", nativeQuery = true)
	public ConsFacturasModel findbyNdNc(String numdoc);	
	
	@Query(value = "select * from tbl_facturas f inner join tbl_poliza p on p.pol_pol = f.fact_pol_pol where p.pol_numeropoliza = ?1", nativeQuery = true)
	public ConsFacturasModel findbyPoliza(String numdoc);	
}