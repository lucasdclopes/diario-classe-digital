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

import br.univesp.diarioclasse.dto.requests.NovoProfessorDto;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.helpers.CadastroMappers;
import br.univesp.diarioclasse.repositorios.CadastroRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	@Autowired private ProfessorRepository professorDal;
	@Autowired private CadastroMappers mappers;
	@Autowired private CadastroRepository cadastroDal;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody NovoProfessorDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException{
		
		boolean existe = cadastroDal.existsByCpf(dto.cpf());
		System.out.print(existe);
		
		Professor professor = new  Professor(dto.dtAdmissao(), dto.materia(), dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		professor.validarSeJaExiste(cadastroDal);
		
		dto.enderecos().ifPresent( lista -> mappers.novoEnderecoDtoParaEndereco(lista, professor)
				.forEach(cadEnd -> professor.adicionarEndereco(cadEnd))
		);
		
		dto.telefones().ifPresent(lista ->mappers.novoTelefoneDtoParaTelefone(lista, professor)
				.forEach(cadTel -> professor.adicionarTelefone(cadTel))
		);

		Integer id = professorDal.save(professor).getIdProfessor();
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/professores/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Professor> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Optional<Professor> professor = professorDal.findById(id);
		return ResponseEntity.ok(professor.orElseThrow(() -> new EntidadeNaoEncontradaException()));
	}
	
}
