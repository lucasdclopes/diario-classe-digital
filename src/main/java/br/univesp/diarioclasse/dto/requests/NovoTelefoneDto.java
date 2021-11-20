package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.constantes.TipoTelefone;
import br.univesp.diarioclasse.validadores.SomenteDigitos;

public record NovoTelefoneDto (@NotNull TipoTelefone tpTelefone,
		@NotBlank @Length(max = 2) @SomenteDigitos String ddd,
		@NotBlank @Length(max = 9) @SomenteDigitos String numeroTelefone) {

}
