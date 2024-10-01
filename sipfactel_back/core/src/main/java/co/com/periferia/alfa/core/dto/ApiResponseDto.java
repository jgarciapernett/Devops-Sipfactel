package co.com.periferia.alfa.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto {

	private Object data;
	private Long totalElements;
	
}
