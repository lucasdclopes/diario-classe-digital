package br.univesp.diarioclassedigital.dto.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

public record NovoProfessorDto(
		@JsonFormat(pattern="dd/MM/yyyy")
		LocalDate dtAdmissao,
		@NotBlank
		String nome,
		String cpf,
		String rg,
		@JsonFormat(pattern="dd/MM/yyyy")
		LocalDate dtNascimento,
		String sexo,
		String nomeMae,
		String nomePai) {
}
