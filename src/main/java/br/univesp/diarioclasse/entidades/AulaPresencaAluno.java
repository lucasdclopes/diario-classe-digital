package br.univesp.diarioclasse.entidades;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "aulaPresencaAlunos")
public class AulaPresencaAluno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AulaPresencaAlunoId id = new AulaPresencaAlunoId();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idAula")
	@JoinColumn(name = "idAula")
	private Aula aula; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("idAluno")
	@JoinColumn(name = "idAluno")
	private Aluno aluno; 

	private boolean isPresente;
	private boolean hasAtestado;
}
