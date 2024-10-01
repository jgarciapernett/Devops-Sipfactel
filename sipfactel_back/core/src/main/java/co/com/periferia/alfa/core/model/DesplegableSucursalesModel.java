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
@Table(name = "tbl_sucursales")
public class DesplegableSucursalesModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tbl_sucursales_seq")
	@SequenceGenerator(name = "tbl_sucursales_seq", sequenceName = "tbl_sucursales_seq", allocationSize = 1)

	@Column(name = "suc_suc")
	private Integer sucu;
	
	@Column(name = "suc_nomsuc", length = 100)
	private String nomsuc;
	
}
