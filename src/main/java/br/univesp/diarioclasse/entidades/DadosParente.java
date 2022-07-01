package br.univesp.diarioclasse.entidades;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.validadores.CpfNumerico;

@Embeddable
public class DadosParente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotBlank @Length(max = 200)
	private String nome;
	@NotBlank @Length(max = 11) @CpfNumerico
	private String CPF;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public DadosParente() {}
	
	public DadosParente(String nome,String CPF, Telefone telContato) {
		this.nome = nome;
		this.CPF = CPF;
		this.telContato = telContato;
	}

	@Valid
	@Embedded 
	private Telefone telContato;

	public String getNome() {
		return nome;
	}

	public String getCPF() {
		return CPF;
	}

	public Telefone getTelContato() {
		return telContato;
	}
	
	

}
