package br.univesp.diarioclasse.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.validadores.SomenteDigitos;

@Embeddable
public class Telefone implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Length(max = 3) @SomenteDigitos 
	private String telDDD;
	@Length(max = 10) @SomenteDigitos 
	private String telNro;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Telefone() {}
	
	public Telefone(String telDDD, String telNro) {
		this.telDDD = telDDD;
		this.telNro = telNro;
	}
	
	public String getTelDDD() {
		return telDDD;
	}
	public String getTelNro() {
		return telNro;
	}

	
}
