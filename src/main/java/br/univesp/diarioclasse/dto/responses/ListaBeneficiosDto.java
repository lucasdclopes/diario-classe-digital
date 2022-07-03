package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record ListaBeneficiosDto(
		Integer idBeneficio, 
		Integer idAluno,
		String nomeAluno,
		String nisAluno,
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtRecebimento, 
		String responsavelRecebimento
		) {

}
