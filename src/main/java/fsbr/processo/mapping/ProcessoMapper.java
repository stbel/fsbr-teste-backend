package fsbr.processo.mapping;

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import fsbr.processo.dto.ProcessoChangeDTO;
import fsbr.processo.dto.ProcessoCreateDTO;
import fsbr.processo.dto.ProcessoResponseDTO;
import fsbr.processo.model.ProcessoEntity;

@Mapper(componentModel = "spring")
public interface ProcessoMapper { 

	ProcessoResponseDTO toResponseDTO(ProcessoEntity entity);

	List<ProcessoResponseDTO> toResponseDTO(List<ProcessoEntity> entities);
	
	ProcessoEntity toEntity(ProcessoCreateDTO dto);
	
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void fillEntity(ProcessoChangeDTO dto, @MappingTarget ProcessoEntity entity);
}
