package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.validadores.CpfNumerico;

public record NovoProfessorDto(
		@JsonFormat(pattern="dd/MM/yyyy") LocalDate dtAdmissao,
		@NotBlank String nome,
		@NotBlank @CpfNumerico String cpf, 
		String rg,
		@JsonFormat(pattern="dd/MM/yyyy") LocalDate dtNascimento,
		Sexo sexo,
		String nomeMae,
		String nomePai, 
		Optional<List<NovoEnderecoDto>> enderecos,
		Optional<List<NovoTelefoneDto>> telefones,
		Optional<Materia> materia) {
}
