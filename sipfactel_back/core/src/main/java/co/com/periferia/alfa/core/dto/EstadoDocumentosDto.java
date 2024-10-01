package co.com.periferia.alfa.core.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.RespDelcopModel;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase dto que recopila la data extaridad de esatdoDocumentos de titanio
 * 
 * @author Duvan Rodriguez
 */
@Getter
@Setter
public class EstadoDocumentosDto {

	private static final Logger LOG = LoggerFactory.getLogger(EstadoDocumentosDto.class);

	@SerializedName("error_id")
	private Integer errorId;
	@SerializedName("error_msg")
	private String errorMsg;
	private List<DocumentosDto> documentos;

	/**
	 * Metood que convierte el dto de los documentos consultados al modelo delcop
	 * para ser guardados en bd
	 * 
	 * @param dto - EstadoDocumentosDto, dto con los documentos consultados de
	 *            titanio
	 * @return List<RespDelcopModel> - lista con el modelo de los datos a guardar en
	 *         BD
	 */

	public List<RespDelcopModel> dtoAModelo(EstadoDocumentosDto dto, List<ConsultaEstadoConNumdocDto> numdocs) {
		LOG.info("Incio conversion de dto a modelo delcop");
		List<RespDelcopModel> listModel = new ArrayList<>();
		if (dto.getDocumentos() != null) {
			Integer cantidadDocumentos = dto.getDocumentos().size();
			LOG.info("Cantidad documentos = {} ", cantidadDocumentos);
			for (int i = 0; i < cantidadDocumentos; i++) {
				
				RespDelcopModel modelo = new RespDelcopModel();
				if (dto.getDocumentos().get(i).getDocid() != null) {

					modelo.setErrorId(dto.getErrorId());
					modelo.setErrorMsg(dto.getErrorMsg());
					modelo.setEstados(dto.getDocumentos().get(i).getEstado());
					modelo.setDoc(dto.getDocumentos().get(i).getDocid());
					List<ConsultaEstadoConNumdocDto> encontrado = numdocs.stream()
							.filter(d -> d.getNumdoc().equals(modelo.getDoc())).collect(Collectors.toList());
					if (!encontrado.isEmpty()) {
						modelo.setDoc(encontrado.get(0).getNumdoc());
						listModel.add(modelo);
					} else {
						modelo.setDoc("null");
					}

				}
			}
		}
		return listModel;
	}

}
