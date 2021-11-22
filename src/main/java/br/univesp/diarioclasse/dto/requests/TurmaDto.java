package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.enums.PeridoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record TurmaDto(	
		@NotNull String descTurma,
		@NotNull PeridoEstudo tpPeriodo,
		@NotNull TipoNivelEnsino tpNivelEnsino) {}
