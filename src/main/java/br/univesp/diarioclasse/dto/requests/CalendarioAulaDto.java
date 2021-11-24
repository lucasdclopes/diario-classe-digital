package br.univesp.diarioclasse.dto.requests;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.helpers.DateHelper;

public record CalendarioAulaDto(	
		@NotNull DiaDaSemana diaSemana,
		@NotNull @JsonFormat(pattern=DateHelper.patternHoraPtBr) LocalTime hrInicio,
		@NotNull @JsonFormat(pattern=DateHelper.patternHoraPtBr) LocalTime hrFim,
		@NotNull Integer idMateria,
		@NotNull Integer idProfessor,
		@NotNull Integer idTurma
		) {}
