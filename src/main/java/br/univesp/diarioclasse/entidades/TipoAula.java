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

@Entity
@Table(name = "tipos_aula")
public class TipoAula implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTipoAula;
	
	private Integer diaSemana;
	private LocalTime hrInicio;
	private LocalTime hrFim;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idMateria")
	private Materia materia;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idProfessor")
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@OneToMany(mappedBy = "tipoAula")
	private List<Aula> aulas = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public TipoAula() {}
	
}
