package br.univesp.diarioclasse.exceptions;

public class AutorizacaoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AutorizacaoException() {
		super();
	}
	public AutorizacaoException(String message) {
		super(message);
	}
	
}
