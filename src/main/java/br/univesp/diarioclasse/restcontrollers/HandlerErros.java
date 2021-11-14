package br.univesp.diarioclasse.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.univesp.diarioclasse.dto.responses.ErroDto;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@RestControllerAdvice
public class HandlerErros {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDto> handle(MethodArgumentNotValidException exception) {
		List<ErroDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDto erro = new ErroDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(EntidadeJaExisteException.class)
	public ErroDto handle(EntidadeJaExisteException exception) {
		return new ErroDto(exception.getCampoRepeticao(), exception.getMessage());
	}
}
