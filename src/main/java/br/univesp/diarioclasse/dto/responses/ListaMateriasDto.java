package br.univesp.diarioclasse.dto.responses;

import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record ListaMateriasDto(
		Integer idMateria,
		String descMateria,
		TipoNivelEnsino tpNivelEnsino ) {}
