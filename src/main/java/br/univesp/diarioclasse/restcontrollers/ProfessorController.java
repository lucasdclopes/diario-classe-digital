package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.requests.NovoProfessorDto;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.repositorios.CadastroRepositorio;
import br.univesp.diarioclasse.repositorios.ProfessorRepositorio;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	@Autowired
	private ProfessorRepositorio professorDal;
	@Autowired
	private CadastroRepositorio cadastroDal;
	
	@PostMapping
	public ResponseEntity<Professor> cadastrar(@Valid @RequestBody NovoProfessorDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException{
		
		Professor professor = new  Professor(dto.dtAdmissao(), null, dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		if (cadastroDal.existsCadastroByCpf(dto.cpf()))
			throw new EntidadeJaExisteException("Já existe um cadastro com este cpf", dto.cpf());
			

		Professor professorSalvo = professorDal.save(professor);
		URI uri = ControllerHelper.montarUriResourceCriado(uriBuilder,"/professores/{id}",professorSalvo.getIdProfessor());
		return ResponseEntity.created(uri).body(professorSalvo);
	}
	
}
