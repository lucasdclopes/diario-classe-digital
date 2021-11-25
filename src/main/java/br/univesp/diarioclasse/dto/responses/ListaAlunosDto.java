package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record ListaAlunosDto(
		Integer idCadastro, String nroMatricula, @JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtMatricula, String ra, String nome ) {

}
