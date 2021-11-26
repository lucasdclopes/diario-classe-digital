package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record LoginDto(
		@NotBlank @Email String emailLogin, 
		@NotBlank String senha) {

}
