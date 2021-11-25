package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.queryparams.CalendarioAulaParams;
import br.univesp.diarioclasse.dto.requests.CalendarioAulaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesCalendarioAulaDto;
import br.univesp.diarioclasse.dto.responses.ListaCalendarioAulaDto;
import br.univesp.diarioclasse.entidades.CalendarioAula;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.enums.IEnumParseavel;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.helpers.DtoMappers;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;
import br.univesp.diarioclasse.repositorios.MateriaRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TurmaRepository;

@RestController
@RequestMapping("/calendario-aulas")
public class CalendarioAulaController {

	@Autowired private CalendarioAulaRepository calendarioDao;
	@Autowired private ProfessorRepository professorDao;
	@Autowired private MateriaRepository materiaDao;
	@Autowired private TurmaRepository turmaDao;
	
	@Autowired private DtoMappers mappers;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody CalendarioAulaDto dto, UriComponentsBuilder uriBuilder) 
			throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException{
		
		CalendarioAula calendario = new CalendarioAula(
				dto.diaSemana(),dto.hrInicio(),dto.hrFim(),
				materiaDao.findById(dto.idMateria()).orElseThrow(() -> new EntidadeNaoEncontradaException("A matéria informada não foi encontrada")),
				professorDao.findById(dto.idProfessor()).orElseThrow(() -> new EntidadeNaoEncontradaException("O professor informado não foi encontrado")),
				turmaDao.findById(dto.idTurma()).orElseThrow(() -> new EntidadeNaoEncontradaException("A turma informada não foi encontrada"))
				);
		
		calendario.validar(calendarioDao);
		Integer id = calendarioDao.save(calendario).getIdCalendarioAula();
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/calendario-aulas/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesCalendarioAulaDto> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		CalendarioAula calendario = calendarioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		Materia materia = calendario.getMateria();
		Professor professor = calendario.getProfessor();
		Turma turma = calendario.getTurma();
		DetalhesCalendarioAulaDto detalhes = new DetalhesCalendarioAulaDto(
				calendario.getIdCalendarioAula(), calendario.getDiaSemana(), calendario.getHrInicio(), calendario.getHrFim(), 
				mappers.materiaParaDto(materia), 
				mappers.cadastroParaDtoSimples(professor), 
				mappers.turmaPataDto(turma)
				);

		
		return ResponseEntity.ok(detalhes);
	}
	
	@GetMapping
	public ResponseEntity<List<ListaCalendarioAulaDto>> listar(CalendarioAulaParams params,
			@PageableDefault(sort = {"diaSemana","turma.descTurma","hrInicio"}, direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		
		Page<ListaCalendarioAulaDto> pagina = calendarioDao.paginar(
				IEnumParseavel.valueOfTratado(params.diaSemana(),DiaDaSemana.class), 
				params.idTurma(), params.idMateria(),params.idProfessor(), params.hrAula(), paginacao);
		if (pagina.hasContent()) 
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remover(@PathVariable Integer id) throws EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion {
		
		CalendarioAula calendario = calendarioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		calendarioDao.delete(calendario);
		return ResponseEntity.noContent().build();
		
		
	}
	
}
