package br.univesp.diarioclasse.restcontrollers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.univesp.diarioclasse.dto.responses.ErroCampoDto;
import br.univesp.diarioclasse.dto.responses.ErroSimplesDto;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;

@RestControllerAdvice
public class HandlerErros {

	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroCampoDto> handle(MethodArgumentNotValidException exception) {
		List<ErroCampoDto> dto = new ArrayList<>();
		
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroCampoDto erro = new ErroCampoDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(DadosInvalidosException.class)
	public ErroCampoDto handle(DadosInvalidosException exception) {
		return new ErroCampoDto(exception.getCampoInvalido(), exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(EstadoObjetoInvalidoExcpetion.class)
	public ErroSimplesDto handle(EstadoObjetoInvalidoExcpetion exception) {
		return new ErroSimplesDto(exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(EntidadeJaExisteException.class)
	public ErroCampoDto handle(EntidadeJaExisteException exception) {
		return new ErroCampoDto(exception.getCampoRepeticao(), exception.getMessage());
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ErroSimplesDto handle(EntidadeNaoEncontradaException exception) {
		return new ErroSimplesDto("Não foram encontrados dados para os valores especificados");
	}
	
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErroSimplesDto handle(HttpMessageNotReadableException exception) {
		if (exception.getCause()!=null) {
			if (exception.getCause() instanceof JsonParseException)
				return new ErroSimplesDto("A requisição tem formato inválido. Possívelmente o json está incorreto");
			if (exception.getCause() instanceof InvalidFormatException e)
				return new ErroSimplesDto("A requisição possui uma constante inválida. " + e.getOriginalMessage());
		}
		throw exception;
	}
}
