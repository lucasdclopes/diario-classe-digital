
package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;

import br.univesp.diarioclasse.validadores.CpfNumerico;

public class CadastroAdmDto {

	@NotBlank
	private String nome;
	@NotBlank @CpfNumerico
	private String cpf;
	
	public CadastroAdmDto() {}

	public String getNome() {
		return nome;
	}

	public String getCpf() {
		return cpf;
	}
	
}
