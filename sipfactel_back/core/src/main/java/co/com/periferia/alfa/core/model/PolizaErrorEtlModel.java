package co.com.periferia.alfa.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PolizaErrorEtlModel {

	@Id
	@Column(name = "id_error")
	private Integer iderror;

	@Column(name = "id_tabla")
	private Integer idtabla;

	@Column(name = "tabla")
	private String tabla;

	@Column(name = "env_numpoliza")
	private String numpoliza;

	@Column(name = "env_numdocumento")
	private String numdocumento;

	@Column(name = "TipoMovimiento")
	private String tipomovimiento;

	@Column(name = "fechaEmision")
	private String fechaemision;

	@Column(name = "ErrorId")
	private Integer errorid;

	@Column(name = "fechaInsercion")
	private String fechainsercion;

	@Column(name = "mensaje_con_valor")
	private String mensaje;

	@Column(name = "categoria")
	private Integer categoria;

	@Column(name = "observacion")
	private String observacion;

	@Column(name = "num_persona")
	private String numpersona;

}
