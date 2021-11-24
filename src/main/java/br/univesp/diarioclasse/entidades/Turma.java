package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;

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
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
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
	
	
	
}
