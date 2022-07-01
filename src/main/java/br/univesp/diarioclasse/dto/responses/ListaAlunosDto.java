package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record ListaAlunosDto(
		Integer idAluno, String nroMatricula, @JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtMatricula, String NIS, String nome, String nomeMae ) {

}
