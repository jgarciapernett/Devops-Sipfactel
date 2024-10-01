package co.com.periferia.alfa.core.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.stereotype.Repository;

import co.com.periferia.alfa.core.dto.ConsAdquirientesDTO;
import co.com.periferia.alfa.core.model.ConsAdquirientesModel;

@Repository
public class ConsAdquirientesRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	//SP ADQUIRIENTES
	@SuppressWarnings("unchecked")
    public List<ConsAdquirientesDTO> getAdquirientes(Integer tipoidentificacion, String numidentificacion, String razonsocial) {
    	List<ConsAdquirientesModel> result = new ArrayList<>();
    	List<ConsAdquirientesDTO> resultado = new ArrayList<>();
        try {
            StoredProcedureQuery procedureQuery = entityManager.createStoredProcedureQuery("SP_ADQUIRIENTES",ConsAdquirientesModel.class);
            procedureQuery.registerStoredProcedureParameter("c_Resp", void.class, ParameterMode.REF_CURSOR);
            procedureQuery.registerStoredProcedureParameter("cod_tipoidentificacion", int.class, ParameterMode.IN);
            procedureQuery.registerStoredProcedureParameter("num_identificacion", String.class, ParameterMode.IN);
            procedureQuery.registerStoredProcedureParameter("razon_social", String.class, ParameterMode.IN);
            procedureQuery.setParameter("cod_tipoidentificacion", tipoidentificacion);
            procedureQuery.setParameter("num_identificacion", numidentificacion);
            procedureQuery.setParameter("razon_social", razonsocial);
            procedureQuery.execute();
            result = procedureQuery.getResultList();
            resultado = result.stream().map(e -> new ConsAdquirientesDTO().modeloAdto(e)).collect(Collectors.toList());
        } catch (Exception e) {
            return new ArrayList<>();
        }
        return resultado;
    }

}
