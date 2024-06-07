package fsbr.comum.armazenamento;

@SuppressWarnings("serial")
public class ArmazenamentoException extends RuntimeException	 {

	public ArmazenamentoException(String message) {
		super(message);
	}

	public ArmazenamentoException(String message, Throwable cause) {
		super(message, cause);
	}
}
