package br.univesp.diarioclassedigital.restcontrollers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclassedigital.dto.requests.NovoProfessorDto;
import br.univesp.diarioclassedigital.entidades.Professor;
import br.univesp.diarioclassedigital.repositorios.ProfessorRepositorio;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	@Autowired
	private ProfessorRepositorio professorDal;
	
	@PostMapping
	public ResponseEntity<Professor> cadastrar(@Valid @RequestBody NovoProfessorDto dto, UriComponentsBuilder uriBuilder){
		Professor professor = new  Professor(dto.dtAdmissao(), null, dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		Professor professorSalvo = professorDal.save(professor);
		URI uri = ControllerHelper.montarUriResourceCriado(uriBuilder,"/professores/{id}",professorSalvo.getIdProfessor());
		return ResponseEntity.created(uri).body(professorSalvo);
	}
	
}
