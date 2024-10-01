package co.com.periferia.alfa.core.services.impl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.com.periferia.alfa.core.exception.ExcepcionSegAlfa;
import co.com.periferia.alfa.core.exception.UtilExcecion;
import co.com.periferia.alfa.core.model.DatosCorreoModel;
import co.com.periferia.alfa.core.repository.DatosCorreoRepository;
import co.com.periferia.alfa.core.services.EnviarEmailService;

@Component
public class EnviarEmailServiceImpl implements EnviarEmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnviarEmailServiceImpl.class);

	@Autowired
	DatosCorreoRepository datosCorreoRepository;

	@Autowired
	UtilExcecion utilException;

	public String sendmail(String mensaje, String subject) throws ExcepcionSegAlfa {

		try {
			LOGGER.info("Clase EnvioEmail - Parametros |menasaje: {}  |subject: {}", mensaje, subject);
			DatosCorreoModel info = datosCorreoRepository.getOne(1);

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			/**
			 * props.put("mail.smtp.ssl.enable", "false");
			 */
			props.put("mail.smtp.host", info.getHost());
			props.put("mail.smtp.port", info.getPuerto());

			Session session = Session.getInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(info.getEmisor(), info.getPass());
				}
			});
			/**
			 * Session session = Session.getDefaultInstance(props);
			 */

			MimeMessage msg = new MimeMessage(session);

			msg.setFrom(info.getEmisor());
			msg.addRecipients(Message.RecipientType.TO, info.getReceptor());
			/**
			 * msg.addRecipients(Message.RecipientType.CC, info.getCc());
			 * msg.addRecipients(Message.RecipientType.BCC, info.getCco());
			 */
			msg.setSubject(subject);

			/**
			 * BodyPart adjunto = new MimeBodyPart();
			 * adjunto.setDataHandler(new DataHandler(new
			 * FileDataSource("LogoSegAlfa.png")));
			 * adjunto.setFileName("LogoSegAlfa.png");
			 */

			MimeMultipart multiParte = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setText(mensaje);
			/**
			 * multiParte.addBodyPart(adjunto);
			 */
			multiParte.addBodyPart(messageBodyPart);
			msg.setContent(multiParte);
			/**
			 * Transport.send(msg);
			 */
		} catch (MessagingException | PersistenceException | IllegalArgumentException | NullPointerException e) {
			LOGGER.error("Error al ejecutar Clase EnvioEmail");
			throw utilException.getById(17).crearExcepcion();
		}
		return "El correo fue enviado correctamente";
	}

}
