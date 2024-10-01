package co.com.periferia.alfa.core.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.com.periferia.alfa.core.dto.DetalleAdquirientesDTO;
import co.com.periferia.alfa.core.dto.DetalleFacturasDTO;
import co.com.periferia.alfa.core.dto.PolizaErrorEtlDTO;
import co.com.periferia.alfa.core.model.DetalleAdquirientesModel;
import co.com.periferia.alfa.core.model.DetalleFacturasModel;
import co.com.periferia.alfa.core.model.PolizaErrorEtlModel;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.MapperUtil;

@Repository
public class TablaPolizasRepository {

	@Autowired
	MapperUtil mapper;
	
	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger LOG = LoggerFactory.getLogger(TablaPolizasRepository.class);

	private static final String CURSOR = "c_Resp";

	// SP POLIZAS ERROR ETL

	@SuppressWarnings("unchecked")
	public List<PolizaErrorEtlDTO> getFiltroPolizasErrorEtl(String numpoliza, String numdoc, Integer categoria) {
		LOG.info("Ingreso a metodo getFiltroPolizasErrorEtl, parametros: numpoliza {} | numdoc {} | categoria {} ",
				numpoliza, numdoc, categoria);
		List<PolizaErrorEtlModel> result = new ArrayList<>();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_POLIZA_ERRORETL",
					PolizaErrorEtlModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(FacEnum.NUMPOLIZA.getValor(), String.class,
					ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.NUMDOC.getValor(), String.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.CATEGORIA.getValor(), int.class, ParameterMode.IN);
			procedureQuery.setParameter(FacEnum.NUMPOLIZA.getValor(), numpoliza);
			procedureQuery.setParameter(FacEnum.NUMDOC.getValor(), numdoc);
			procedureQuery.setParameter(FacEnum.CATEGORIA.getValor(), categoria);
			procedureQuery.execute();
			result = procedureQuery.getResultList();
		} catch (Exception e) {
			return new ArrayList<>();
		}
		
		return mapper.mapAll(result, PolizaErrorEtlDTO.class);
	}

	// SP DETALLEFACTURA ETL
	public DetalleFacturasDTO getDetalleFacturaEtl(String numpoliza, Integer idtabla, String tabla) {
		LOG.info("Ingreso a metodo getDetalleFacturaEtl, parametros: numpoliza {} | idtabla {} | tabla {} ", numpoliza, idtabla, tabla);
		DetalleFacturasModel result = new DetalleFacturasModel();
		DetalleFacturasDTO resultado = new DetalleFacturasDTO();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_DETALLEFACTURAETL",
					DetalleFacturasModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(FacEnum.NUMPOLIZA.getValor(), String.class,
					ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.IDTABLA.getValor(), int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.TABLA.getValor(), String.class, ParameterMode.IN);
			procedureQuery.setParameter(FacEnum.NUMPOLIZA.getValor(), numpoliza);
			procedureQuery.setParameter(FacEnum.IDTABLA.getValor(), idtabla);
			procedureQuery.setParameter(FacEnum.TABLA.getValor(), tabla);
			procedureQuery.execute();
			result = (DetalleFacturasModel) procedureQuery.getSingleResult();
			resultado = resultado.modeloAdto(result);
		} catch (Exception e) {
			return new DetalleFacturasDTO();
		}
		return resultado;
	}

	// SP CONSULTA ADQUIRIENTES
	public DetalleAdquirientesDTO getFiltroAdqurientes(String numpoliza, String tipoMovimiento) {
		LOG.info("Ingreso a metodo getFiltroAdqurientes, parametros: numpoliza {} | tipoMovimiento {} ", numpoliza, tipoMovimiento);
		DetalleAdquirientesModel resultList = new DetalleAdquirientesModel();
		DetalleAdquirientesDTO result = new DetalleAdquirientesDTO();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_CONSULTA_ADQUIRIENTES",
					DetalleAdquirientesModel.class);
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			procedureQuery.registerStoredProcedureParameter(FacEnum.NUMPOLIZA.getValor(), String.class,
					ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.TIPOMOV.getValor(), String.class, ParameterMode.IN);
			procedureQuery.setParameter(FacEnum.NUMPOLIZA.getValor(), numpoliza);
			procedureQuery.setParameter(FacEnum.TIPOMOV.getValor(), tipoMovimiento);
			procedureQuery.execute();
			resultList = (DetalleAdquirientesModel) procedureQuery.getSingleResult();
			result = result.modeloAdto(resultList);

		} catch (Exception e) {
			return new DetalleAdquirientesDTO();
		}
		return result;
	}
	
	/**
	 * Metodo que ejecuta los sp de EDITAR o EDITARPOLIZA_ERRORETL
	 * @param tipo - boolean que define que sp se ejecutara, true = SP_EDITAR, false = SP_EDITARPOLIZA_ERRORETL
	 * @param dto - Clase dto que tiene la informacion a editar
	 */
	
	public void editarDetalleOrErrorEtl(boolean tipo, DetalleFacturasDTO dto) {
		LOG.info("Ingreso a metodo editarDetalleAndErrorEtl, parametros: tipo {} | dto {} ", tipo, dto);
		StoredProcedureQuery procedureQuery = null;
		String facPol = null;
		try {
			if(tipo) {
				procedureQuery = entityManager.createStoredProcedureQuery("SP_EDITAR");	
				facPol = FacEnum.FAC.getValor();
			} else {
				procedureQuery = entityManager.createStoredProcedureQuery("SP_EDITARPOLIZA_ERRORETL");
				facPol = FacEnum.POL.getValor();
			}
			
			procedureQuery.registerStoredProcedureParameter(facPol, int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.DETFAC.getValor(), int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.TIPODOC.getValor(), int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.DESCRIPCIONSERVICIO.getValor(), String.class,ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.VALORSERVICIO.getValor(), int.class,ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.VALORBRUTO.getValor(), int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.TARIFA.getValor(), int.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.BASEIMPONIBLE.getValor(), int.class,ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.VALORTRIBUTO1.getValor(), int.class,ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.TOTALFACTURA.getValor(), int.class,ParameterMode.IN);
			procedureQuery.setParameter(facPol, dto.getId());
			procedureQuery.setParameter(FacEnum.DETFAC.getValor(), dto.getIdDet());
			procedureQuery.setParameter(FacEnum.TIPODOC.getValor(), dto.getTipodoc());
			procedureQuery.setParameter(FacEnum.DESCRIPCIONSERVICIO.getValor(), dto.getDescripcion());
			procedureQuery.setParameter(FacEnum.VALORSERVICIO.getValor(), dto.getValor());
			procedureQuery.setParameter(FacEnum.VALORBRUTO.getValor(), dto.getValoriva());
			procedureQuery.setParameter(FacEnum.TARIFA.getValor(), dto.getTasaiva());
			procedureQuery.setParameter(FacEnum.BASEIMPONIBLE.getValor(), dto.getSubtotalvalor());
			procedureQuery.setParameter(FacEnum.VALORTRIBUTO1.getValor(), dto.getSubtotaliva());
			procedureQuery.setParameter(FacEnum.TOTALFACTURA.getValor(), dto.getTotal());
			procedureQuery.execute();	
		} catch (NoResultException | NullPointerException | IllegalArgumentException e) {
			LOG.error("Ha ocurrido un error al intentar editar: error = {} | {}", e.getMessage(), e.getStackTrace());
		}
		
	}
}
