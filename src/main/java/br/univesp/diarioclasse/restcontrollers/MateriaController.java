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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.univesp.diarioclasse.dto.queryparams.MateriaParams;
import br.univesp.diarioclasse.dto.requests.MateriaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesMateriaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesMateriaDto.ProfessorMateria;
import br.univesp.diarioclasse.dto.responses.ListaMateriasDto;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.enums.IEnumParseavel;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.repositorios.MateriaRepository;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

	@Autowired private MateriaRepository materiaDao;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody MateriaDto dto, UriComponentsBuilder uriBuilder) 
			throws EntidadeJaExisteException, DadosInvalidosException{
		
		Materia materia = new Materia(dto.descMateria(), dto.tpNivelEnsino());
		materia.validar(materiaDao);
		Integer id = materiaDao.save(materia).getIdMateria();
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/materias/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesMateriaDto> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Materia materia = materiaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
				
		DetalhesMateriaDto detalhes = new DetalhesMateriaDto(
				materia.getIdMateria(),materia.getDescMateria(),materia.getTpNivelEnsino(),
				materia.getProfessores().stream().map(ProfessorMateria::new).toList()
				);
		
		return ResponseEntity.ok(detalhes);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Integer id, @Valid @RequestBody MateriaDto dto) 
			throws EntidadeNaoEncontradaException, EntidadeJaExisteException, DadosInvalidosException, EstadoObjetoInvalidoExcpetion{
		
		Materia materia = materiaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		if (dto.descMateria() != null)
			materia.atualizarDescMateria(dto.descMateria(),materiaDao);
		if(dto.tpNivelEnsino() != null)
			materia.atualizarTpNivelEnsino(dto.tpNivelEnsino());
		materiaDao.save(materia);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ListaMateriasDto>> listar(MateriaParams params,
			@PageableDefault(sort = {"tpNivelEnsino","descMateria"}, direction = Direction.ASC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		Page<ListaMateriasDto> pagina = materiaDao.paginar(params.descMateria(),
				IEnumParseavel.valueOfTratado(params.tpNivelEnsino(),TipoNivelEnsino.class),
				paginacao);
		if (pagina.hasContent()) 
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> remover(@PathVariable Integer id) throws EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion {
		
		Materia materia = materiaDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		if (!materia.getProfessores().isEmpty())
			throw new EstadoObjetoInvalidoExcpetion("Não é possível deletar uma matéria que tenha professores lessionando");
		if (!materia.getCalendarioAula().isEmpty())
			throw new EstadoObjetoInvalidoExcpetion("Não é possível deletar uma matéria que esteja no calendário de aulas");
		materiaDao.delete(materia);
		return ResponseEntity.noContent().build();
		
		
	}
	
}
