package co.com.periferia.alfa.core.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_datos_proveedor")
public class DatosProveedorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_datos_proveedor_seq")
	@SequenceGenerator(name = "tbl_datos_proveedor_seq", sequenceName = "tbl_datos_proveedor_seq", allocationSize = 1)
	@Column(name = "prove_prove")
	private Integer proveedor;

	@Column(name = "prove_cod_pais")
	private Integer codPais;

	@Column(name = "prove_nitprovtecno", length = 100)
	private String nitprovtecno;

	@Column(name = "prove_dvprovtecno", length = 100)
	private String dvprovtecno;

	@Column(name = "prove_softwareid", length = 100)
	private String softwareid;

	@Column(name = "prove_softseccode", length = 100)
	private String softseccode;

	@Column(name = "prove_nitdian", length = 100)
	private String nitdian;

	@Column(name = "prove_digveridian", length = 100)
	private String digveridian;

	@Column(name = "prove_qrcode", length = 100)
	private String qrcode;

	@Column(name = "prove_versbaseubl", length = 100)
	private String versbaseubl;

	@Column(name = "prove_versformato", length = 100)
	private String versformato;

	@Column(name = "prove_cufe", length = 100)
	private String cufe;

	@Column(name = "prove_cantlineas", length = 100)
	private String cantlineas;

	@Column(name = "prove_estado", length = 100)
	private String estado;

	@Column(name = "prove_fcreacion")
	private Date fcreacion;

	@Column(name = "prove_ucreacion", length = 100)
	private String ucreacion;

}
