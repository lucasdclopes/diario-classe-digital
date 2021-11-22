package br.univesp.diarioclasse.dto.queryparams;

import br.univesp.diarioclasse.enums.PeridoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record TurmaParams(
		String descTurma,
		PeridoEstudo tpPeriodo,
		TipoNivelEnsino tpNivelEnsino) {}
