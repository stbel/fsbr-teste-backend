package fsbr.fronteira.ibge;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="ibgeLocalidadesFronteiraClient", url = "https://servicodados.ibge.gov.br/api/v1/localidades/municipios")
public interface IbgeLocalidadesFronteira {
	
	@GetMapping("/{id}?view=nivelado")
	Map<String, String> getMunicipio(@PathVariable("id") Long id);
}