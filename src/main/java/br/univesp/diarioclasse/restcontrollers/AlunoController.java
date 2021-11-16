package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.requests.NovoAlunoDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
import br.univesp.diarioclasse.services.AlunoService;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired
	private AlunoRepository alunoDal;
	
	@Autowired
	private AlunoService service;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody NovoAlunoDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException{
		Integer id = service.criarNovoAluno(dto);
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/alunos/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Optional<Aluno> aluno = alunoDal.findById(id);
		return ResponseEntity.ok(aluno.orElseThrow(() -> new EntidadeNaoEncontradaException()));
	}
	
}
