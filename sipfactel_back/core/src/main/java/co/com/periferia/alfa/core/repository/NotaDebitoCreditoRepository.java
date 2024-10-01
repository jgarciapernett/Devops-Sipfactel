package co.com.periferia.alfa.core.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.periferia.alfa.core.model.NotaDebitoCreditoModel;

public interface NotaDebitoCreditoRepository extends JpaRepository <NotaDebitoCreditoModel, Integer>{

	@Query(value = "select * from tbl_notas_debitocredito where not_numdoc = ?1", nativeQuery = true)
	public NotaDebitoCreditoModel findByDebitoCredito(String numdoc);
	
	@Query(value = "select n.not_not, n.not_baseimponible, n.not_numdoc, n.not_total, n.not_valortributo, n.not_valorbrutoantesimpuesto, "
			+ "p.porc_porc, p.porc_tarifa "
			+ "from tbl_notas_debitocredito n inner join tbl_porcentajes p on p.porc_porc = n.not_porc_porc1 "
			+ "where n.not_not = :dni", nativeQuery = true)
	public List<Object[]> findNotaByPoliza(@Param("dni") Integer dni);
	
	@Query(value = "select n from NotaDebitoCreditoModel n where n.id = :nota")
	public NotaDebitoCreditoModel findNotaById(@Param("nota") Integer nota);
	
	@Query(value = "SELECT * FROM (SELECT TO_NUMBER(REPLACE(regexp_replace(NOT_NUMDOC, '[a-zA-Z]' ,''),chr(10),'')) AS changes "
			+ "FROM TBL_NOTAS_DEBITOCREDITO "
			+ "INNER JOIN TBL_CONFIGURACION DOC ON DOC.CONF_CONF=NOT_COD_TIPODOCUMENTO "
			+ "where NOT_SUC_SUC = ?1 and NOT_COMP_COMP = ?2 "
			+ "and NOT_NUMDOC like %?3% and DOC.CONF_VALOR = 'Nota credito' ORDER BY changes desc) WHERE ROWNUM =1", nativeQuery = true)
	public Integer findFactNC(Integer suc, Integer comp, String prefijo);
	
	@Query(value = "SELECT * FROM (SELECT TO_NUMBER(REPLACE(regexp_replace(NOT_NUMDOC, '[a-zA-Z]' ,''),chr(10),'')) AS changes "
			+ "FROM TBL_NOTAS_DEBITOCREDITO "
			+ "INNER JOIN TBL_CONFIGURACION DOC ON DOC.CONF_CONF=NOT_COD_TIPODOCUMENTO "
			+ "where NOT_SUC_SUC = ?1 and NOT_COMP_COMP = ?2 "
			+ "and NOT_NUMDOC like %?3% and DOC.CONF_VALOR = 'Nota debito' ORDER BY changes desc) WHERE ROWNUM =1", nativeQuery = true)
	public Integer findFactND(Integer suc, Integer comp, String prefijo);
	
	@Modifying
	@Transactional
    @Query(value = "update TBL_NOTAS_DEBITOCREDITO set not_numdoc = null, not_cod_estado = 1255 where not_numdoc = :numDoc", nativeQuery = true)
    public void encolarNumeracion(@Param("numDoc") String numdoc);
	
	@Modifying
	@Transactional
    @Query(value = "update TBL_NOTAS_DEBITOCREDITO set NOT_ERROR_1260 = :error where not_numdoc = :nota", nativeQuery = true)
    public void guardarError1260(@Param("error") String error, @Param("nota") String nota);
	
	@Modifying
	@Transactional
    @Query(value = "update TBL_NOTAS_DEBITOCREDITO set not_cod_estado = 1255 where not_numdoc = :numDoc", nativeQuery = true)
    public void encolarNota(@Param("numDoc") String numdoc);
	
	@Modifying
	@Transactional
    @Query(value = "update TBL_NOTAS_DEBITOCREDITO set not_cod_estado = 1255, not_calidadcod = 1 where not_not = :dni", nativeQuery = true)
    public void encolarNotaPorId(@Param("dni") Integer dni);
	
}


