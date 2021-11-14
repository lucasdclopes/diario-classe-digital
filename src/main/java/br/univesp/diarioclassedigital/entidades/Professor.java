package br.univesp.diarioclassedigital.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "cadastro_professor")
public class Professor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Integer idProfessor;
	
	@OneToOne(fetch = FetchType.EAGER)
	@MapsId(value = "idProfessor")
	@JoinColumn(name = "idProfessor")
	private Cadastro cadastro;
	
	private LocalDate dtAdmissao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idMateria")
	private Materia materia;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "professor")
	private List<Aula> aulas = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "professor")
	private List<TipoAula> tipoAula = new ArrayList<>();
}
