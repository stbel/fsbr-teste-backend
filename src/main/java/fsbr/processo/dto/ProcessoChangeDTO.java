package fsbr.processo.dto;

import org.springframework.web.multipart.MultipartFile;

import fsbr.comum.Constantes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessoChangeDTO {

	@NotNull
	private Long id;

	/** String no formato 1111111-11.1111.1.11.1111 que só aceite números */
	@Pattern(regexp = Constantes.PROCESSO_FORMATO_REGEX)
	private String npu;

	private Long ibgeMunicipioId;

	private Long ibgeUfId;

	private MultipartFile docPdf;
}
