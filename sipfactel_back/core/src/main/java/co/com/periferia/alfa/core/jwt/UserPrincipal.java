package co.com.periferia.alfa.core.jwt;

import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import co.com.periferia.alfa.core.model.admin.User;

@Component
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 5502125617054272076L;

	private Integer id;

	private String name;

	private String username;

	@JsonIgnore
	private String email;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal create(User user) {

		return new UserPrincipal().setId(user.getUsuaUsua())
				.setName(user.getUsuaNombres() + " " + user.getUsuaApellidos()).setUsername(user.getUsuaUsuario())
				.setEmail(user.getUsuaCorreo());
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}

	public UserPrincipal setId(Integer id) {
		this.id = id;
		return this;
	}

	public UserPrincipal setName(String name) {
		this.name = name;
		return this;
	}

	public UserPrincipal setUsername(String username) {
		this.username = username;
		return this;
	}

	public UserPrincipal setEmail(String email) {
		this.email = email;
		return this;
	}

	public UserPrincipal setPassword(String password) {
		this.password = password;
		return this;
	}

	public UserPrincipal setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
		return this;
	}

}
