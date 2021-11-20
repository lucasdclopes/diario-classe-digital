package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import br.univesp.diarioclasse.constantes.Sexo;

public record CadastroParamFiltro(String nome,
		String cpf,
		String rg,
		LocalDate dtNascimento,
		Sexo sexo,
		String nomeMae,
		String nomePai) {}
