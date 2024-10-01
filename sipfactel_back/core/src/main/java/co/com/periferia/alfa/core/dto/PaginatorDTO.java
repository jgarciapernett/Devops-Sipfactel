package co.com.periferia.alfa.core.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginatorDTO {
	
	private Integer from;
	private Integer to;
	private Integer total;
	private Integer currentPage;
	private Integer lastPage;
	
	
	public PaginatorDTO() {
		super();
	}
	
	public PaginatorDTO(Integer from, Integer to, Integer total, Integer currentPage, Integer lastPage) {
		super();
		this.from = from;
		this.to = to;
		this.total = total;
		this.currentPage = currentPage;
		this.lastPage = lastPage;
	}
	
}
