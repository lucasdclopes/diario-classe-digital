package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record AulaDto(
		@JsonFormat(pattern=DateHelper.patternDataPtBr) @NotNull LocalDate dtAula, 
		@NotNull Integer idCalendarioAula) {}
