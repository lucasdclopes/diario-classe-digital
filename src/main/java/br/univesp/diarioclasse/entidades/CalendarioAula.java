package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.enums.DiaDaSemana;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;

@Entity
@Table(name = "calendario_aulas")
public class CalendarioAula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCalendarioAula;
	
	@NotNull
	private DiaDaSemana diaSemana;
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
	
	/*
	@OneToMany(mappedBy = "calendarioAula")
	private List<Aula> aulas = new ArrayList<>();
	*/
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public CalendarioAula() {}

	
	public CalendarioAula(DiaDaSemana diaSemana, LocalTime hrInicio, LocalTime hrFim,
			Materia materia, Professor professor, Turma turma) throws DadosInvalidosException {
		
		//Valida se este objeto pode ser de fato constrúido com os dados informados
		if (hrFim.isBefore(hrInicio))
			throw new DadosInvalidosException("A aula não pode terminar antes de ter começado", "hrInicio");
		
		if (!professor.getMateria().equals(materia))
			throw new DadosInvalidosException(String.format("O professor selecionado, %s, não leciona %s para o %s "
					, professor.getNome(),materia.getDescMateria(),materia.getTpNivelEnsino().getDescricaoAmigavel())
					, "descMateria,TpNivelEnsino");
		
		if (!professor.getMateria().equals(materia) || professor.getMateria().getTpNivelEnsino() != turma.getTpNivelEnsino())
			throw new DadosInvalidosException(String.format("A turma selecionada é do %s, porém o professor selecionado leciona para o %s "
					, turma.getTpNivelEnsino().getDescricaoAmigavel(),professor.getMateria().getTpNivelEnsino().getDescricaoAmigavel())
					, "turma");
			
		this.diaSemana = diaSemana;
		this.hrInicio = hrInicio;
		this.hrFim = hrFim;
		this.materia = materia;
		this.professor = professor;
		this.turma = turma;
	}

	/**
	 * Valida se os dados são consistantes
	 * @param calendarioDao interface com o método de validação de horários
	 */
	public void validar(CalendarioAulaExistente calendarioDao) throws DadosInvalidosException{
		if (calendarioDao.verificaConflitoHorarios(hrInicio, hrFim, diaSemana, turma.getIdTurma(), professor.getIdCadastro()))
			throw new DadosInvalidosException(
					"Esta aula conflita com outra aula no calendário. Verifique se os horários batem ou se o professor não está já registrado em outra aula neste horário ", 
					"hrInicio,hrFim");
	}
	

	public Integer getIdCalendarioAula() {
		return idCalendarioAula;
	}

	public LocalTime getHrInicio() {
		return hrInicio;
	}

	public LocalTime getHrFim() {
		return hrFim;
	}

	/*
	public List<Aula> getAulas() {
		return Collections.unmodifiableList(aulas);
	}
	*/

	public Materia getMateria() {
		return materia;
	}

	public Professor getProfessor() {
		return professor;
	}

	public Turma getTurma() {
		return turma;
	}
	
	public DiaDaSemana getDiaSemana() {
		return diaSemana;
	}
	
}
