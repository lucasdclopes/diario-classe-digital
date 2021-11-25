package br.univesp.diarioclasse.restcontrollers;

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
import br.univesp.diarioclasse.dto.requests.AulaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto.PresencaAlunosAulaDto;
import br.univesp.diarioclasse.dto.responses.ListaCalendarioAulaDto;
import br.univesp.diarioclasse.entidades.Aula;
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
import br.univesp.diarioclasse.repositorios.AulaRepository;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;
import br.univesp.diarioclasse.repositorios.MateriaRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TurmaRepository;

@RestController
@RequestMapping("/aulas")
public class AulaController {

	@Autowired private AulaRepository aulaDao;
	@Autowired private CalendarioAulaRepository calendarioDao;
	@Autowired private ProfessorRepository professorDao;
	@Autowired private MateriaRepository materiaDao;
	@Autowired private TurmaRepository turmaDao;
	
	@Autowired private DtoMappers mappers;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody AulaDto dto, UriComponentsBuilder uriBuilder) 
			throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion{
		
		CalendarioAula calendario = calendarioDao.findById(dto.idCalendarioAula()).orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		Aula aula = Aula.comecarAulaDoCalendario(dto.dtAula(), calendario);

		Integer id = aulaDao.save(aula).getIdAula();
		return ResponseEntity.created(ControllerHelper.montarUriLocalResource(uriBuilder,"/aulas/{id}",id)).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesAulaDto> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Aula aula = aulaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		Materia materia = aula.getMateria();
		Professor professor = aula.getProfessor();
		Turma turma = aula.getTurma();
		
		DetalhesAulaDto detalhes = new DetalhesAulaDto(aula.getIdAula(), aula.getDtAula(), aula.getDtHrIniciada(), aula.getDtHrFinalizada(), aula.getStatusAula(),
				mappers.materiaParaDto(materia), 
				mappers.cadastroParaDtoSimples(professor), 
				mappers.turmaPataDto(turma),
				aula.getPresencaAlunos().stream().map(PresencaAlunosAulaDto::new).toList());
		
				/*
		DetalhesCalendarioAulaDto detalhes = new DetalhesCalendarioAulaDto(
				calendario.getIdCalendarioAula(), calendario.getDiaSemana(), calendario.getHrInicio(), calendario.getHrFim(), 
				new ListaMateriasDto(materia.getIdMateria(), materia.getDescMateria(), materia.getTpNivelEnsino()),
				new CadastroDadosBasicosDto(professor.getIdCadastro(), professor.getNome()),
				new ListaTurmasDto(turma.getIdTurma(), turma.getDescTurma(), turma.getTpPeriodo(), turma.getTpNivelEnsino())
				);
				*/

		
		return ResponseEntity.ok(detalhes);
	}
	
	@GetMapping
	public ResponseEntity<List<ListaCalendarioAulaDto>> listar(CalendarioAulaParams params,
			@PageableDefault(sort = {"diaSemana","turma.descTurma","hrInicio"}, direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		
		Page<ListaCalendarioAulaDto> pagina = calendarioDao.paginar(
				IEnumParseavel.valueOfTratado(params.diaSemana(),DiaDaSemana.class), 
				params.idTurma(), params.idMateria(),params.idProfessor(), paginacao);
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
