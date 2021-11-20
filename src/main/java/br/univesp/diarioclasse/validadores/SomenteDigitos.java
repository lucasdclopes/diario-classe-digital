package br.univesp.diarioclasse.validadores;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ValidadorSomenteDigitos.class)
@Documented
public @interface SomenteDigitos {

	  String message() default "{somenteDigitos.invalido}";

	  Class<?>[] groups() default { };

	  Class<? extends Payload>[] payload() default { };
}
