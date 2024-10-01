package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.OpcHomologacionModel;

public interface OpcHomologacionRepository extends JpaRepository<OpcHomologacionModel, Integer> {

	@Query(value = "select conf_conf,conf_sistema from tbl_homologacion h inner join tbl_configuracion c on c.conf_conf = h.homo_conf_conf where c.conf_padre is null group by conf_sistema,conf_conf", nativeQuery = true)
	public List<OpcHomologacionModel> findByTipo();

}