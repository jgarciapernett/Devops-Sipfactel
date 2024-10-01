package co.com.periferia.alfa.core.model.admin;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_usuario_usua_usua_seq")
    @SequenceGenerator(name="tbl_usuario_usua_usua_seq", sequenceName="tbl_usuario_usua_usua_seq", allocationSize=1)
    @Column(name = "usua_usua")
	private Integer usuaUsua;
    
    @Column(name="usua_apellidos", length = 100)
	private String usuaApellidos;
    
    @Column(name="usua_nombres", length = 100)
	private String usuaNombres;
	
    @Column(name="usua_contrasenia", length = 100)
	private String usuaContrasenia;
    
    @Column(name="usua_correo", length = 100)
	private String usuaCorreo;
    
    @Column(name="usua_usuario", length = 45)
	private String usuaUsuario;
    
    @Column(name="usua_estado", length = 3)
	private String usuaEstado;
    
    @Column(name="usua_factualliza")
	private Date usuaFactualiza;
    
    @Column(name="usua_uactualiza", length = 45)
	private String usuaUactualiza;
    
    @Column(name="usua_fcreacion")
	private Date usuaFcreacion;    
	
	@Column(name="usua_ucreacion", length = 45)
	private String usuaUcreacion;
	
	@NotNull
	@Column(name="usua_rol", length = 45)
	private Integer usuaRol;

	@Override
	public String toString() {
		return "User [usuaUsua=" + usuaUsua + ", usuaApellidos=" + usuaApellidos + ", usuaNombres=" + usuaNombres
				+ ", usuaContrasenia=" + usuaContrasenia + ", usuaCorreo=" + usuaCorreo
				+ ", usuaUsuario=" + usuaUsuario + ", usuaEstado=" + usuaEstado + ", usuaFactualiza=" + usuaFactualiza
				+ ", usuaUactualiza=" + usuaUactualiza + ", usuaFcreacion=" + usuaFcreacion + ", usuaUcreacion="
				+ usuaUcreacion + ", usuaRol=" + usuaRol + "]";
	}	
	
}
