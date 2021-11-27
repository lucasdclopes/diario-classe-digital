package br.univesp.diarioclasse.dto.responses;

import br.univesp.diarioclasse.enums.TipoCadastro;

public record LoginOkDto(
		String tokenAcesso,
		Integer idCadastro,
		String nome,
		TipoCadastro tipoCadastro ) {}
