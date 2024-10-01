package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.DesplegablesModel;

public interface DesplegablesRepository extends JpaRepository<DesplegablesModel, Integer> {

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Formas de pago' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByFormaPago();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Tipo Persona' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByTipoPersona();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Tipo de Identificaci\u00f3n' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByTipoIdentificacion();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Responsabilidades Fiscales' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByOblFiscales();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Paises' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByPaises();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Ciudades' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByCiudades();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Departamentos' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByDepartamentos();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Tipos de tributos' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByCodTributo();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'R\u00e9gimen Fiscal' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByRegfiscal();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Unidades de medida' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByUmedidas();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Productos' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByProductos();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Envio FE' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByEnvioFE();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Ambiente de destino' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByAmbDestino();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Monedas' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByMonedas();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Medios de Pago' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByMediosPago();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Tipos de Documentos' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByTiposDoc();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Estado Factura' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByEstadoFac();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_sistema = 'Tipos de operaci\u00f3n' and conf_padre is not null and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public List<DesplegablesModel> findByTipoOperacion();

	@Query(value = "select conf_conf, conf_valor from tbl_configuracion where conf_conf = ?1 and conf_estado = 'ACT' ORDER BY conf_valor", nativeQuery = true)
	public DesplegablesModel findByobl(Integer id);

}
