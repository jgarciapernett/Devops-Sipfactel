package co.com.periferia.alfa.core.dto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentosDto {

	@SerializedName("doc_id")
	private String docid;
	private String estado;
	private String error;
	
}
