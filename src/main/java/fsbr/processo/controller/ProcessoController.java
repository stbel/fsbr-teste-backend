package fsbr.processo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fsbr.comum.armazenamento.ArmazenamentoArquivosService;
import fsbr.processo.dto.ProcessoChangeDTO;
import fsbr.processo.dto.ProcessoCreateDTO;
import fsbr.processo.dto.ProcessoResponseDTO;
import fsbr.processo.model.ProcessoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("processos")
public class ProcessoController {

	@Autowired
	private ProcessoService service;
	
	@Autowired
	private ArmazenamentoArquivosService arquivosService;
	
	@Operation(summary = "Listar processos - recupera listagem dos processos")
	@GetMapping
	public ResponseEntity<Page<ProcessoResponseDTO>> search(Pageable pageable) {

		var responseDtos = service.list(pageable);

		return ResponseEntity.ok(responseDtos);
	}

	@Operation(summary = "Buscar processo - retorna processo específico pelo id")
	@GetMapping("{id}")
	public ResponseEntity<ProcessoResponseDTO> find(@PathVariable("id") Long id) {

		var responseDto = service.find(id);

		return ResponseEntity.ok(responseDto);
	}
	
	@Operation(summary = "Criar processo - cadastra novo processo")
	@PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ProcessoResponseDTO> create(@ModelAttribute  @Valid ProcessoCreateDTO createDTO) {

		var responseDto = service.create(createDTO);

		return ResponseEntity.ok(responseDto);
	}
		
	@Operation(summary = "Alterar processo - altera processo existente")
	@PutMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<ProcessoResponseDTO> change(@ModelAttribute @Valid ProcessoChangeDTO changeDTO) {

		var responseDto = service.change(changeDTO);

		return ResponseEntity.ok(responseDto);
	}
	
	@Operation(summary = "Buscar processo - retorna processo específico pelo id")
	@GetMapping("arquivo/{npu}")
	public ResponseEntity<?>  downloadFile(@PathVariable("npu") String npu) throws IOException {

		var responseResource = arquivosService.buscarComoResource(String.format("%s.pdf", npu));

		 return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM)
				 .header(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=%s", responseResource.getFilename()))
				 .body(responseResource);
	}
}
