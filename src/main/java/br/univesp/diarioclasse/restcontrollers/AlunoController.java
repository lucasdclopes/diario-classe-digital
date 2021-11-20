package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;
import java.util.List;
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

import br.univesp.diarioclasse.dto.requests.AlunoParamFiltro;
import br.univesp.diarioclasse.dto.requests.CadastroParamFiltro;
import br.univesp.diarioclasse.dto.requests.NovoAlunoDto;
import br.univesp.diarioclasse.dto.responses.ListaAlunosDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.helpers.CadastroMappers;
import br.univesp.diarioclasse.repositorios.AlunoRepository;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

	@Autowired private AlunoRepository alunoDal;
	
	@Autowired private CadastroMappers mappers;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody NovoAlunoDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException{
				
		Aluno aluno = new Aluno(dto.nroMatricula(), dto.dtMatricula(), dto.ra(), dto.turma(), dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		aluno.validarSeJaExiste(alunoDal);
		
		dto.enderecos().ifPresent( lista -> mappers.novoEnderecoDtoParaEndereco(lista, aluno)
				.forEach(cadEnd -> aluno.adicionarEndereco(cadEnd))
		);
		
		dto.telefones().ifPresent(lista ->mappers.novoTelefoneDtoParaTelefone(lista, aluno)
				.forEach(cadTel -> aluno.adicionarTelefone(cadTel))
		);
			
		Integer id = alunoDal.save(aluno).getIdAluno();
		
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/alunos/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Optional<Aluno> aluno = alunoDal.findById(id);
		return ResponseEntity.ok(aluno.orElseThrow(() -> new EntidadeNaoEncontradaException()));
	}
	
	@GetMapping
	public ResponseEntity<List<ListaAlunosDto>> listar(AlunoParamFiltro AlunoParams,CadastroParamFiltro cadParams) throws EntidadeNaoEncontradaException{
		List<ListaAlunosDto> alunos = cadParams.nome()==null? alunoDal.findByCpfOrRaOrNroMatricula(cadParams.cpf(),AlunoParams.ra(),AlunoParams.nroMatricula())
					: alunoDal.findByCpfOrRaOrNroMatriculaOrNomeStartingWith(cadParams.cpf(),AlunoParams.ra(),AlunoParams.nroMatricula(),cadParams.nome());
		if (!alunos.isEmpty())
			return ResponseEntity.ok(alunos);
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
}
