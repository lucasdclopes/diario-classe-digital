package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.constantes.TipoTelefone;

public record NovoTelefoneDto ( @NotNull TipoTelefone tpTelefone,
		@NotBlank String ddd,
		@NotBlank String numeroTelefone) {

}
