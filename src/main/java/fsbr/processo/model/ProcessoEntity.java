package fsbr.processo.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "processo")
public class ProcessoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true)
	private String npu;

	@Column(nullable = false)
	private Long ibgeMunicipioId;

	@Column(nullable = false)
	private String ibgeMunicipioNome;

	@Column(nullable = false)
	private Long ibgeUfId;

	@Column(nullable = false)
	private String ibgeUfNome;

	@Column(nullable = false)
	private String ibgeUfSigla;

	@Column(nullable = false)
	private LocalDateTime dataCadastro;

	@Column(nullable = false)
	private LocalDateTime dataVisualizacao;

}
