package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.constantes.StatusAula;

@Entity
@Table(name = "aulas")
public class Aula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idAula;
	@NotNull
	private LocalDate dtAula;
	private LocalDateTime dtHrIniciada;
	private LocalDateTime dtHrFinalizada;
	@NotNull @Length(min = 2, max = 2)
	private String statusAula;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idCalendarioAula")
	private CalendarioAula calendarioAula;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@ManyToOne(fetch = FetchType.EAGER, optional =  false)
	@JoinColumn(name = "idProfessor")
	private Professor professor;
	
	@OneToMany(mappedBy = "aula", fetch = FetchType.LAZY)
	private List<AulaPresencaAluno> presencaAlunos = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Aula() {}

	private Aula(LocalDate dtAula,StatusAula statusAula, CalendarioAula calendarioAula, Turma turma, Professor professor) {
		super();
		this.dtAula = dtAula;
		this.statusAula = statusAula.getCodigo();
		this.calendarioAula = calendarioAula;
		this.turma = turma;
		this.professor = professor;
	}
	
	public static Aula agendarAulaDoCalendario(LocalDate dtAula, CalendarioAula calendarioAula) {
		return new Aula(dtAula, StatusAula.AGENDADA, calendarioAula, calendarioAula.getTurma(), calendarioAula.getProfessor());
	}

}
