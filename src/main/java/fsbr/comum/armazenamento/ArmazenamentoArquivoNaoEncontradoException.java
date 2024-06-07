package fsbr.comum.armazenamento;

@SuppressWarnings("serial")
public class ArmazenamentoArquivoNaoEncontradoException extends RuntimeException	 {

	public ArmazenamentoArquivoNaoEncontradoException(String message) {
		super(message);
	}

	public ArmazenamentoArquivoNaoEncontradoException(String message, Throwable cause) {
		super(message, cause);
	}
}
