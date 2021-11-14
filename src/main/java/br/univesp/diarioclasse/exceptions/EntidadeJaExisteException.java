package br.univesp.diarioclasse.exceptions;

public class EntidadeJaExisteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String idRecursoExistente;

	public EntidadeJaExisteException(String message, String idRecursoExistente) {
		super(message);
		this.idRecursoExistente = idRecursoExistente;
	}

}
