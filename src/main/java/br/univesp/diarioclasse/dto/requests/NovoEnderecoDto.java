package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.constantes.TipoEndereco;
import br.univesp.diarioclasse.validadores.SomenteDigitos;

public record NovoEnderecoDto ( @NotBlank @Length(max = 300) String logradouro,
		@NotBlank  @Length(max = 20) String numeroEndereco,		 
		@Length(max = 20) String complementoEndereco,		 
		@Length(max = 8) @NotBlank @SomenteDigitos String cep,		 
		@Length(max = 50) @NotBlank String bairro,		 
		@Length(max = 50) @NotBlank String cidade,	 
		@Length(max = 2) @NotBlank String uf,
		@NotNull TipoEndereco tpEndereco) {
}
