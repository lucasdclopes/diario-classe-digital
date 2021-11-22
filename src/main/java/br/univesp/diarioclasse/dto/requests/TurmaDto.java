package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record TurmaDto(	
		@NotNull String descTurma,
		@NotNull PeriodoEstudo tpPeriodo,
		@NotNull TipoNivelEnsino tpNivelEnsino) {}
