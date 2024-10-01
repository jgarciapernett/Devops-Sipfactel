package co.com.periferia.alfa.core.model;

import java.time.LocalDateTime;

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
@Table(name = "tbl_horario")
@Getter
@Setter
public class HorarioModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TBL_EJECUCION_DIARIO_ETL_SEQ")
	@SequenceGenerator(name = "TBL_EJECUCION_DIARIO_ETL_SEQ", sequenceName = "TBL_EJECUCION_DIARIO_ETL_SEQ", allocationSize = 1)
	private Long id;
	private String negocio;
	private String ejecucion;
	@Column(name = "hora_inicio")
	private LocalDateTime horaInicio;
	@Column(name = "hora_fin")
	private LocalDateTime horaFin;
	private Integer orden;
	
}
