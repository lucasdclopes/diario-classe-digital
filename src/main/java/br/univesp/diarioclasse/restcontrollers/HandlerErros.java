package br.univesp.diarioclasse.restcontrollers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.univesp.diarioclasse.dto.responses.ErroCampoDto;
import br.univesp.diarioclasse.dto.responses.ErroSimplesDto;
import br.univesp.diarioclasse.exceptions.AutenticacaoException;
import br.univesp.diarioclasse.exceptions.ConstanteInvalidaException;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;

@RestControllerAdvice
public class HandlerErros {
	
	Logger logger = LoggerFactory.getLogger(HandlerErros.class);

	@Autowired
	private MessageSource messageSource;
	
	
	//Erros de login
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(AutenticacaoException.class)
	public ErroSimplesDto handle(AutenticacaoException exception) {
		return new ErroSimplesDto(
				exception.getMessage()!=null?exception.getMessage():"Usuário ou senha inválidos"
					);
	}
	
	
	//Sempre volta uma lista quando é 422, mesmo que só tenha um erro.
	//Fica mais fácil pro client caso ele já saiba que o 422 é semper uma lista
	
	//Erros de validação das anotações
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
	
	//Erro de valores inválidos de regras do domínio
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(DadosInvalidosException.class)
	public List<ErroCampoDto> handle(DadosInvalidosException exception) {
		return Arrays.asList(new ErroCampoDto(exception.getCampoInvalido(), exception.getMessage()));
	}
	
	//Erro de operação inválida para a situação daquele objeto
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(EstadoObjetoInvalidoExcpetion.class)
	public List<ErroSimplesDto> handle(EstadoObjetoInvalidoExcpetion exception) {
		return Arrays.asList(new ErroSimplesDto(exception.getMessage()));
	}

	//valores de constantes inválidos
	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
	@ExceptionHandler(ConstanteInvalidaException.class)
	public List<ErroSimplesDto> handle(ConstanteInvalidaException exception) {
		return Arrays.asList(new ErroSimplesDto(exception.getMessage()));
	}
	
	//erro de duplicidade. Já existe o que está tentando gravar
	@ResponseStatus(code = HttpStatus.CONFLICT)
	@ExceptionHandler(EntidadeJaExisteException.class)
	public ErroCampoDto handle(EntidadeJaExisteException exception) {
		return new ErroCampoDto(exception.getCampoRepeticao(), exception.getMessage());
	}
	
	//não foi encontrado
	@ResponseStatus(code = HttpStatus.NOT_FOUND)
	@ExceptionHandler(EntidadeNaoEncontradaException.class)
	public ErroSimplesDto handle(EntidadeNaoEncontradaException exception) {
		return new ErroSimplesDto(
				exception.getMessage()!=null?exception.getMessage():"Não foram encontrados dados para os valores especificados"
					);
	}
	
	
	//Dados técnicamente inválidos. Json mal formado, constante inválida, etc
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ErroSimplesDto handle(HttpMessageNotReadableException exception) {
		if (exception.getCause()!=null) {
			if (exception.getCause() instanceof JsonParseException)
				return new ErroSimplesDto("A requisição tem formato inválido. Possívelmente o json está incorreto");
			if (exception.getCause() instanceof InvalidFormatException e)
				return new ErroSimplesDto("A requisição possui um campo com constante ou formatação inválida. " + e.getOriginalMessage());
		}
		logger.warn("Um controller detectou um HttpMessageNotReadableException com cause vazio: " + exception.getMessage(),
				exception
				);//provavelmente é body vazio. Mas como é um tratamento interno do framework, deixa um log
		return new ErroSimplesDto("A requisição tem formato inválido ou o request body está faltando ");
	}
	
	@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ErroSimplesDto handle(HttpRequestMethodNotSupportedException exception) throws HttpRequestMethodNotSupportedException {
		logger.info(exception.getMessage(), exception);//o client está mandando o verbo errado. Não é problema da api
		return new ErroSimplesDto(exception.getMessage());
	}
	
	//qualquer outro erro não previsto. Não retorna o erro pro usuário, mas loga internamente
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ErroSimplesDto handle(Exception exception) {
		logger.error(exception.getMessage(), exception);
		return new ErroSimplesDto("Ocorreu um erro, logs foram gravados. Contate o suporte");
	}
}
