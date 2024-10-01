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
@Table(name = "tbl_obl_fiscales_vd")
public class OblFiscalesValDefModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_obl_fiscales_vd_seq")
    @SequenceGenerator(name="tbl_obl_fiscales_vd_seq", sequenceName="tbl_obl_fiscales_vd_seq", allocationSize=1)
	
	@Column(name = "obl_obl")
	private Integer id;	

	@Column(name = "obl_vd_vd")
	private Integer idValorPordefecto;	
	
	@Column(name = "obl_cod_fiscales")
	private Integer codFiscales;

}
