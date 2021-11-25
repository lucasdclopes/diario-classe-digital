package br.univesp.diarioclasse.dto.responses;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.helpers.DateHelper;

public record DetalhesCalendarioAulaDto(
		Integer idCalendarioAula,
		DiaDaSemana diaSemana,
		@JsonFormat(pattern=DateHelper.patternHoraPtBr) LocalTime hrInicio,
		@JsonFormat(pattern=DateHelper.patternHoraPtBr) LocalTime hrFim,
		ListaMateriasDto materia,
		CadastroDadosBasicosDto professor,
		ListaTurmasDto turma
		) {}

