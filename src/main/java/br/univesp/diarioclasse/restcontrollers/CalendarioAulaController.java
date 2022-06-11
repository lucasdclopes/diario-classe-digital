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
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.helpers.DtoMappers;
import br.univesp.diarioclasse.repositorios.CalendarioAulaRepository;
import br.univesp.diarioclasse.repositorios.MateriaRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TurmaRepository;

@RestController
@RequestMapping("/api/calendario-aulas")
public class CalendarioAulaController {

	/**
	 * Objetos de acesso ao dados. Este controller precisa trabalhar com várias entidades
	 */
	@Autowired private CalendarioAulaRepository calendarioDao;
	@Autowired private ProfessorRepository professorDao;
	@Autowired private MateriaRepository materiaDao;
	@Autowired private TurmaRepository turmaDao;
	
	/**
	 * Ajuda a converter os dados das entidades para classes dtos
	 */
	@Autowired private DtoMappers mappers;
	
	/**
	 * Nova aula no calendário
	 */
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody CalendarioAulaDto dto, UriComponentsBuilder uriBuilder) 
			throws EntidadeJaExisteException, DadosInvalidosException, EntidadeNaoEncontradaException{
		
		
		//busca as entidades de materia, professor e turma e as utiliza para inicializar um calendario de aula
		CalendarioAula calendario = new CalendarioAula(
				dto.diaSemana(),dto.hrInicio(),dto.hrFim(),
				materiaDao.findById(dto.idMateria()).orElseThrow(() -> new EntidadeNaoEncontradaException("A matéria informada não foi encontrada")),
				professorDao.findById(dto.idProfessor()).orElseThrow(() -> new EntidadeNaoEncontradaException("O professor informado não foi encontrado")),
				turmaDao.findById(dto.idTurma()).orElseThrow(() -> new EntidadeNaoEncontradaException("A turma informada não foi encontrada"))
				);
		
		calendario.validar(calendarioDao); //valida se os dados são consistentes
		Integer id = calendarioDao.save(calendario).getIdCalendarioAula();//salva, recupera o id e retorna para o client da api
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/calendario-aulas/{id}",id);
		return ResponseEntity.created(uri).build();
	}
	
	/**
	 * Busca dados detalhados de uma aula do calendário
	 */
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesCalendarioAulaDto> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		CalendarioAula calendario = calendarioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		
		//busca as entidades de materia, cadastro e turma para retornar dado de calendário
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
	
	/**
	 * Retorna dados em página do calendário de aula
	 */
	@GetMapping
	public ResponseEntity<List<ListaCalendarioAulaDto>> listar(CalendarioAulaParams params,
			@PageableDefault(sort = {"diaSemana","hrInicio","turma.descTurma"}, direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		//tem um Pageable default caso o client da api não informe dados de paginação
		
		//vai na Dao e recupera alguns dados. O próprio spring filtra a paginação
		Page<ListaCalendarioAulaDto> pagina = calendarioDao.paginar(
				IEnumParseavel.valueOfTratado(params.diaSemana(),DiaDaSemana.class), 
				params.idTurma(), params.idMateria(),params.idProfessor(), params.hrAula(), paginacao);
		if (pagina.hasContent()) 
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		else
			throw new EntidadeNaoEncontradaException();		
	}

	/**
	 * Deleta uma aula do calendário
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remover(@PathVariable Integer id) throws EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion {
		
		CalendarioAula calendario = calendarioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());//não existe, retorna com erro
		calendarioDao.delete(calendario);
		return ResponseEntity.noContent().build();				
	}	
}
