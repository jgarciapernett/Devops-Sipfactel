package co.com.periferia.alfa.core.dto;

import java.sql.Timestamp;
import java.util.Date;

import co.com.periferia.alfa.core.model.admin.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO implements IBaseDTO<UserDTO, User> {

	private Integer usuaUsua;
	private String usuaUsuario;	
	private String usuaContrasenia;
	private String usuaNombres;
	private String usuaApellidos;
	private String usuaCorreo;
	private String usuaEstado;
	private String usuaUcreacion;
	private Date usuaFcreacion;
	private String usuaUactualiza;
	private Date usuaFactualiza;
	private Integer usuaRol;
	private String nombreRol;

	@Override
	public String toString() {
		return "UserDTO [usuaUsua=" + usuaUsua + ", usuaUsuario=" + usuaUsuario + ", usuaContrasenia=" + usuaContrasenia
				+ ", usuaNombres=" + usuaNombres + ", usuaApellidos=" + usuaApellidos + ", usuaCorreo=" + usuaCorreo + ", usuaEstado=" + usuaEstado + ", usuaUcreacion=" + usuaUcreacion
				+ ", usuaFcreacion=" + usuaFcreacion + ", usuaUactualiza=" + usuaUactualiza + ", usuaFactualiza="
				+ usuaFactualiza + "]";
	}

	@Override
	public UserDTO modeloAdto(User modelo) {
		UserDTO dto = new UserDTO();
		if (modelo.getUsuaUsua() != null) {
			dto.setUsuaUsua(modelo.getUsuaUsua());
		}
		dto.setUsuaUsuario(modelo.getUsuaUsuario());		
		dto.setUsuaContrasenia(modelo.getUsuaContrasenia());
		dto.setUsuaNombres(modelo.getUsuaNombres());
		dto.setUsuaApellidos(modelo.getUsuaApellidos());
		dto.setUsuaCorreo(modelo.getUsuaCorreo());
		dto.setUsuaEstado(modelo.getUsuaEstado());		
		dto.setUsuaUcreacion(modelo.getUsuaUcreacion());
		dto.setUsuaFcreacion(modelo.getUsuaFcreacion());
		dto.setUsuaUactualiza(modelo.getUsuaUactualiza());
		dto.setUsuaFactualiza(modelo.getUsuaFactualiza());
		dto.setUsuaRol(modelo.getUsuaRol());
		return dto;
	}

	@Override
	public User dtoAModelo(UserDTO dto) {
		User modelo = new User();
		if (modelo.getUsuaUsua() != null) {
			modelo.setUsuaUsua(dto.getUsuaUsua());
		}
		modelo.setUsuaUsuario(dto.getUsuaUsuario());
		modelo.setUsuaContrasenia(dto.getUsuaContrasenia());
		modelo.setUsuaNombres(dto.getUsuaNombres());
		modelo.setUsuaApellidos(dto.getUsuaApellidos());
		modelo.setUsuaCorreo(dto.getUsuaCorreo());	
		modelo.setUsuaEstado(dto.getUsuaEstado());
		modelo.setUsuaRol(dto.getUsuaRol());
		
		if (dto.getUsuaFcreacion() != null) {
			modelo.setUsuaFcreacion(dto.getUsuaFcreacion());
		} else {
			modelo.setUsuaFcreacion(new Timestamp(new java.util.Date().getTime()));
		}
		if (dto.getUsuaFactualiza() != null) {
			modelo.setUsuaFactualiza(dto.getUsuaFactualiza());
		} else {
			modelo.setUsuaFactualiza(new Timestamp(new java.util.Date().getTime()));
		}		
		modelo.setUsuaUcreacion(dto.getUsuaUcreacion());
		modelo.setUsuaUactualiza(dto.getUsuaUactualiza());

		return modelo;
	}


}
