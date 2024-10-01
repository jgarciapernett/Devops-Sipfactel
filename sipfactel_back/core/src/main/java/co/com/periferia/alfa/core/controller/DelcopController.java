package co.com.periferia.alfa.core.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.TransactionException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanInstantiationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import co.com.periferia.alfa.core.dto.ConsultaEstadoConNumdocDto;
import co.com.periferia.alfa.core.dto.RespuestaDTO;
import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.jwt.JwtTokenData;
import co.com.periferia.alfa.core.model.ConsEstadosTransitoriosModel;
import co.com.periferia.alfa.core.repository.DatosDelcopRepository;
import co.com.periferia.alfa.core.repository.ErrorInternoRepository;
import co.com.periferia.alfa.core.repository.FacturaDelcopRepository;
import co.com.periferia.alfa.core.repository.IConsultaEstadosRepository;
import co.com.periferia.alfa.core.repository.NotasCreDebRepository;
import co.com.periferia.alfa.core.repository.ReenvioFacturasNotasRepository;
import co.com.periferia.alfa.core.services.CreacionJsonNotasService;
import co.com.periferia.alfa.core.services.CreacionJsonService;
import co.com.periferia.alfa.core.services.EnviarEmailService;
import co.com.periferia.alfa.core.services.IConsultaEstadosRestClientService;
import co.com.periferia.alfa.core.services.IReenviarFacturaNotaService;
import co.com.periferia.alfa.core.services.IRestClient;
import co.com.periferia.alfa.core.utilitarios.EstadoRespuesta;
import co.com.periferia.alfa.core.utilitarios.FacEnum;
import co.com.periferia.alfa.core.utilitarios.NombreEstado;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@Controller
@RequestMapping(value = "/delcop")
public class DelcopController {

	private static final Logger LOGGER = LoggerFactory.getLogger(DelcopController.class);

	@Autowired
	FacturaDelcopRepository factura;

	@Autowired
	CreacionJsonService creacionJsonService;

	@Autowired
	CreacionJsonNotasService creacionJsonNotasService;

	@Autowired
	IRestClient restCliente;

	@Autowired
	public JwtTokenData jtd;

	@Autowired
	FacturaDelcopRepository facturaDelcopRepository;

	@Autowired
	private DatosDelcopRepository datosDelcopRepository;

	@Autowired
	ErrorInternoRepository errorInternoRepository;

	@Autowired
	EnviarEmailService enviarEmailService;

	@Autowired
	NotasCreDebRepository notasCreDebRepository;

	@Autowired
	ReenvioFacturasNotasRepository reenvioRepository;

	@Autowired
	IReenviarFacturaNotaService reenvioService;

	@Autowired
	IConsultaEstadosRestClientService consultaEstadosService;
	
	@Autowired
	IConsultaEstadosRepository ConsultaEstadosTrans;

	@Bean
	String getCronValue() {
		String horaEnvio = FacEnum.HORAENVIO_DEFECTO.getValor();
		try {
			horaEnvio = datosDelcopRepository.getOne(Integer.parseInt(FacEnum.ID_DATOS_DELCOP.getValor()))
					.getHoraEnvio();
			LOGGER.info("Sprint 5 - Hora envio del metodo sp: {} ", horaEnvio);
		} catch (BeanInstantiationException e) {
			LOGGER.info("Ha ocurrido un error con la instaciacin del bean en metodo getCronValue de delcopController");
		}
		return horaEnvio;
	}

	@Bean
	String getCronValueReenvio() {
		String horaEnvio = FacEnum.HORAENVIO_DEFECTO.getValor();
		try {
			horaEnvio = datosDelcopRepository.getOne(Integer.parseInt(FacEnum.ID_DATOS_DELCOP.getValor()))
					.getHoraReenvio();
			LOGGER.info("Hora envio del metodo reenvioFacturasNotas: {} ", horaEnvio);
		} catch (BeanInstantiationException e) {
			LOGGER.info(
					"Ha ocurrido un error con la instaciacion del bean en metodo getCronValueReenvio de delcopController");
		}
		return horaEnvio;
	}
	/**
	 * Metodo que genera la numeracion de facturas y mnostas debito y credito
	 */
	@GetMapping("/generarNumeracion")
	@ResponseBody
	@ApiOperation(value = "asignar numeracion", authorizations = @Authorization(value = "Bearer"))
	public void generaraNumeracion() throws ExcepcionSegAlfa {
		String mensaje = null;
		LOGGER.info("Inicio generacion de numeracion general");
		mensaje = facturaDelcopRepository.numeracionGeneral();
		LOGGER.info("Se genero numeracion general, mensaje = {} ", mensaje);
		enviarEmail(mensaje);
	}
	
	private void enviarEmail (String mensaje) throws ExcepcionSegAlfa {
		if (mensaje != null && !mensaje.equals("Asignaci�n de n�mero de Factura Exitosa")
				&& (!mensaje.equals("Excedi� n�mero de facturas") || !mensaje
						.equals("La Fecha no se encuentra en el rango para asignaci�n de Factura de la Sucursal"))) {
			String mensajeEmail = NombreEstado.MENSAJE_INICIO + "\r\n" + mensaje + NombreEstado.MENSAJE;
			String subject = "Asignacion Facturas";
			enviarEmailService.sendmail(mensajeEmail, subject);
		}
	}

    // Método para emitir facturas
	@Scheduled(cron = "#{getCronValue}")
	    public void  emitirFacturas() throws ExcepcionSegAlfa {
			LOGGER.info(" envio del metodo emitir: {} ");
	    	int id = 1255; //
	    	errorInternoRepository.getReAsignar();
	        creacionJsonService.emitirFacturas(id, "", FacEnum.APLICACION.getValor());
	   
	    }

	 // Método para emitir notas de débito
	@Scheduled(cron = "#{getCronValue}")
	    public void  emitirNotasDebito() throws ExcepcionSegAlfa {
			
			LOGGER.info("envio del metodo DEBITO: {} ");
	       boolean flag = true;//
	       creacionJsonNotasService.emitirNotasDeb(flag, "", FacEnum.APLICACION.getValor());
	       
	    }
	    // Método para emitir notas de crédito
	@Scheduled(cron = "#{getCronValue}")
	   public void emitirNotasCredito( ) throws ExcepcionSegAlfa {
	       
			LOGGER.info("envio del metodo CREDITO: {} ");
	    	boolean flag = true;//
	        creacionJsonNotasService.emitirNotasCred(flag, "", FacEnum.APLICACION.getValor());
	      
	    }
	  // Método para consulta num. Docs de emisión
	 @Scheduled(cron = "#{getCronValue}")
	    public void mapCodigosDocEmision()  {
	        
	    	LOGGER.info(" Ingreso metodo mapear num. Docs de emisión ");
	    	try {
				List<ConsEstadosTransitoriosModel> ListaDocEstadosTrasn = ConsultaEstadosTrans.estadosTransitoriosAConsultar();
				List<ConsultaEstadoConNumdocDto> numDocEmision = new ArrayList<>();

				ListaDocEstadosTrasn.forEach(codigo -> {
					if (codigo != null){
						ConsultaEstadoConNumdocDto dto = new ConsultaEstadoConNumdocDto();
						String numdoc = codigo.getNumdoc();
						dto.setNumdoc(numdoc);
						dto.setTipo(codigo.getTipoDoc());
						numDocEmision.add(dto);
					}
				});

				if (!numDocEmision.isEmpty()){
					consultaEstadosService.consultarEstadoDocFacturas(numDocEmision, null, true);
				}
			}  catch (ExcepcionSegAlfa | JSONException | IOException | JpaSystemException | TransactionException ex) {
				LOGGER.error("Error al consumir el servicio, error: {} | {}", ex.getMessage(), ex.getStackTrace());
	    	}
	    }

	@GetMapping("/reenvioFacturas")
	@ResponseBody
	@Scheduled(cron = "#{getCronValueReenvio}")
	public void reenvioFacturasNotas() {
		try {
			LOGGER.info("Inicio servicio de reenvio de facturas/notas rechazadas");

			reenvioService.reenviarFacturaNotaGeneral();
			
			List<Object[]> codigosTrid = reenvioRepository.codigosTrid();
			
		
			
			if (codigosTrid != null && !codigosTrid.isEmpty()) {
				reenvioService.reenviarFacturaNotaCodigosTrid(codigosTrid);
				String mensaje = facturaDelcopRepository.numeracionGeneral();
				LOGGER.info("Se asigna numeracion genral despues de reenvio, mensaje = {} ", mensaje);
			}

			
			LOGGER.info("Finalizo reenvio de facturas/notas rechazadas");
		} catch (ExcepcionSegAlfa | JSONException | IOException | JpaSystemException | TransactionException ex) {
			LOGGER.error("Error al consumir el servicio de reenvio, error: {} | {}", ex.getMessage(),
					ex.getStackTrace());
		}
	}

	@GetMapping("/envioMail")
    @ResponseBody
	@ApiOperation(value = "envio mail", authorizations = @Authorization(value = "Bearer"))
	public ResponseEntity<RespuestaDTO<String>> envio() {
		LOGGER.info("DelcopController: Ingreso al envio de email");
		RespuestaDTO<String> respuesta = null;
		try {
			respuesta = new RespuestaDTO<>(EstadoRespuesta.OK,
					enviarEmailService.sendmail("Esto es una prueba", "Prueba"));
		} catch (ExcepcionSegAlfa ex) {
			LOGGER.error("Error al consumir el servicio, error: {} | {} ", ex.getMessage(), ex.getStackTrace());
		}
		LOGGER.info("DelcopController: Termino el servicio de envio email");
		return new ResponseEntity<>(respuesta, HttpStatus.OK);
	}

}
