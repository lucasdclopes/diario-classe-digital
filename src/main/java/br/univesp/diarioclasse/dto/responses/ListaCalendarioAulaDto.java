package br.univesp.diarioclasse.dto.responses;

import java.time.LocalTime;

import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record ListaCalendarioAulaDto(
		Integer idCalendarioAula,
		DiaDaSemana diaSemana,
		LocalTime hrInicio,
		LocalTime hrFim,
		String descMateria,
		TipoNivelEnsino tpNivelEnsino,
		String nomeProfessor,
		String descTurma
		) {}

