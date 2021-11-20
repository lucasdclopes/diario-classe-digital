package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ListaAlunosDto(String nroMatricula, LocalDate dtMatricula, String ra, 
		@JsonProperty("nome") String CadastroNome ) {

}
