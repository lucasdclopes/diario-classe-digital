package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.enums.StatusAula;
import br.univesp.diarioclasse.helpers.DateHelper;

public record ListaAulasDto(
		Integer idAula,
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtAula,
		@JsonFormat(pattern=DateHelper.patternDataHoraPtBr) LocalDateTime dtHrIniciada,
		@JsonFormat(pattern=DateHelper.patternDataHoraPtBr) LocalDateTime dtHrFinalizada,
		StatusAula statusAula,
		ListaMateriasDto materia,
		CadastroDadosBasicosDto professor,
		ListaTurmasDto turma
	) {}
