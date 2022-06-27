package br.univesp.diarioclasse.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Embeddable
public class Endereco implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank @Length(max = 300)
	private String endLogradouro;
	@Length(max = 20)
	private String endNumero;
	@Length(max = 50)
	private String endComplemento;
	@NotBlank @Length(max = 8)
	private String endCEP;
	@Length(max = 50)
	private String endBairro;
	@NotBlank @Length(max = 50)
	private String endCidade;
	@NotBlank @Length(max = 2)
	private String endUF;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Endereco() {}
	
	public Endereco(String endLogradouro, String endNumero, String endComplemento,
			String endCEP, String endBairro, String endCidade,
			String endUF) {
		this.endLogradouro = endLogradouro;
		this.endNumero = endNumero;
		this.endComplemento = endComplemento;
		this.endCEP = endCEP;
		this.endBairro = endBairro;
		this.endCidade = endCidade;
		this.endUF = endUF;
	}
	public String getEndLogradouro() {
		return endLogradouro;
	}
	public String getEndNumero() {
		return endNumero;
	}
	public String getEndComplemento() {
		return endComplemento;
	}
	public String getEndCEP() {
		return endCEP;
	}
	public String getEndBairro() {
		return endBairro;
	}
	public String getEndCidade() {
		return endCidade;
	}
	public String getEndUF() {
		return endUF;
	}
	
}
