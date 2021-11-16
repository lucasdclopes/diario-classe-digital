package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.constantes.TipoCadastro;

@Entity
@Table(name = "cadastro_professor")
public class Professor implements Serializable, ICadastravel {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	private Integer idProfessor;
	
	@OneToOne(fetch = FetchType.EAGER, optional=false,cascade = CascadeType.ALL)
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
	private List<CalendarioAula> tipoAula = new ArrayList<>();

	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Professor() {}
	public Professor(LocalDate dtAdmissao, Optional<Materia> materia, String nome, String cpf, String rg, LocalDate dtNascimento, Sexo sexo, 
			String nomeMae, String nomePai ) {
		this.dtAdmissao = dtAdmissao;
		materia.ifPresent(m -> this.materia = m);
		this.cadastro = new Cadastro(nome, cpf, rg, dtNascimento, sexo, nomeMae, nomePai, TipoCadastro.PROFESSOR);
		//this.cadastro.setProfessor(this);
	}
	
	@Override
	public Cadastro getDadosCadastrais() {
		return cadastro;
	}
	@Override
	public void adicionarEndereco(Endereco endereco) {
		this.cadastro.adicionarEndereco(endereco);
		
	}
	@Override
	public void adicionarTelefone(Telefone telefone) {
		this.cadastro.adicionarTelefone(telefone);
	}

	public Integer getIdProfessor() {
		return idProfessor;
	}
	public LocalDate getDtAdmissao() {
		return dtAdmissao;
	}
	public Materia getMateria() {
		return materia;
	}
	
	
}
