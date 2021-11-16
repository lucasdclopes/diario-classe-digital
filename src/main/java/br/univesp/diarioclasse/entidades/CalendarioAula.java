package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalTime;
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

import br.univesp.diarioclasse.constantes.DiaDaSemana;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;

@Entity
@Table(name = "calendario_aulas")
public class CalendarioAula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCalendarioAula;
	
	@NotNull
	private String diaSemana;
	@NotNull
	private LocalTime hrInicio;
	@NotNull
	private LocalTime hrFim;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "idMateria")
	private Materia materia;

	@ManyToOne(fetch = FetchType.EAGER, optional =  false)
	@JoinColumn(name = "idProfessor")
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.EAGER, optional =  false)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@OneToMany(mappedBy = "calendarioAula")
	private List<Aula> aulas = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public CalendarioAula() {}

	
	public CalendarioAula(DiaDaSemana diaSemana, LocalTime hrInicio, LocalTime hrFim,
			Materia materia, Professor professor, Turma turma) throws DadosInvalidosException {
		
		if (hrFim.isBefore(hrInicio))
			throw new DadosInvalidosException("A aula não pode terminar antes de ter começado", "hrInicio");
		
		if (!professor.getMateria().equals(materia))
			throw new DadosInvalidosException("O professor selecionado, " + professor.getDadosCadastrais().getNome() + ", não leciona " + materia.getDescMateria() 
			, "descMateria");
		this.diaSemana = diaSemana.getCodigo();
		this.hrInicio = hrInicio;
		this.hrFim = hrFim;
		this.materia = materia;
		this.professor = professor;
		this.turma = turma;
	}
	
}
