package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

public record ListaAlunosDto(
		Integer idCadastro, String nroMatricula, LocalDate dtMatricula, String ra, String nome ) {

}
