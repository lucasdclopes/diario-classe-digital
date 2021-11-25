package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;

/**
 * Especifica os ids da tabela N para N da AulaPresencaAluno
 * Necessário pois não é uma simples tabela N para N. Tem campos extras na tabela
 *
 */
@Embeddable
public class AulaPresencaAlunoId implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer idAluno;
	private Integer idAula;
	public AulaPresencaAlunoId() {} 
	public AulaPresencaAlunoId(Integer idAluno, Integer idAula) {
		this.idAluno = idAluno;
		this.idAula = idAula;
	}
	@Override
	public int hashCode() {
		return Objects.hash(idAluno, idAula);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AulaPresencaAlunoId other = (AulaPresencaAlunoId) obj;
		return Objects.equals(idAluno, other.idAluno) && Objects.equals(idAula, other.idAula);
	}
	
	
	
}
