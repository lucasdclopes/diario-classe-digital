package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;
import java.time.LocalDateTime;
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

import br.univesp.diarioclasse.dto.queryparams.BeneficioParams;
import br.univesp.diarioclasse.dto.requests.BeneficioDto;
import br.univesp.diarioclasse.dto.responses.ListaBeneficiosDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.Beneficio;
import br.univesp.diarioclasse.exceptions.AutorizacaoException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.helpers.ControllerHelper;
import br.univesp.diarioclasse.repositorios.AlunoRepository;
import br.univesp.diarioclasse.repositorios.BeneficioRepository;
import br.univesp.diarioclasse.seguranca.UsuarioLogado;

@RestController
@RequestMapping("/api/beneficios")
public class BeneficioController {

	@Autowired private AlunoRepository alunoDao;
	@Autowired private BeneficioRepository beneficioDao;
	
	@Autowired private UsuarioLogado usuarioLogado;
	
	@PostMapping
	public ResponseEntity<Object> cadastrar(@Valid @RequestBody BeneficioDto dto, UriComponentsBuilder uriBuilder) throws EntidadeNaoEncontradaException {
				
		if (dto.getIdAluno().equals(0))
			throw new EntidadeNaoEncontradaException("Nenhum aluno selecionado");
		Aluno aluno = alunoDao.findById(dto.getIdAluno()).orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));
		Beneficio bene = new Beneficio(dto.getDtRecebimento(),dto.getResponsavelRecebimento(),dto.getDescBeneficio(),aluno);
			
		Integer id = beneficioDao.save(bene).getIdBeneficio();
		
		URI uri = ControllerHelper.montarUriLocalResource(uriBuilder,"/beneficios/{id}",id);
		return ResponseEntity.created(uri).build();

	}
	
	
	@PutMapping("/{id}")
	public ResponseEntity<Object> atualizar(@PathVariable Integer id, @Valid @RequestBody BeneficioDto dto) throws EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion {
		
		Beneficio bene = beneficioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		if (bene.getDtRegistrado().isAfter(LocalDateTime.now().plusHours(48))) //evitar adulterações de registros
			throw new AutorizacaoException("Por segurança, não é possível alterar registros que foram feitos a mais de 48 horas");
		if (dto.getIdAluno() != null) {
			if (dto.getIdAluno().equals(0))
				throw new EntidadeNaoEncontradaException("Nenhum aluno selecionado");
			Aluno aluno = alunoDao.findById(dto.getIdAluno()).orElseThrow(() -> new EntidadeNaoEncontradaException("Aluno não encontrado"));
			bene.atualizarAluno(aluno);
		}
		if (dto.getDtRecebimento() != null) {
			bene.atualizarDtRecebimento(dto.getDtRecebimento());
		}
		if (dto.getResponsavelRecebimento() != null) {
			bene.atualizarResponsavelRecebimento(dto.getResponsavelRecebimento());
		}
		if (dto.getDescBeneficio() != null) {
			bene.atualizarDescBeneficio(dto.getDescBeneficio());
		}		


		beneficioDao.save(bene);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Beneficio> encontrarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException{
		Beneficio bene = beneficioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		return ResponseEntity.ok(bene);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deletarPorid(@PathVariable Integer id) throws EntidadeNaoEncontradaException, EstadoObjetoInvalidoExcpetion{
		Beneficio bene = beneficioDao.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException());
		bene.validarDelecao(usuarioLogado);
		beneficioDao.delete(bene);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping
	public ResponseEntity<List<ListaBeneficiosDto>> listar(BeneficioParams params,
			@PageableDefault(sort = "dtRecebimento", direction = Direction.DESC, page = 0, size = 10) Pageable paginacao
			) throws EntidadeNaoEncontradaException{
			
		Page<ListaBeneficiosDto> pagina = beneficioDao.paginar(params.idAluno(),params.dtRecebimentoInicio(),params.dtRecebimentoFim(),params.responsavelRecebimento(),
				paginacao);
		if (pagina.hasContent()) {
			return ResponseEntity.ok().headers(ControllerHelper.adicionarHeaderPaginacao(pagina.getTotalPages(), pagina.hasNext())).body(pagina.getContent());
		}
		else
			throw new EntidadeNaoEncontradaException();
			
	}
	
}
