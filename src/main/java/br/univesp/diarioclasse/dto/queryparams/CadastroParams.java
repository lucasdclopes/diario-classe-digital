package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record CadastroParams(String nome,
		String cpf,
		String rg,
		@DateTimeFormat(pattern = DateHelper.patternDataPtBr) LocalDate dtNascimento,
		String sexo,
		String nomeMae,
		String nomePai) {}
