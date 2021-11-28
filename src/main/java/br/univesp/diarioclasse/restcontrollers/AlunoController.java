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

import br.univesp.diarioclasse.dto.queryparams.AlunoParams;
import br.univesp.diarioclasse.dto.queryparams.CadastroParams;
import br.univesp.diarioclasse.dto.requests.AlunoDto;
import br.univesp.diarioclasse.dto.responses.ListaAlunosDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.helpers.DtoMappers;
import br.univesp.diarioclasse.repositorios.AlunoRepository;

@RestController
@RequestMapping("/api/alunos")
public class AlunoController {

	@Autowired private AlunoRepository alunoDao;
	
	@Autowired private DtoMappers mappers;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody AlunoDto dto, UriComponentsBuilder uriBuilder) throws EntidadeJaExisteException, DadosInvalidosException{
				
		Aluno aluno = new Aluno(dto.getNroMatricula(), dto.getDtMatricula(), dto.getRa(), Optional.ofNullable(dto.getTurma()), dto.getNome(), dto.getCpf(), dto.getRg(), 
				dto.getDtNascimento(), dto.getSexo(), dto.getNomeMae(), dto.getNomePai(), dto.getEmailContato());
		
		aluno.validarSeAlunoJaExiste(alunoDao,alunoDao);
		
		if (dto.getEnderecos() != null)	
			mappers.novoEnderecoDtoParaEndereco(dto.getEnderecos(), aluno).forEach(cadEnd -> aluno.adicionarEndereco(cadEnd));
		
		if (dto.getTelefones() != null)
			mappers.novoTelefoneDtoParaTelefone(dto.getTelefones(), aluno).forEach(cadTel -> aluno.adicionarTelefone(cadTel));
			
		Integer id = alunoDao.save(aluno).getIdAluno();
		
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/alunos/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Integer id, @Valid @RequestBody AlunoDto dto) throws EntidadeNaoEncontradaException, EntidadeJaExisteException, DadosInvalidosException{
		//TODO: Atualizar o endreço e telefone
		Aluno aluno = alunoDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		mappers.atualizarCadastroDeDto(dto, aluno, alunoDao);
		if (dto.getRa() != null) {
			aluno.atualizarRa(dto.getRa(), alunoDao);
		}
		if (dto.getNroMatricula() != null) {
			aluno.atualizarNroMatricula(dto.getNroMatricula(), alunoDao);
		}
		if (dto.getDtMatricula() != null) {
			aluno.atualizarDtMatricula(dto.getDtMatricula());
		}
		alunoDao.save(aluno);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Aluno> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Aluno aluno = alunoDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		Long totalFaltas = alunoDao.calcularTotalFaltas(id);
		return ResponseEntity.ok(aluno);
	}
	
	@GetMapping
	public ResponseEntity<List<ListaAlunosDto>> listar(AlunoParams AlunoParams,CadastroParams cadParams,
			@PageableDefault(sort = "dtMatricula", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		Page<ListaAlunosDto> pagina = alunoDao.paginar(cadParams.cpf(),AlunoParams.ra(),AlunoParams.nroMatricula(),cadParams.nome(),paginacao);
		if (pagina.hasContent()) {
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		}
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
}
