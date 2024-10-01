package co.com.periferia.alfa.core.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import co.com.periferia.alfa.core.model.admin.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select u from User u where UPPER(u.usuaUsuario) like '%'||UPPER(?1)||'%' ")
	public User findByName(String userName);

	@Query(value = "select u from User u where UPPER(u.usuaUsuario) LIKE UPPER (concat('%', ?1,'%'))")
	public List<User> findLikeName(String nombre);

	@Query(value = "select u from User u where u.usuaUsua = ?1 ")
	public User findByID(Integer userID);

	@Query(value = "select u from User u where UPPER(u.usuaNombres) LIKE %?1% or UPPER(u.usuaApellidos) LIKE %?1%")
	public List<User> findByNameSurname(String userName, Pageable pageable);

	@Query(value = "select u from User u")
	public List<List<User>> buscarTodos();

}
