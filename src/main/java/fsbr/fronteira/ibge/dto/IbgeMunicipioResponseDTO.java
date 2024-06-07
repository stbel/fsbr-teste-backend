package fsbr.fronteira.ibge.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IbgeMunicipioResponseDTO {
	
	private Long municipioId;
	
	private 	String municipioNome;
	
	private Long UFId;
	
	private String ufSigla;
	
	private String ufNome;
}
