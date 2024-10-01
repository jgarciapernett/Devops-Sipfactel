package co.com.periferia.alfa.core.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.Sesion;


public interface SesionRepository extends JpaRepository <Sesion, Integer> {
	
	@Query(value ="select s from Sesion s where s.sesiIp = ?1")
	public Sesion findSesionByIp  (String ip); 
	
	@Modifying
	@Transactional
	@Query(value ="delete from Sesion s where s.sesiIp = ?1")
	public void logOut  (String ip); 
	
	@Query(value ="select s from Sesion s where s.sesiRefreshToken = ?1")
	public Sesion findSesionByUUID (String refreshTokenRequest); 

}
