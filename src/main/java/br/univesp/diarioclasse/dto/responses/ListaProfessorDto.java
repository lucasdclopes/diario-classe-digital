package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.helpers.DateHelper;

public record ListaProfessorDto(
		Integer idCadastro, @JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtAdmissao, String nome, String nomeMateria, TipoNivelEnsino nivelEnsino )
{}
