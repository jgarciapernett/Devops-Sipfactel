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
@Table(name = "tbl_obl_fiscales_asp")
public class OblFiscalesModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="tbl_obl_fiscales_asp_seq")
    @SequenceGenerator(name="tbl_obl_fiscales_asp_seq", sequenceName="tbl_obl_fiscales_asp_seq", allocationSize=1)
	@Column(name = "asp_asp")
	private Integer id;	
	
	@Column(name = "asp_comp_comp")
	private Integer idComp;	
	
	@Column(name = "asp_cod_fiscales")
	private Integer codFiscales;

}
