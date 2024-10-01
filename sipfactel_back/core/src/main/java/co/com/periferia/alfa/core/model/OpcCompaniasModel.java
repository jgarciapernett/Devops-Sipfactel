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
@Table(name = "tbl_companias")
public class OpcCompaniasModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_companias_seq")
	@SequenceGenerator(name = "tbl_companias_seq", sequenceName = "tbl_companias_seq", allocationSize = 1)	
	@Column(name = "comp_comp")
	private Integer id;

	@Column(name = "comp_nombre", length = 100)
	private String nombre;

}
