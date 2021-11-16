package br.univesp.diarioclasse.exceptions;

public class EntidadeNaoEncontradaException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Indica que a entidade não pode ser encontrada para os dados informados
	 * @param message mensagem descrevendo o problema. Pode-se usar o construtor padrão para uma mensagem padrão
	 */
	public EntidadeNaoEncontradaException(String message) {
		super(message);
	}
	/**
	 * Indica que a entidade não pode ser encontrada para os dados informados
	 */
	public EntidadeNaoEncontradaException() {
		super();
	}	
	
	

}
