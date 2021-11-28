package br.univesp.diarioclasse.dto.responses;

import java.util.List;

import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record DetalhesTurmaDto(
		Integer idTurma,
		String descTurma,
		PeriodoEstudo tpPeriodo,
		TipoNivelEnsino tpNivelEnsino,
		List<AlunoDadosBasicosDto> alunos,
		Long totalFaltas) {}
