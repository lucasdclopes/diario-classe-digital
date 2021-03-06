package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record TurmaDto(	
		@NotBlank @Length(max = 15) String descTurma,
		@NotNull PeriodoEstudo tpPeriodo,
		@NotNull TipoNivelEnsino tpNivelEnsino) {}
