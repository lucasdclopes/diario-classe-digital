package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;

import br.univesp.diarioclasse.enums.Sexo;

public record CadastroParams(String nome,
		String cpf,
		String rg,
		LocalDate dtNascimento,
		Sexo sexo,
		String nomeMae,
		String nomePai) {}
