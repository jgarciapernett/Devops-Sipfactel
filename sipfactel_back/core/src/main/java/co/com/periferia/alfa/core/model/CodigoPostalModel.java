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
@Table(name = "tbl_codigo_postal")
public class CodigoPostalModel {
	
	@Id
	@Column(name = "post_post")
	private Integer id;
	
	@Column(name = "post_nombremunicipio", length = 100)
	private String nombre;
	
	@Column(name = "post_codpostal", length = 50)
	private String codpostal;

}
