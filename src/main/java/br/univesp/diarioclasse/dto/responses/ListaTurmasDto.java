package br.univesp.diarioclasse.dto.responses;

import br.univesp.diarioclasse.enums.PeridoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record ListaTurmasDto(
		Integer idTurma,
		String descTurma,
		PeridoEstudo tpPeriodo,
		TipoNivelEnsino tpNivelEnsino ) {}
