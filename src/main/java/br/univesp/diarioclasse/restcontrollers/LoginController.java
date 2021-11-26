package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.requests.LoginDto;
import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.Login;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.helpers.Cifrador;
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.helpers.DtoMappers;
import br.univesp.diarioclasse.repositorios.CadastroRepository;
import br.univesp.diarioclasse.repositorios.LoginRepository;

@RestController
@RequestMapping("/cadastros")
public class LoginController {
	
	@Autowired private LoginRepository loginDao;
	@Autowired private CadastroRepository cadastroDao;
	
	@Autowired private DtoMappers mappers;
	
	@PostMapping("/{id}/logins")
	public ResponseEntity<Object> cadastrar(@PathVariable("id") Integer idCadastro, 
			@Valid @RequestBody LoginDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException{
				
		Cadastro cadsatro = cadastroDao.findById(idCadastro).orElseThrow(() -> new EntidadeNaoEncontradaException("O cadastro selecionado não existe"));
		Cifrador sha = new Cifrador();
		Login login = new Login(dto.email(), sha.gerarSha256(dto.senha()), cadsatro);
		login.validarSeJaExiste(loginDao);
			
		Integer id = loginDao.save(login).getIdLogin();
		
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/cadastros/" + idCadastro + "/logins/{id}",id);
		return ResponseEntity.created(uri).build();

	}

	/*
	@PostMapping
	public ResponseEntity<Object> realizarLogin(LoginDto dto) {
		
	}
	*/
}
