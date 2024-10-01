package co.com.periferia.alfa.core.dto;

import java.util.List;

import co.com.periferia.alfa.core.model.admin.Roles;
import co.com.periferia.alfa.core.model.admin.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthenticationResponseDTO {
	private String accessToken;
	private String refreshToken;
	private String tokenType = "Bearer";
	private User user;
	private List<MenuDTO> listMenu;
	private List<Roles> listRole;
	private List<AlertaDTO> listAlerta;

	public JwtAuthenticationResponseDTO(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public JwtAuthenticationResponseDTO(String accessToken, String refreshToken, User user, List<MenuDTO> listMenu,
			List<Roles> listRole, List<AlertaDTO> listAlerta) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.user = user;
		this.listMenu = listMenu;
		this.listRole = listRole;
		this.listAlerta = listAlerta;
	}
}
