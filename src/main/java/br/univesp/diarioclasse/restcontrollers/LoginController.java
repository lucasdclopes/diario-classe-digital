package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;
import java.time.LocalDate;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.requests.CadastroAdmDto;
import br.univesp.diarioclasse.dto.requests.LoginDto;
import br.univesp.diarioclasse.dto.responses.LoginOkDto;
import br.univesp.diarioclasse.entidades.Administrador;
import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.DadosParente;
import br.univesp.diarioclasse.entidades.Login;
import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.exceptions.AutenticacaoException;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.repositorios.CadastroRepository;
import br.univesp.diarioclasse.repositorios.LoginRepository;
import br.univesp.diarioclasse.seguranca.Cifrador;
import br.univesp.diarioclasse.seguranca.GeradorToken;

@RestController
@RequestMapping("/api/")
public class LoginController {
	
	@Autowired private LoginRepository loginDao;
	@Autowired private CadastroRepository cadastroDao;
	
	@PostMapping("/cadastros/{id}/logins")
	public ResponseEntity<Object> cadastrar(@PathVariable("id") Integer idCadastro, 
			@Valid @RequestBody LoginDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException{
				
		Cadastro cadsatro = cadastroDao.findById(idCadastro).orElseThrow(() -> new EntidadeNaoEncontradaException("O cadastro selecionado não existe"));

		Login login = new Login(dto.emailLogin(), dto.senha(), cadsatro,  new Cifrador());
		login.validarSeJaExiste(loginDao);
			
		Integer id = loginDao.save(login).getIdLogin();
		
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/cadastros/" + idCadastro + "/logins/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@PostMapping("/cadastros-adm/")//somente durante os testes, para criar cadastros adm de acesso ao site
	public ResponseEntity<Object> cadastrarAdm(@Valid @RequestBody CadastroAdmDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException{
			
		Cadastro adm = new Administrador(
				dto.getNome(),dto.getCpf(),"00000000",LocalDate.now().minusYears(30),Sexo.DESCONHECIDO,
				new DadosParente("Teste mãe", "00000000000",new Telefone("000", "000000000"),false),
				null,
				"teste@teste.com.br",
				null,
				null,
				new Telefone("000", "000000000"),
				new Telefone("000", "000000000")
				);
				
	
		if (cadastroDao.existsByCpf(dto.getCpf()))
			throw new EntidadeJaExisteException("Já existe outro usuário com esta identificação", "cpf");

		Integer id = cadastroDao.save(adm).getIdCadastro();
		
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/cadastros/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@PostMapping("/logar")
	public ResponseEntity<LoginOkDto> logar(@Valid @RequestBody LoginDto dto) {
		Login login = loginDao.findByEmailLogin(dto.emailLogin().strip()).orElseThrow(() -> new AutenticacaoException()); //busca pelo email, que retorna um optional. Se não existir, joga erro...
		if (!login.isSenhaValida(dto.senha(), new Cifrador())) //já bateu o email no select, bate a senha, passando o cifrador. Concentrando tudo no mesmo cifrador é mais fácil trocar a criptografia caso necessário
			throw new AutenticacaoException(); //o handler pega este erro e trata adequadamente
		else {
			String token = new GeradorToken().gerarTokenAcesso(login);
			login.definirTokenAcesso(token);
			loginDao.save(login);
			return ResponseEntity.ok(new LoginOkDto(token,login.getCadastro().getIdCadastro(),login.getCadastro().getNome(),login.getCadastro().getTipoCadastro())
					); //responde com o token de acesso
		}
		
	}
	/*
	@GetMapping("/logar")
	public ResponseEntity<Object> logar() {
		return null;
	}
	*/

	/*
	@PostMapping
	public ResponseEntity<Object> realizarLogin(LoginDto dto) {
		
	}
	*/
}
