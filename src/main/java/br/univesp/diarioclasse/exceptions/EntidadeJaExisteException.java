package br.univesp.diarioclasse.exceptions;

public class EntidadeJaExisteException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String campoRepeticao;
	
	/**
	 * Indica que a entidade já existe
	 * @param message mensagem de erro
	 * @param campoRepeticao descrição do(s) campo(s) que disparou o erro
	 */
	public EntidadeJaExisteException(String message, String campoRepeticao) {
		super(message);
		this.campoRepeticao = campoRepeticao;
	}

	
	public String getCampoRepeticao() {
		return campoRepeticao;
	}
}
