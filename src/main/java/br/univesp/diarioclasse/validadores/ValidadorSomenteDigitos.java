package br.univesp.diarioclasse.validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
/**
 * Campos que não desejamos tratar como INT ou LONG, mas queremos que não tenha caracteres (exemplo, alguns documentos sem máscara)
 * @author lucas.trabalho
 *
 */
public class ValidadorSomenteDigitos implements ConstraintValidator<SomenteDigitos, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if (value == null)
			return true;
		return value.matches("[0-9]+");
	}

}
