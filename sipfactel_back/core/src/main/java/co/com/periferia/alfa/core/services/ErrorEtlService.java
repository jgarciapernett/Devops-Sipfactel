package co.com.periferia.alfa.core.services;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;

public interface ErrorEtlService {

	String error(Integer iderror, Integer idtabla, String tabla, Integer errorid, String usuario)
			throws ExcepcionSegAlfa;

	boolean personas(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa;

	boolean detalleFactura(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa;

	boolean factura(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa;

	boolean notas(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa;

	boolean detalleNotas(Integer idtabla, Integer errorid) throws ExcepcionSegAlfa;

	boolean poliza(Integer errorid, Integer iderror, Integer idtabla) throws ExcepcionSegAlfa;

	void detalleError(Integer iderror, String usuario) throws ExcepcionSegAlfa;

	String categoria(Integer iderror, String categoria, String observacion, String usuario) throws ExcepcionSegAlfa;

}
