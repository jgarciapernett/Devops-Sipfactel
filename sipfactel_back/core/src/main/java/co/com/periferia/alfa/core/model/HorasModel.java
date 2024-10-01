package co.com.periferia.alfa.core.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tbl_hora")
@Getter
@Setter
public class HorasModel {

	@Id
	private Integer dni;
	private String hora;
	private String estado;
	
}
