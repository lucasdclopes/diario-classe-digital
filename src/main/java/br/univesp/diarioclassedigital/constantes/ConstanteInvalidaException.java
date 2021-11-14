package br.univesp.diarioclassedigital.constantes;

public class ConstanteInvalidaException extends Exception {

	private static final long serialVersionUID = 1L;
	private String valorEnviado;

	public ConstanteInvalidaException(String message,String valorEnviado) {
		super(message);
		this.valorEnviado = valorEnviado;
	}

	public String getValorEnviado() {
		return valorEnviado;
	} 
}
