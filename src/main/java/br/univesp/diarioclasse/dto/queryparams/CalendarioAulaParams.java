package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record CalendarioAulaParams(
		String diaSemana,
		Integer idTurma,
		Integer idMateria,
		Integer idProfessor,
		@DateTimeFormat(pattern = DateHelper.patternHoraPtBr)LocalTime hrAula) {

}
