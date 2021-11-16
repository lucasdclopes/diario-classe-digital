package br.univesp.diarioclasse.exceptions;

public class DadosInvalidosException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private String campoInvalido;
	
	/**
	 * Indica que houve um problema com os dados enviados
	 * @param message mensagem de erro
	 * @param campoInvalido descrição do(s) campo(s) que disparou o erro
	 */
	public DadosInvalidosException(String message, String campoInvalido) {
		super(message);
		this.campoInvalido = campoInvalido;
	}

	public String getCampoInvalido() {
		return campoInvalido;
	}
	
	
}
