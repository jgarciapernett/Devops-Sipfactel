package co.com.periferia.alfa.core.utilitarios;

public final class NombreEstado {

	private NombreEstado() {
		throw new IllegalStateException("Utility class");
	}

	public static final String ESTADO_ACTIVO = "ACT";
	public static final String ESTADO_ELIMINADO = "ELI";
	public static final String ESTADO_SUSPENDIDO = "SUS";
	public static final String TIPO_APP = "APP";
	public static final String ACCESO_CORRECTO = "Acceso Correcto";
	public static final String ACCESO_INCORRECTO = "Acceso Inorrecto";
	public static final String FORMATO_FECHA = "dd/MM/yy";
	public static final String HEADERS_1 = "Content-Disposition";
	public static final String HEADERS_2 = "attachment; filename=Auditoria.xlsx";
	public static final String HEADERS_3 = "attachment; filename=Usuarios.xlsx";
	public static final String HEADERS_4 = "attachment; filename=DetalleFacturas.xlsx";
	public static final String HEADERS_5 = "attachment; filename=DetalleEstado.xlsx";
	public static final String HEADERS_REPORTE_ADQUIRIENTES = "attachment; filename=ReporteAdquirientes.xlsx";
	public static final String AUDITORIA = "Auditoria";
	public static final String USUARIOS = "Usuarios";
	public static final String DETALLE = "DetalleFactura";
	public static final String ESTADO = "DetalleEstado";
	public static final String MEDIATYPE = "application/vnd.ms-excel";
	public static final String FECHA_JSON = "yyyy-MM-dd";
	public static final String MENSAJE_INICIO = "Buen Día \r\n";
	public static final String MENSAJE = ", por favor actualizar los datos de la sucursal.\r\n" + "\r\n" + "Gracias.";
	public static final String MENSAJE_NOTAS = ".Por favor actualizar los datos de la sucursal.\r\n" + "\r\n" + "Gracias.";
}
