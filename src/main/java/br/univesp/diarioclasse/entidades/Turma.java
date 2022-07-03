package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.exceptions.AutorizacaoException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EntidadeNaoEncontradaException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.seguranca.UsuarioLogado;

@Entity
@Table(name = "turmas")
public class Turma implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTurma;
	@NotNull @Length(max = 15)
	@NaturalId(mutable = true) //não tem sentido várias turmas no mesmo período com o mesmo nome. Por ex 1a ano B é o suficiente
	private String descTurma;
	@NotNull
	private PeriodoEstudo tpPeriodo;
	@NotNull @NaturalId(mutable = true)
	private TipoNivelEnsino tpNivelEnsino;
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
	private List<Aluno> alunos = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
	private List<Aula> aulas = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY,orphanRemoval = true)
	private List<CalendarioAula> tiposAulas = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Turma() {}

	public Turma(String descTurma, PeriodoEstudo tpPeriodo, TipoNivelEnsino tpNivelEnsino) {
		this.descTurma = descTurma;
		this.tpNivelEnsino = tpNivelEnsino;
		this.tpPeriodo = tpPeriodo;
	}
	
	public void validar (TurmaUnica turmaUnica) throws EntidadeJaExisteException {
		if (turmaUnica.existsByDescTurmaAndTpNivelEnsino(this.descTurma,this.tpNivelEnsino))
			throw new EntidadeJaExisteException("Já existe outra turma com este nome no " + this.tpNivelEnsino.getDescricaoAmigavel() , "descTurma");
	}
	
	public void validarDelecao(UsuarioLogado usuarioLogado) throws EstadoObjetoInvalidoExcpetion {
		if (usuarioLogado.getTipoCadastro() != TipoCadastro.ADMINISTRATIVO )
			throw new AutorizacaoException("Somente um administrador pode deletar uma turma");
		if (!this.aulas.isEmpty())
			throw new EstadoObjetoInvalidoExcpetion("Existem aulas que foram dadas para esta turma. Por isso, para garantir a integridade dos dados, não é possível deleta-la");
		if (!this.alunos.isEmpty())
			throw new EstadoObjetoInvalidoExcpetion("Não é possível deletar uma turma que possua alunos");
	}
	
	public void atualizarDescTurma(String descTurma, TurmaUnica turmaUnica) throws EntidadeJaExisteException {
		if (!this.descTurma.equalsIgnoreCase(descTurma)) {
			this.descTurma = descTurma;
			validar(turmaUnica);
		} 
	}
	
	public void atualizarTpPeriodo(PeriodoEstudo tpPeriodo) throws EstadoObjetoInvalidoExcpetion {
		if (this.tpPeriodo != tpPeriodo)
			throw new EstadoObjetoInvalidoExcpetion("Não é possível alterar o período do estudo da turma");
	}
	
	public void atualizarTpNivelEnsino(TipoNivelEnsino tpNivelEnsino) throws EstadoObjetoInvalidoExcpetion {
		if (this.tpNivelEnsino != tpNivelEnsino)
			throw new EstadoObjetoInvalidoExcpetion("Não é possível alterar o nível de ensino da turma");
	}
	
	public void remover(Aluno aluno) throws EntidadeNaoEncontradaException {
		if (!aluno.getTurma().equals(this))
			throw new EntidadeNaoEncontradaException("O aluno selecionado não pertence a esta turma");
		if (!this.alunos.contains(aluno))
			throw new EntidadeNaoEncontradaException("O aluno selecionado não foi encontrado nesta turma");
		this.alunos.remove(aluno);
		aluno.removerTurmaAtual();
	}
	
	public String getNomeTurmaComNivel() {
		return this.descTurma + " do " + this.getTpNivelEnsino().getDescricaoAmigavel();
	}
	
	public void addAluno(Aluno aluno) {
		if (!alunos.contains(aluno))
			this.alunos.add(aluno);	
		if (!this.equals(aluno.getTurma()))// <- essa validação impede que a recursiva entre em stackoverflow
			aluno.atualizarTurma(this);
	}	
	
	public Integer getIdTurma() {
		return idTurma;
	}

	public String getDescTurma() {
		return descTurma;
	}

	public TipoNivelEnsino getTpNivelEnsino() {
		return tpNivelEnsino;
	}

	public PeriodoEstudo getTpPeriodo() {
		return tpPeriodo;
	}

	public List<Aluno> getAlunos() {
		return Collections.unmodifiableList(alunos);
	}

	public List<Aula> getAulas() {
		return Collections.unmodifiableList(aulas);
	}

	public List<CalendarioAula> getTiposAulas() {
		return Collections.unmodifiableList(tiposAulas);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idTurma);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Turma other = (Turma) obj;
		return Objects.equals(idTurma, other.idTurma);
	}
	
}
