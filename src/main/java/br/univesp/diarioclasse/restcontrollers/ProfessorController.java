package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.requests.CadastroParamFiltro;
import br.univesp.diarioclasse.dto.requests.NovoProfessorDto;
import br.univesp.diarioclasse.dto.responses.ListaProfessorDto;
import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.helpers.CadastroMappers;
import br.univesp.diarioclasse.repositorios.CadastroRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;

@RestController
@RequestMapping("/professores")
public class ProfessorController {

	@Autowired private ProfessorRepository professorDao;
	@Autowired private CadastroMappers mappers;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody NovoProfessorDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException, DadosInvalidosException{
		
		boolean existe = professorDao.existsByCpf(dto.cpf());
		System.out.print(existe);
		
		Professor professor = new  Professor(dto.dtAdmissao(), dto.materia(), dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		professor.validarSeJaExiste(professorDao);
		
		dto.enderecos().ifPresent( lista -> mappers.novoEnderecoDtoParaEndereco(lista, professor)
				.forEach(cadEnd -> professor.adicionarEndereco(cadEnd))
		);
		
		dto.telefones().ifPresent(lista ->mappers.novoTelefoneDtoParaTelefone(lista, professor)
				.forEach(cadTel -> professor.adicionarTelefone(cadTel))
		);

		Integer id = professorDao.save(professor).getIdProfessor();
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/professores/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Professor> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Optional<Professor> professor = professorDao.findById(id);
		return ResponseEntity.ok(professor.orElseThrow(() -> new EntidadeNaoEncontradaException()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Professor> atualizar(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		//TODO: Atualizar o endreço e telefone
		Professor professor = professorDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		return null;
		
	}
	
	@GetMapping
	public ResponseEntity<List<ListaProfessorDto>> listar(CadastroParamFiltro cadParams,
			@PageableDefault(sort = "dtAdmissao", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		Page<ListaProfessorDto> pagina = professorDao.paginar(cadParams.cpf(),cadParams.nome(),paginacao);
		if (pagina.hasContent()) {
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		}
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
}
