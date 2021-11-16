package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.validadores.CpfNumerico;

public record NovoProfessorDto(
		@JsonFormat(pattern="dd/MM/yyyy")
		LocalDate dtAdmissao,
		@NotBlank
		String nome,
		@CpfNumerico
		String cpf,
		String rg,
		@JsonFormat(pattern="dd/MM/yyyy")
		LocalDate dtNascimento,
		String sexo,
		String nomeMae,
		String nomePai, 
		List<NovoEnderecoDto> enderecos,
		List<NovoTelefoneDto> telefones) {
}
