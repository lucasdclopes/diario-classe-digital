package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Table;

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
		super();
		this.idAluno = idAluno;
		this.idAula = idAula;
	}
	
}
