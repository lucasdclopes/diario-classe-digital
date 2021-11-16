package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aulas")
public class Aula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAula;
	private LocalDate dtAula;
	private LocalDateTime dtHrIniciada;
	private LocalDateTime dtHrFinalizada;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTipoAula")
	private TipoAula tipoAula;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProfessor")
	private Professor professor;
	
	@OneToMany(mappedBy = "aula", fetch = FetchType.LAZY)
	private Set<AulaPresencaAluno> presencaAlunos = new HashSet<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Aula() {}

}
