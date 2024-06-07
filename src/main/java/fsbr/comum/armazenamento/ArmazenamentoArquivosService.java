package fsbr.comum.armazenamento;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArmazenamentoArquivosService {

	private final Path caminhoDocumentos = Paths.get("documentos-processos");

	public void inicializar() {
		try {
			Files.createDirectories(caminhoDocumentos);
		} catch (IOException e) {
			throw new ArmazenamentoException("Falha ao inicializar diretório em disco", e);
		}
	}

	public void gravarPdf(MultipartFile file, String nomeArquivoProcesso) {

		if (!caminhoDocumentos.toFile().exists()) {
			this.inicializar();
		}

		if (!file.getContentType().equals("application/pdf")) {
			throw new ArmazenamentoException("Somente é permitido armazenamento pdf para esse registro.");
		}

		try {
			if (file.isEmpty()) {
				throw new ArmazenamentoException("Não é permitido armazenar aquivos vazios");
			}

			Path destinationFile = this.caminhoDocumentos.resolve(Paths.get(nomeArquivoProcesso)).normalize().toAbsolutePath();

			if (!destinationFile.getParent().equals(this.caminhoDocumentos.toAbsolutePath())) {
				throw new ArmazenamentoException("Não é permitido armazenar arquivos fora do diretório padrão.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (IOException e) {
			throw new ArmazenamentoException("Erro ao gravar o arquivo.", e);
		}
	}

	public Path buscar(String filename) {
		return caminhoDocumentos.resolve(filename);
	}

	public Resource buscarComoResource(String filename) {
		try {
			Path file = buscar(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new ArmazenamentoArquivoNaoEncontradoException("Não foi possível ler o arquivo: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new ArmazenamentoArquivoNaoEncontradoException("Arquivo com caminho incorreto: " + filename, e);
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(caminhoDocumentos.toFile());
	}
}
