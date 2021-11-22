package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record ListaProfessorDto(
		Integer idCadastro, LocalDate dtAdmissao, String nome, String nomeMateria, TipoNivelEnsino nivelEnsino )
{}