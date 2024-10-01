package co.com.periferia.alfa.core.model;

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
@Table(name = "tbl_categoria")
public class DesplegableCategoriaModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_categoria_seq")
	@SequenceGenerator(name = "tbl_categoria_seq", sequenceName = "tbl_categoria_seq", allocationSize = 1)

	@Column(name = "cat_cat")
	private Integer cat;
	
	@Column(name = "cat_nombre", length = 500)
	private String nombre;
	
	@Column(name = "cat_descripcion", length = 500)
	private String descripcion;

}
