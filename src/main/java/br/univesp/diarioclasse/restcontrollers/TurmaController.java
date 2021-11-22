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

import br.univesp.diarioclasse.dto.queryparams.TurmaParams;
import br.univesp.diarioclasse.dto.requests.TurmaDto;
import br.univesp.diarioclasse.dto.responses.ListaTurmasDto;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.enums.IEnumParseavel;
import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.repositorios.TurmaRepository;

@RestController
@RequestMapping("/turmas")
public class TurmaController {

	@Autowired private TurmaRepository turmaDao;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody TurmaDto dto, UriComponentsBuilder uriBuilder) 
			throws EntidadeJaExisteException, DadosInvalidosException{
		
		Turma turma = new Turma(dto.descTurma(), dto.tpPeriodo(), dto.tpNivelEnsino());
		turma.validar(turmaDao);
		Integer id = turmaDao.save(turma).getIdTurma();
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/professores/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Turma> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Optional<Turma> turma = turmaDao.findById(id);
		return ResponseEntity.ok(turma.orElseThrow(() -> new EntidadeNaoEncontradaException()));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Integer id, @Valid @RequestBody TurmaDto dto) 
			throws EntidadeNaoEncontradaException, EntidadeJaExisteException, DadosInvalidosException, EstadoObjetoInvalidoExcpetion{
		
		Turma turma = turmaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		if (dto.descTurma() != null)
			turma.atualizarDescTurma(dto.descTurma(), turmaDao);
		if(dto.tpNivelEnsino() != null)
			turma.atualizarTpNivelEnsino(dto.tpNivelEnsino());
		if (dto.tpPeriodo() != null) {
			turma.atualizarTpPeriodo(dto.tpPeriodo());
		}
		turmaDao.save(turma);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ListaTurmasDto>> listar(TurmaParams params,
			@PageableDefault(sort = {"tpNivelEnsino","descTurma"}, direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		Page<ListaTurmasDto> pagina = turmaDao.paginar(params.descTurma(),
				IEnumParseavel.valueOfTratado(params.tpNivelEnsino(),TipoNivelEnsino.class),
				IEnumParseavel.valueOfTratado(params.tpPeriodo(),PeriodoEstudo.class)
				,paginacao);
		if (pagina.hasContent()) 
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
}
