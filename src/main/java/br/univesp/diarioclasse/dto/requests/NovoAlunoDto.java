package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.validadores.CpfNumerico;

public record NovoAlunoDto (
		@NotBlank String nroMatricula,
		@JsonFormat(pattern="dd/MM/yyyy") LocalDate dtMatricula,
		@NotBlank String ra,
		@NotBlank String nome,
		@CpfNumerico String cpf,
		String rg, 
		@JsonFormat(pattern="dd/MM/yyyy") LocalDate dtNascimento,
		String sexo,
		@NotBlank String nomeMae,
		String nomePai, 
		Optional<List<NovoEnderecoDto>> enderecos,
		Optional<List<NovoTelefoneDto>> telefones,
		Optional<Turma> turma) {
}
