package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.helpers.DateHelper;
import br.univesp.diarioclasse.validadores.CpfNumerico;
import br.univesp.diarioclasse.validadores.SomenteDigitos;

public record NovoProfessorDto(
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtAdmissao,
		@NotBlank @Length(max = 200) String nome,
		@NotBlank @CpfNumerico String cpf, 
		@Length(max = 10) @SomenteDigitos String rg,
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtNascimento,
		Sexo sexo,
		@Length(max = 200) String nomeMae,
		@Length(max = 200) String nomePai, 
		@Valid Optional<List<NovoEnderecoDto>> enderecos,
		@Valid Optional<List<NovoTelefoneDto>> telefones,
		Optional<Materia> materia) {
}
