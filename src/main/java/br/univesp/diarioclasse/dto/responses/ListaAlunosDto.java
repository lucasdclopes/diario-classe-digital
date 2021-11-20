package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

public record ListaAlunosDto(String nroMatricula, LocalDate dtMatricula, String ra, String nome ) {

}
