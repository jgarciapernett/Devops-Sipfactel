package co.com.periferia.alfa.core.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import co.com.periferia.alfa.core.dto.TablaReporteContableDTO;
import co.com.periferia.alfa.core.dto.TablaReporteEstadoDTO;
import co.com.periferia.alfa.core.model.ReporteContableModel;
import co.com.periferia.alfa.core.model.TablaReporteContableModel;
import co.com.periferia.alfa.core.model.TablaReporteEstadoModel;
import co.com.periferia.alfa.core.utilitarios.FacEnum;

@Repository
public class ReportesRepository {

	public static final Logger LOG = LoggerFactory.getLogger(ReportesRepository.class);

	@PersistenceContext
	private EntityManager entityManager;

	private static final String CURSOR = "c_Resp";
	private static final String REPORTE_TOTALES = "SP_REPORTE_TOTALES";
	private static final String REPORTE_DETALLE = "SP_REPORTE_DETALLE"; 
	private static final String REPORTE_ESTADO = "SP_REPORTEESTADO";

	/*
	 * SP REPORTE CONTABLE
	 */
	@SuppressWarnings("unchecked")
	public List<TablaReporteContableDTO> getFiltroReporteContable(String fechaini, String fechafin, Integer tipodoc,
			String numdoc, String numpoliza) {
		LOG.info("Entro al metodo getFiltroReporteContable del reposiorio de reportes");
		List<TablaReporteContableModel> result = new ArrayList<>();
		List<TablaReporteContableDTO> resultado = new ArrayList<>();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(REPORTE_TOTALES,
					TablaReporteContableModel.class);			
			String[] parametros = new String[4];
			parametros[0] = fechaini;
			parametros[1] = fechafin;
			parametros[2] = numdoc;
			parametros[3] = numpoliza;
			
			result = spReportes(procedureQuery, REPORTE_TOTALES, tipodoc, parametros).getResultList();
			
			resultado = result.stream().map(e -> new TablaReporteContableDTO().modeloAdto(e))
					.collect(Collectors.toList());
		} catch (IllegalArgumentException | NoResultException | NullPointerException e) {
			LOG.warn("Ha ocurrido un error en el metodo getFiltroReporteContable, error = {} | {} ", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		}
		return resultado;
	}

	/*
	 * SP REPORTE CONTABLE EXCEL
	 */
	@SuppressWarnings("unchecked")
	public List<ReporteContableModel> reporteDetalle(String fechaini, String fechafin, Integer tipodoc, String numdoc,
			String numpoliza, String compania) {
		LOG.info("Entro al metodo reporteDetalle del reposiorio de reportes");
		List<ReporteContableModel> result = new ArrayList<>();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(REPORTE_DETALLE,
					ReporteContableModel.class);			
			String[] parametros = new String[5];
			parametros[0] = fechaini;
			parametros[1] = fechafin;
			parametros[2] = numdoc;
			parametros[3] = numpoliza;
			parametros[4] = compania;
			
			result = spReportes(procedureQuery, REPORTE_DETALLE, tipodoc, parametros).getResultList();
			
		} catch (IllegalArgumentException | NoResultException | NullPointerException e) {
			LOG.warn("Ha ocurrido un error en el metodo reporteDetalle, error = {} | {} ", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		}
		return result;
	}

	/**
	 * SP REPORTE ESTADO
	 * 
	 * @param estado    (1255 descargado, 1256 enviado, 1257 rechazado por titanio,
	 *                  1258 remediado, 1259 aceptado, 1260 rechazado por la dian
	 * @param fechaini
	 * @param fechafin
	 * @param numpoliza
	 * @param numdoc    Numero de la transaccion
	 * @param compania  Codigo de la compania
	 * @param suscursal Codigo de la suscursal
	 */
	public List<TablaReporteEstadoDTO> getFiltroReporteEstado(Integer estado, String fechaini, String fechafin,
			String numpoliza, String numdoc, String compania, String sucursal) {
		LOG.info("Entro al metodo getFiltroReporteEstado del reposiorio de reportes");
		List<TablaReporteEstadoModel> result = reporteEstado(estado, fechaini, fechafin, numpoliza, numdoc, compania, sucursal);
		List<TablaReporteEstadoDTO> resultado = new ArrayList<>();
		try {
			resultado = result.stream().map(e -> new TablaReporteEstadoDTO().modeloAdto(e))
					.collect(Collectors.toList());
			LOG.info("finalizo consulta");
		} catch (IllegalArgumentException | NoResultException | NullPointerException e) {
			LOG.warn("Ha ocurrido un error en el metodo getFiltroReporteEstado, error = {} | {} ", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		}
		return resultado;
	}

	/*
	 * SP REPORTE ESTADO EXCEL
	 */
	@SuppressWarnings("unchecked")
	public List<TablaReporteEstadoModel> reporteEstado(Integer estado, String fechaini, String fechafin,
			String numpoliza, String numdoc, String compania, String sucursal) {
		LOG.info("Entro al metodo reporteEstado del reposiorio de reportes");
		List<TablaReporteEstadoModel> result = new ArrayList<>();
		try {
			StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery(REPORTE_ESTADO,
					TablaReporteEstadoModel.class);			
			String[] parametros = new String[6];
			parametros[0] = fechaini;
			parametros[1] = fechafin;
			parametros[2] = numdoc;
			parametros[3] = numpoliza;
			parametros[4] = compania;
			parametros[5] = sucursal;
			
			result = spReportes(procedureQuery, REPORTE_ESTADO, estado, parametros).getResultList();
		} catch (IllegalArgumentException | NoResultException | NullPointerException e) {
			LOG.warn("Ha ocurrido un error en el metodo reporteEstado, error = {} | {} ", e.getMessage(), e.getStackTrace());
			return new ArrayList<>();
		}
		return result;
	}

	/**
	 * Metodo que implementa la logica del stored procedure query
	 * @param procedureQuery - StoredProcedureQuery que ya viene con el sp que llama y la clase a la que se mapea
	 * @param sp - nombre del sp que usara el procedure query
	 * @param estadoTipoDoc - Integer que puede ser el estado o el tipo doc, dependiendo de que sp lo use
	 * @param parametros - arreglo con los parametros a enviar al sp, 0 = fechaIni, 1 = fechaFin, 2 = numDoc, 3 = numPoliza, 4 = compania, 5 = sucursal
	 * @return StoredProcedureQuery - retorna el query ejecutado
	 */
	
	private StoredProcedureQuery spReportes(StoredProcedureQuery procedureQuery, String sp, Integer estadoTipoDoc, String[] parametros) {
		LOG.info("Inicio consulta de procedimiento: {} ", sp);
		
		try {
			procedureQuery.registerStoredProcedureParameter(CURSOR, void.class, ParameterMode.REF_CURSOR);
			
			if(sp.equals(REPORTE_ESTADO)) {
				procedureQuery.registerStoredProcedureParameter(FacEnum.ESTADO.getValor(), int.class, ParameterMode.IN);
				procedureQuery.registerStoredProcedureParameter(FacEnum.COMPANIA.getValor(), String.class, ParameterMode.IN);
				procedureQuery.registerStoredProcedureParameter(FacEnum.SUCURSAL.getValor(), String.class, ParameterMode.IN);
				procedureQuery.setParameter(FacEnum.ESTADO.getValor(), estadoTipoDoc);
				procedureQuery.setParameter(FacEnum.COMPANIA.getValor(), parametros[4]);
				procedureQuery.setParameter(FacEnum.SUCURSAL.getValor(), parametros[5]);
			} else if(sp.equals(REPORTE_DETALLE)) {
				procedureQuery.registerStoredProcedureParameter(FacEnum.COMPANIA.getValor(), String.class, ParameterMode.IN);
				procedureQuery.registerStoredProcedureParameter(FacEnum.TIPODOC.getValor(), int.class, ParameterMode.IN);
				procedureQuery.setParameter(FacEnum.COMPANIA.getValor(), parametros[4]);
				procedureQuery.setParameter(FacEnum.TIPODOC.getValor(), estadoTipoDoc);
			} else if(sp.equals(REPORTE_TOTALES)) {
				procedureQuery.registerStoredProcedureParameter(FacEnum.TIPODOC.getValor(), int.class, ParameterMode.IN);
				procedureQuery.setParameter(FacEnum.TIPODOC.getValor(), estadoTipoDoc);
			}
			
			procedureQuery.registerStoredProcedureParameter(FacEnum.FECHAINI.getValor(), String.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.FECHAFIN.getValor(), String.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.NUMDOC.getValor(), String.class, ParameterMode.IN);
			procedureQuery.registerStoredProcedureParameter(FacEnum.NUMPOLIZA.getValor(), String.class, ParameterMode.IN);
			procedureQuery.setParameter(FacEnum.FECHAINI.getValor(), parametros[0]);
			procedureQuery.setParameter(FacEnum.FECHAFIN.getValor(), parametros[1]);
			procedureQuery.setParameter(FacEnum.NUMDOC.getValor(), parametros[2]);
			procedureQuery.setParameter(FacEnum.NUMPOLIZA.getValor(), parametros[3]);
			procedureQuery.execute();
		} catch (IllegalArgumentException | NoResultException | NullPointerException e) {
			LOG.warn("Ha ocurrido un error durante la consulta del procedimeinto: {} | error = {} ", sp, e.getMessage());
		}

		return procedureQuery;
	}
}
