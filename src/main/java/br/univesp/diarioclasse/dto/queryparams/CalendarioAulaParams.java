package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalTime;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record CalendarioAulaParams(
		String diaSemana,
		Integer idTurma,
		Integer idMateria,
		Integer idProfessor,
		@DateTimeFormat(pattern = DateHelper.patternHoraPtBr)LocalTime hrAula) {

}
