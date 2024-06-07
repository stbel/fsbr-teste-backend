package fsbr.processo.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessoResponseDTO {

	private Long id;

	private String npu;

	private Long ibgeMunicipioId;
		
	private String ibgeMunicipioNome;
	
	private Long ibgeUfId;
	
	private String ibgeUfNome;
	
	private String ibgeUfSigla;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataCadastro;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime dataVisualizacao;
}
