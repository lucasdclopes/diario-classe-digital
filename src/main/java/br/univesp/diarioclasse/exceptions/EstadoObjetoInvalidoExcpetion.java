package br.univesp.diarioclasse.exceptions;

/**
 * Indica que o objeto está em um estado inválido para a operação
 */
public class EstadoObjetoInvalidoExcpetion extends Exception {

	private static final long serialVersionUID = 1L;

	private EstadoObjetoInvalidoExcpetion(String message) {
		super(message);
	}

}
