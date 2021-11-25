package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotNull;

public record PresencaAlunoDto(
		@NotNull Integer idAluno,
		@NotNull Boolean isPresente) {

}
