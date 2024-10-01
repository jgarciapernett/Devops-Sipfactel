package co.com.periferia.alfa.core.exception;

import co.com.periferiait.utilitarios.exepcion.ExcepcionPeriferia;

public interface ITipoError<K, T extends ITipoError<K, T, S>, S extends ExcepcionPeriferia> {

    public K getCodigo();
    
    public String getMessage();
    
    public Long getId();

    public S crearExcepcion();

}