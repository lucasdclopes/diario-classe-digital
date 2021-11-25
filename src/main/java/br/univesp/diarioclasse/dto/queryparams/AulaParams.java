package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record AulaParams(
		@DateTimeFormat(pattern = DateHelper.patternDataPtBr) LocalDate dtAula, 
		@DateTimeFormat(pattern = DateHelper.patternDataHoraPtBr) LocalDateTime hrAula, 
		String statusAula, 
		Integer idTurma, 
		Integer idMateria, 
		Integer idProfessor) {
}
