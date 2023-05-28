package br.univesp.diarioclasse.helpers;

import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.dto.requests.CadastroDto;
import br.univesp.diarioclasse.dto.responses.AlunoDadosBasicosDto;
import br.univesp.diarioclasse.dto.responses.CadastroDadosBasicosDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesAulaDto.PresencaAlunoAulaDto;
import br.univesp.diarioclasse.dto.responses.DetalhesTurmaDto;
import br.univesp.diarioclasse.dto.responses.ListaAulasDto;
import br.univesp.diarioclasse.dto.responses.ListaMateriasDto;
import br.univesp.diarioclasse.dto.responses.ListaTurmasDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.Aula;
import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.CadastroExistente;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@Component
public class DtoMappers {
	
	public ListaTurmasDto turmaPataDto(Turma turma) {
		return new ListaTurmasDto(turma.getIdTurma(), turma.getDescTurma(), turma.getTpPeriodo(), turma.getTpNivelEnsino());
	}
	
	public DetalhesTurmaDto turmaPataDetalhesDto(Turma turma, Long totalFaltas) {
		return new DetalhesTurmaDto(turma.getIdTurma(), turma.getDescTurma(), turma.getTpPeriodo(), turma.getTpNivelEnsino(),
				turma.getAlunos().stream().map(this::alunoParaDtoSimples).toList(),
				totalFaltas);
	}
	
	public CadastroDadosBasicosDto cadastroParaDtoSimples(Cadastro cadastro) {
		return new CadastroDadosBasicosDto(cadastro.getIdCadastro(), cadastro.getNome());
	}
	
	public AlunoDadosBasicosDto alunoParaDtoSimples(Aluno aluno) {
		return new AlunoDadosBasicosDto(aluno.getIdCadastro(), aluno.getNome(), aluno.getNroMatricula(),aluno.getNIS());
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
		if (cadastroDto.getIsMaeSolo() != null)
		    cadastro.alterarIsMaeSolo(cadastroDto.getIsMaeSolo());
		if (cadastroDto.getMae()!= null)
			cadastro.atualizarMae(cadastroDto.getMae());
		if (cadastroDto.getPai()!= null)
			cadastro.atualizarPai(cadastroDto.getPai());
		if (cadastroDto.getCpf()!= null)
			cadastro.atualizarCpf(cadastroDto.getCpf(),cadastroExistente);
		if (cadastroDto.getRg()!= null)
			cadastro.atualizarRg(cadastroDto.getRg());
		if (cadastroDto.getDtNascimento()!= null)
			cadastro.atualizarDtNascimento(cadastroDto.getDtNascimento());
		if (cadastroDto.getSexo()!= null)
			cadastro.atualizarSexo(cadastroDto.getSexo());
		if (cadastroDto.getEmailContato()!= null)
			cadastro.atualizarEmailContato(cadastroDto.getEmailContato());
		if (cadastroDto.getEndComercial()!= null)
			cadastro.alterarEnderecoComercial(cadastroDto.getEndComercial());
		if (cadastroDto.getEndResidencial() != null)
			cadastro.alterarEnderecoResidencial(cadastroDto.getEndResidencial());
		if (cadastroDto.getTelCelular() != null)
			cadastro.alterarTelefoneCelular(cadastroDto.getTelCelular());
		if (cadastroDto.getTelFixo() != null)
			cadastro.alterarTelefoneFixo(cadastroDto.getTelFixo());
		if (cadastroDto.getIsFilhoUnico() != null)
		    cadastro.alterarIsFilhoUnico(cadastroDto.getIsFilhoUnico());
		if (cadastroDto.getIsAbaixoPeso() != null)
		    cadastro.alterarIsAbaixoPeso(cadastroDto.getIsAbaixoPeso());
		if (cadastroDto.getRecebePensaoAlimenticia() != null)
		    cadastro.alterarRecebePensaoAlimenticia(cadastroDto.getRecebePensaoAlimenticia());
		if (cadastroDto.getConviveDoente() != null)
		    cadastro.alterarConviveDoente(cadastroDto.getConviveDoente());
		
	}
}
