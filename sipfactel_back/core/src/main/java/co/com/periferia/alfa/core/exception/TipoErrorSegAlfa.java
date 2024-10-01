package co.com.periferia.alfa.core.exception;

import java.io.Serializable;


public class TipoErrorSegAlfa implements ITipoError<Integer, TipoErrorSegAlfa, ExcepcionSegAlfa>, Serializable{
	
//	ERROR_SEG_ALFA_001    (1001, "%s, no fue guardado correctamente %s"),
//	ERROR_SEG_ALFA_002    (1002, "%s, no fue actualizado correctamente"),
//    ERROR_SEG_ALFA_003    (1003, "%s, no fue eliminado correctamente"),
//    ERROR_SEG_ALFA_004    (1004, "%s, no existe %s"),
//    ERROR_SEG_ALFA_005    (1005, "La lista esta vacia"),
//    ERROR_SEG_ALFA_006    (1006, "Exepcion de autenticacion"),
//    ERROR_SEG_ALFA_007    (1007, "La contrase\u00f1a es incorrecta"),
//    ERROR_SEG_ALFA_008    (1008, "El usuario no existe"),
//    ERROR_SEG_ALFA_009 	  (1009, "El registro no existe"),
//    ERROR_SEG_ALFA_010    (1010, "%s, ya existe %s"),
//    ERROR_SEG_ALFA_011    (1011, "No existen registros para la b\u00fasqueda"),
//    ERROR_SEG_ALFA_012    (1012, "Error al crear el archivo"),
//    ERROR_SEG_ALFA_013    (1013, "Error al eliminar el registro"),
//    ERROR_SEG_ALFA_014    (1014, "Se ha presentado un problema, por favor intentar nuevamente"),
//    ERROR_SEG_ALFA_015    (1015, "El usuario ya existe")
//	

	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private String mensaje;
	private Long id;


    public TipoErrorSegAlfa(int codigo, String mensaje, Long id){
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.id = id;
    }

	@Override
	public Integer getCodigo() {
		return codigo;
	}

	@Override
	public String getMessage() {
		return mensaje;
	}

	@Override
	public Long getId() {
		return this.id;
	}


	@Override
	public ExcepcionSegAlfa crearExcepcion() {
		return new ExcepcionSegAlfa(this);
	}
	
	public ExcepcionSegAlfa crearExcepcion(Throwable causa) {
		return new ExcepcionSegAlfa(causa,this);
	}

   

}
