package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import co.com.periferia.alfa.core.model.ReceptoresModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReceptoresDTO implements IBaseDTO<ReceptoresDTO, ReceptoresModel> {
	
	@SerializedName("Nombre")
	private String nombre;
	@SerializedName("Email")
	private String email;
	@SerializedName("Enviar_Email")
	private boolean enviarEmail;
	@SerializedName("Incluir_Anexos")
	private boolean incluirAnexos;
	@SerializedName("Incluir_PDF")
	private boolean incluirPDF;
	@SerializedName("Incluir_XML")
	private boolean incluirXML;
	
	public ReceptoresDTO() {
		super();
	}
	
	public ReceptoresDTO(String nombre, String email, boolean enviarEmail, boolean incluirAnexos, boolean incluirPDF,
			boolean incluirXML) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.enviarEmail = enviarEmail;
		this.incluirAnexos = incluirAnexos;
		this.incluirPDF = incluirPDF;
		this.incluirXML = incluirXML;
	}

	@Override
	public ReceptoresDTO modeloAdto(ReceptoresModel modelo) {
		ReceptoresDTO dto = new ReceptoresDTO();
		dto.setEmail(nulo(modelo.getEmail()));
		dto.setNombre(nulo(modelo.getNombre()));
		dto.setEnviarEmail(validaCero(modelo.getEnviarEmail()));
		dto.setIncluirAnexos(validaCero(modelo.getIncluirAnexos()));
		dto.setIncluirPDF(validaCero(modelo.getIncluirPDF()));
		dto.setIncluirXML(validaCero(modelo.getIncluirXML()));
		return dto;
	}
	
	@Override
	public ReceptoresModel dtoAModelo(ReceptoresDTO dto) {
		return null;
	}
	
	public String nulo(String dato) {

		if (dato == null) {
			dato = "";
		}
		return dato;
	}
	
	private boolean validaCero(String valor) {
		boolean valida = true;
		if(valor.equals("0")) valida = false;
		return valida;
	}
}
