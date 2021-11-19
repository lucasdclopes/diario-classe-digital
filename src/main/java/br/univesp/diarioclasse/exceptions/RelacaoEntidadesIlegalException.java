package br.univesp.diarioclasse.exceptions;

/**
 * Quando uma relação é recebida para ser adicionada, mas ela possuí uma referencia incorreta de relacionamento
 */
public class RelacaoEntidadesIlegalException extends IllegalArgumentException {

	private static final long serialVersionUID = 1L;

	public RelacaoEntidadesIlegalException(String s) {
		super(s);
	}
}
