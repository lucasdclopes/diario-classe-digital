package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.constantes.TipoEndereco;

public record NovoEnderecoDto ( @NotBlank String logradouro,
		@NotBlank String numeroEndereco,		 
		String complementoEndereco,		 
		@NotBlank String cep,		 
		@NotBlank String bairro,		 
		@NotBlank String cidade,	 
		@NotBlank String uf,
		@NotNull TipoEndereco tpEndereco) {
}
