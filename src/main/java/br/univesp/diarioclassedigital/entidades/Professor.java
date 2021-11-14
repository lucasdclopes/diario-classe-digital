package br.univesp.diarioclassedigital.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.univesp.diarioclassedigital.constantes.TipoCadastro;

@Entity
@Table(name = "cadastro_professor")
public class Professor implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Integer idProfessor;
	
	@OneToOne(fetch = FetchType.EAGER, optional=false)
	@MapsId
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

	public Professor(LocalDate dtAdmissao, Materia materia, String nome, String cpf, String rg, LocalDate dtNascimento, String sexo, 
			String nomeMae, String nomePai ) {
		this.dtAdmissao = dtAdmissao;
		this.materia = materia;
		this.cadastro = new Cadastro(nome, cpf, rg, dtNascimento, sexo, nomeMae, nomePai, TipoCadastro.PROFESSOR);
		//this.cadastro.setProfessor(this);
	}

	public Integer getIdProfessor() {
		return idProfessor;
	}
	
	

}
