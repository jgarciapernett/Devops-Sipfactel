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

@Entity
@Getter
@Setter
@Table(name = "TBL_NUMERACION_NO_USADA")
public class NumeracionNoUsadaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_NUMERACION_NO_USADA_SEQ")
	@SequenceGenerator(name = "TBL_NUMERACION_NO_USADA_SEQ", sequenceName = "TBL_NUMERACION_NO_USADA_SEQ", allocationSize = 1)
	@Column(name = "dni")
	private Integer dni;

	@Column(name = "codigos_sin_vigencia")
	private String codigos;

	@Column(name = "fecha_hora")
	private String fechaHora;
	
}
