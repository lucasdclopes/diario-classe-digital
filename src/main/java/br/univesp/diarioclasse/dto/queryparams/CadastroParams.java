package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;

public record CadastroParams(String nome,
		String cpf,
		String rg,
		LocalDate dtNascimento,
		String sexo,
		String nomeMae,
		String nomePai) {}
