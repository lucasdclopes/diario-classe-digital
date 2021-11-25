package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@Entity
@Table(name = "aulaPresencaAlunos")
public class AulaPresencaAluno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AulaPresencaAlunoId id = new AulaPresencaAlunoId();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional =  false)
	@MapsId("idAula")
	@JoinColumn(name = "idAula")
	private Aula aula; 
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@MapsId("idAluno")
	@JoinColumn(name = "idAluno")
	private Aluno aluno; 

	private Boolean isPresente;
	private boolean hasAtestado;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public AulaPresencaAluno() {}
	
	

	public AulaPresencaAluno(Aula aula, Aluno aluno, boolean isPresente) throws EntidadeJaExisteException {
		this(aula,aluno);
		this.isPresente = isPresente;
	}
	
	public AulaPresencaAluno(Aula aula, Aluno aluno) throws EntidadeJaExisteException {
		this.aula = aula;
		this.aluno = aluno;
		this.hasAtestado = false;
	}
	
	public void adicionarAtestado() {
		//TODO: Se der tempo, adicionar uma foto/scan do atestado
		this.hasAtestado = true;
	}
	
	public void marcarPresenca() {
		this.isPresente = true;
	}
	public void marcarFalta() {	
		this.isPresente = false;
	}

	
	public Aula getAula() {
		return aula;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public Boolean isPresente() {
		return isPresente;
	}
	
	public boolean hasAtestado() {
		return hasAtestado;
	}

	public AulaPresencaAlunoId getId() {
		return id;
	}


	@Override
	public int hashCode() {
		return Objects.hash(aluno, aula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AulaPresencaAluno other = (AulaPresencaAluno) obj;
		return Objects.equals(aluno, other.aluno) && Objects.equals(aula, other.aula);
	}
		
}
