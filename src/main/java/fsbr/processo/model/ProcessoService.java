package fsbr.processo.model;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import fsbr.comum.armazenamento.ArmazenamentoArquivosService;
import fsbr.fronteira.ibge.IbgeLocalidadesFronteira;
import fsbr.processo.dto.ProcessoChangeDTO;
import fsbr.processo.dto.ProcessoCreateDTO;
import fsbr.processo.dto.ProcessoResponseDTO;
import fsbr.processo.mapping.ProcessoMapper;
import jakarta.transaction.Transactional;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository repository;

	@Autowired
	private ArmazenamentoArquivosService arquivosService;

	@Autowired
	private IbgeLocalidadesFronteira localidadesFronteira;

	@Autowired
	private ProcessoMapper mapper;

	public Page<ProcessoResponseDTO> list(Pageable pageable) {

		return repository.findAll(pageable).map(mapper::toResponseDTO);
	}

	@Transactional
	public ProcessoResponseDTO find(Long id) {
		
		var persisted  = repository.findById(id).orElseThrow(() -> new RuntimeException(String.format("Some with id %d not found.", id)));
		persisted.setDataVisualizacao(LocalDateTime.now());
		
		return mapper.toResponseDTO(persisted);
	}

	@Transactional
	public ProcessoResponseDTO create(ProcessoCreateDTO createDTO) {

		var entity = mapper.toEntity(createDTO);

		preencherDadosLocalidade(createDTO.getIbgeUfId(), createDTO.getIbgeMunicipioId(), entity);

		entity.setDataCadastro(LocalDateTime.now());
		entity.setDataVisualizacao(LocalDateTime.now());

		arquivosService.gravarPdf(createDTO.getDocPdf(), createDTO.getNpu() + ".pdf");

		return mapper.toResponseDTO(repository.save(entity));
	}

	@Transactional
	public ProcessoResponseDTO change(ProcessoChangeDTO changeDTO) {

		var persisted = repository.findById(changeDTO.getId())
				.orElseThrow(() -> new RuntimeException(String.format("Processo com id %d não encontrado.", changeDTO.getId())));

		mapper.fillEntity(changeDTO, persisted);
		
		preencherDadosLocalidade(changeDTO.getIbgeUfId(), changeDTO.getIbgeMunicipioId(), persisted);

		return mapper.toResponseDTO(repository.save(persisted));
	}
	
	private void preencherDadosLocalidade(Long ibgeUfId, Long ibgeMunicipioId, ProcessoEntity entity) {
		var municipioData = localidadesFronteira.getMunicipio(ibgeMunicipioId);

		if (!ibgeUfId.equals(Long.valueOf(municipioData.get("UF-id")))) {
			throw new IllegalArgumentException("Município não pentence ao Estado informado.");
		}

		entity.setIbgeMunicipioNome(municipioData.get("municipio-nome"));
		entity.setIbgeUfNome(municipioData.get("UF-nome"));
		entity.setIbgeUfSigla(municipioData.get("UF-sigla"));
	}
}
