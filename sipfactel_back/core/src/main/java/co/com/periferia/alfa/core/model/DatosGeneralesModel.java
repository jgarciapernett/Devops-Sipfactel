package co.com.periferia.alfa.core.model;

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
@Table(name = "tbl_datos_generales")
public class DatosGeneralesModel {
	
	@Id
	@ GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_datos_generales_seq")
	@ SequenceGenerator(name = "tbl_datos_generales_seq", sequenceName = "tbl_datos_generales_seq", allocationSize = 1)
	@NotNull
	@Column(name = "dato_dato")
	private Integer dato;
	
	@NotNull
	@Column(name = "dato_cod_pais")
	private Integer codPais;
	
	@NotNull
	@Column(name = "dato_nompais", length = 100)
	private String nomPais;
	
	@NotNull
	@Column(name = "dato_cod_moneda")
	private Integer moneda;

	@NotNull
	@Column(name = "dato_cod_ambiente")
	private Integer ambiente;
	
	@NotNull
	@Column(name = "dato_estado", length = 100)
	private String estado;
	
	@NotNull
	@Column(name = "dato_fcracion")
	private Date fcreacion;
	
	@NotNull
	@Column(name = "dato_ucreacion", length = 100)
	private String ucreacion;

}
