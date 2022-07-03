package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record BeneficioParams(
		Integer idAluno,
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtRecebimentoInicio, 
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtRecebimentoFim, 
		String responsavelRecebimento
		) {}
