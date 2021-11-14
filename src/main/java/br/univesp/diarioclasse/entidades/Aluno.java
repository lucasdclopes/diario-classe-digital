package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_alunos")
public class Aluno implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idAluno;
	
	private String nroMatricula;
	private LocalDate dtMatricula;
	private String ra;
	
	@OneToOne(fetch = FetchType.EAGER)
	@MapsId(value = "idAluno")
	@JoinColumn(name = "idAluno")
	private Cadastro cadastro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private Set<AulaPresencaAluno> presencaAlunos = new HashSet<>();

}
