package br.univesp.diarioclasse.helpers;

import java.util.List;

import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.dto.requests.CadastroDto;
import br.univesp.diarioclasse.dto.requests.EnderecoDto;
import br.univesp.diarioclasse.dto.requests.TelefoneDto;
import br.univesp.diarioclasse.dto.responses.CadastroDadosBasicosDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto;
import br.univesp.diarioclasse.dto.responses.ListaMateriasDto;
import br.univesp.diarioclasse.dto.responses.ListaTurmasDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto.PresencaAlunoAulaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesTurmaDto;
import br.univesp.diarioclasse.dto.responses.ListaAulasDto;
import br.univesp.diarioclasse.entidades.Aula;
import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.CadastroExistente;
import br.univesp.diarioclasse.entidades.Endereco;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@Component
public class DtoMappers {

	public List<Endereco> novoEnderecoDtoParaEndereco (List<EnderecoDto> enderecos, Cadastro cadastro) {
		return enderecos.stream().map(end -> (
				new Endereco(end.logradouro(), end.numeroEndereco(), end.complementoEndereco(), end.cep(), 
						end.bairro(), end.cidade(), end.uf(), end.tpEndereco(), cadastro))
				)
				.toList();
	}
	
	public List<Telefone> novoTelefoneDtoParaTelefone (List<TelefoneDto> telefones, Cadastro cadastro) {		
		return telefones.stream().map(tel -> (		
				new Telefone(tel.tpTelefone(), tel.ddd(), tel.numeroTelefone(), cadastro)))	
				.toList();
	}
	
	public ListaTurmasDto turmaPataDto(Turma turma) {
		return new ListaTurmasDto(turma.getIdTurma(), turma.getDescTurma(), turma.getTpPeriodo(), turma.getTpNivelEnsino());
	}
	
	public DetalhesTurmaDto turmaPataDetalhesDto(Turma turma) {
		return new DetalhesTurmaDto(turma.getIdTurma(), turma.getDescTurma(), turma.getTpPeriodo(), turma.getTpNivelEnsino(),
				turma.getAlunos().stream().map(this::cadastroParaDtoSimples).toList());
	}
	
	public CadastroDadosBasicosDto cadastroParaDtoSimples(Cadastro cadastro) {
		return new CadastroDadosBasicosDto(cadastro.getIdCadastro(), cadastro.getNome());
	}
	
	public ListaAulasDto aulaParaDto(Aula aula) {
		return new ListaAulasDto(aula.getIdAula(), aula.getDtAula(), aula.getDtHrIniciada(), aula.getDtHrFinalizada(), aula.getStatusAula(),
				this.materiaParaDto(aula.getMateria()), 
				this.cadastroParaDtoSimples(aula.getProfessor()), 
				this.turmaPataDto(aula.getTurma()));
	}
	
	public DetalhesAulaDto aulaParaDetalhesDto(Aula aula) {
		return new DetalhesAulaDto(aula.getIdAula(), aula.getDtAula(), aula.getDtHrIniciada(), aula.getDtHrFinalizada(), aula.getStatusAula(),
				this.materiaParaDto(aula.getMateria()), 
				this.cadastroParaDtoSimples(aula.getProfessor()), 
				this.turmaPataDto(aula.getTurma()),
				aula.getPresencaAlunos().stream().map(PresencaAlunoAulaDto::new).toList());
	}
	
	public ListaMateriasDto materiaParaDto(Materia materia) {
		return new ListaMateriasDto(materia.getIdMateria(), materia.getDescMateria(),materia.getTpNivelEnsino());
	}
	
	public void atualizarCadastroDeDto(CadastroDto cadastroDto, Cadastro cadastro, CadastroExistente cadastroExistente) throws EntidadeJaExisteException, DadosInvalidosException {
		if (cadastroDto.getNome()!= null)
			cadastro.atualizarNome(cadastroDto.getNome());
		if (cadastroDto.getNomeMae()!= null)
			cadastro.atualizarNomeMae(cadastroDto.getNomeMae());
		if (cadastroDto.getNomePai()!= null)
			cadastro.atualizarNomePai(cadastroDto.getNomePai());
		if (cadastroDto.getCpf()!= null)
			cadastro.atualizarCpf(cadastroDto.getCpf(),cadastroExistente);
		if (cadastroDto.getRg()!= null)
			cadastro.atualizarRg(cadastroDto.getRg());
		if (cadastroDto.getDtNascimento()!= null)
			cadastro.atualizarDtNascimento(cadastroDto.getDtNascimento());
		if (cadastroDto.getSexo()!= null)
			cadastro.atualizarSexo(cadastroDto.getSexo());
		
	}
}
