package co.com.periferia.alfa.core.services;



import java.util.List;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

/**
 * @author jhonnatangil
 * Clase con los metodos basicos para la definicion de los servicios a implementar
 * @param <T> Clase DTO a implementar
 * @param <K> Llave primaria a usar del DTO
 */
public interface IServicio<T, K> {

    /***
     * Metodo para guardar la entidad clase
     * @param clase
     * @return
     */
    T guardar(T clase) throws ExcepcionSegAlfa;

    /***
     * Metodo para actualizar la entidad clase
     * @param clase
     * @param llave
     * @return
     */
    T actualizar(T clase, K llave) throws ExcepcionSegAlfa;

    /***
     * Metodo para eliminar la entidad clase
     * @param llave
     * @return
     */
    T eliminar(K llave) throws ExcepcionSegAlfa;

    /***
     * Metodo para listar todas las entidades de tipo clase
     * @return
     */
    List<T> buscarTodos() throws ExcepcionSegAlfa;

    /***
     * Metodo para consultar la entidad por su ID
     * @param llave
     * @return
     */
    T buscarPorID(K llave) throws ExcepcionSegAlfa;


}
