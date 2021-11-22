package br.univesp.diarioclasse.dto.responses;

import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record ListaTurmasDto(
		Integer idTurma,
		String descTurma,
		PeriodoEstudo tpPeriodo,
		TipoNivelEnsino tpNivelEnsino ) {}
