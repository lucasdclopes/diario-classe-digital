package br.univesp.diarioclasse.restcontrollers;

import java.util.List;

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

import br.univesp.diarioclasse.dto.queryparams.AulaParams;
import br.univesp.diarioclasse.dto.requests.AulaDto;
import br.univesp.diarioclasse.dto.requests.PresencaAlunoDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto;
import br.univesp.diarioclasse.dto.responses.ListaAulasDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.Aula;
import br.univesp.diarioclasse.entidades.AulaPresencaAluno;
import br.univesp.diarioclasse.entidades.CalendarioAula;
import br.univesp.diarioclasse.enums.IEnumParseavel;
import br.univesp.diarioclasse.enums.StatusAula;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.helpers.DtoMappers;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
import br.univesp.diarioclasse.repositorios.AulaRepository;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;

@RestController
@RequestMapping("/api/aulas")
public class AulaController {

	@Autowired private AulaRepository aulaDao;
	@Autowired private CalendarioAulaRepository calendarioDao;
	@Autowired private AlunoRepository alunoDao;
	
	@Autowired private DtoMappers mappers;
	
	@PostMapping
	public ResponseEntity<DetalhesAulaDto> cadastrar(@Valid @RequestBody AulaDto dto, UriComponentsBuilder uriBuilder) 
			throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion{
		
		CalendarioAula calendario = calendarioDao.findById(dto.idCalendarioAula()).orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		Aula aula = Aula.comecarAulaDoCalendario(dto.dtAula(), calendario);
		aula.adicionarTodaTurmaNaListaChamada();
		Integer id = aulaDao.save(aula).getIdAula();
		
		DetalhesAulaDto detalhes = mappers.aulaParaDetalhesDto(aula);
		
		return ResponseEntity.created(ControllerHelper.montarUriLocalResource(uriBuilder,"/aulas/{id}",id)).body(detalhes);

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesAulaDto> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Aula aula = aulaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		DetalhesAulaDto detalhes = mappers.aulaParaDetalhesDto(aula);

		return ResponseEntity.ok(detalhes);
	}
	
	
	@GetMapping
	public ResponseEntity<List<ListaAulasDto>> listar(AulaParams params,
			@PageableDefault(sort = {"dtHrIniciada"}, direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		
		Page<Aula> pagina = aulaDao.paginar(params.dtAula(),params.hrAula(),
				IEnumParseavel.valueOfTratado(params.statusAula(), StatusAula.class),
				params.idTurma(),params.idMateria(),params.idProfessor(),paginacao);

		if (pagina.hasContent()) {
			List<ListaAulasDto> aulas = pagina.getContent().stream().map(mappers::aulaParaDto).toList();
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(aulas);
		}
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
	@PutMapping("/{id}/presencas")
	public ResponseEntity<Object> atualizarPresencas(@PathVariable Integer id, @Valid @RequestBody List<PresencaAlunoDto> dtos) throws EntidadeNaoEncontradaException, EntidadeJaExisteException, DadosInvalidosException{
		//TODO: Precisa passar a lista de alunos pro usuário. Passar a lista de alunos da turma. Depois, quando fechar a aula, ve se estão todos na chamada
		Aula aula = aulaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("A aula informada não foi encontrada"));
		
		for (PresencaAlunoDto presenca : dtos) {
			Aluno aluno = alunoDao.findById(presenca.idAluno()).orElseThrow(() -> new EntidadeNaoEncontradaException("O aluno informado não foi encontrado"));
			aula.adicionarChamadaIndividual(new AulaPresencaAluno(
					aula,
					aluno, 
					presenca.isPresente())
					);
		}  
		
		aulaDao.save(aula);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<Object> finalizarAula(@PathVariable Integer id) throws EntidadeNaoEncontradaException, EntidadeJaExisteException, DadosInvalidosException, EstadoObjetoInvalidoExcpetion{
		//TODO: Precisa passar a lista de alunos pro usuário. Passar a lista de alunos da turma. Depois, quando fechar a aula, ve se estão todos na chamada
		Aula aula = aulaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("A aula informada não foi encontrada"));
		
		aula.finalizarAula();
		
		aulaDao.save(aula);
		return ResponseEntity.ok().build();
	}
	
	/*
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remover(@PathVariable Integer id) throws EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion {
		
		CalendarioAula calendario = calendarioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		calendarioDao.delete(calendario);
		return ResponseEntity.noContent().build();
		
		
	}
	*/
	
}
