package co.com.periferia.alfa.core.jwt;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.com.periferia.alfa.core.model.admin.User;
import co.com.periferia.alfa.core.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserPrincipal userPIRN;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail)  {
		User user = userRepository.findByName(usernameOrEmail);
		return userPIRN.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Integer id) {
		User user = userRepository.findByID(id);

		return userPIRN.create(user);
	}
}
