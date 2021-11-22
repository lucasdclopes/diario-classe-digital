package br.univesp.diarioclasse.dto.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record MateriaDto(	
		@NotBlank @Length(max = 20) String descMateria,
		@NotNull TipoNivelEnsino tpNivelEnsino) {}
