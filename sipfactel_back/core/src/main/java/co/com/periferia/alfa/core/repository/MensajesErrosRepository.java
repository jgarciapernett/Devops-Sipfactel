package co.com.periferia.alfa.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.periferia.alfa.core.model.MensajesErrorModel;

public interface MensajesErrosRepository extends JpaRepository<MensajesErrorModel, Integer> {

}
