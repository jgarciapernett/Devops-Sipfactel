package co.com.periferia.alfa.core.model.admin;

import java.sql.Timestamp;

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
@Table(name = "tbl_sesion")
public class Sesion {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_sesion_sesi_sesi_seq")
    @SequenceGenerator(name="tbl_sesion_sesi_sesi_seq", sequenceName="tbl_sesion_sesi_sesi_seq", allocationSize=1)
	@Column(name = "sesi_sesi")
	private Integer sesiSesi;
	
	@NotNull
	@Column(name = "sesi_ip", length = 45)
	private String sesiIp;
	
	@Column(name = "sesi_token", length = 300)
	private String sesiToken;
	
	@NotNull
	@Column(name = "sesi_usuario")
	private Integer sesiUsuario;
	
	@NotNull
	@Column(name = "sesi_fecha")
	private Timestamp sesiFecha;
	
	@NotNull
	@Column(name = "sesi_refreshtoken")
	private String sesiRefreshToken;
	
	@Override
	public String toString() {
		return "Sesion [sesiSesi=" + sesiSesi + ", sesiIp=" + sesiIp + ", sesiToken=" + sesiToken + ", sesiUsuario="
				+ sesiUsuario + ", sesiFecha=" + sesiFecha + ", sesiRefreshToken=" + sesiRefreshToken + "]";
	}



	
}
