package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_datos_delcop")
public class DatosDelcopModel {

	@Id
	@Column(name = "dato_dato")
	private Integer id;

	@Column(name = "dato_nit")
	private Integer nit;

	@Column(name = "dato_usuario")
	private String usuario;

	@Column(name = "dato_password")
	private String password;

	@Column(name = "dato_campo")
	private String campo;

	@Column(name = "dato_comparacion")
	private String comparacion;

	@Column(name = "dato_tr_tipo_id")
	private Integer trTipoId;

	@Column(name = "dato_hora_envio")
	private String horaEnvio;

	@Column(name = "dato_url_signin")
	private String urlSignin;

	@Column(name = "dato_url_listar")
	private String urlListar;

	@Column(name = "dato_url_emitir")
	private String urlEmitir;

	@Column(name = "dato_config_cn")
	private String configCN;

	@Column(name = "dato_ip_ldap")
	private String ipLdap;

	@Column(name = "dato_pass_ldap")
	private String passLdap;

	@Column(name = "dato_config_ou")
	private String configOU;

	@Column(name = "dato_config_dc")
	private String configDC;

	@Column(name = "dato_tr_tipo_id_deb")
	private Integer trTipoIdDeb;

	@Column(name = "dato_tr_tipo_id_cre")
	private Integer trTipoIdCre;

	@Column(name = "dato_url_documentos")
	private String urlDocumentos;
	
	@Column(name = "dato_url_eliminar_transaccion")
	private String urlEliminarTransaccion;
	
	@Column(name = "dato_hora_reenvio")
	private String horaReenvio;

	@Column(name = "dato_limite_fac")
	private Integer datoLimiteFac;
	
	@Column(name = "dato_url_detalle")
	private String urlDetalle;
}
