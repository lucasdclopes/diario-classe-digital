package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.constantes.TipoCadastro;

@Entity
@Table(name = "cadastro_alunos")
public class Aluno implements Serializable, ICadastravel {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer idAluno;
	
	@NotNull @Column(unique = true)
	private String nroMatricula;
	private LocalDate dtMatricula;
	@NotNull @Column(unique = true)
	private String ra;
	
	@OneToOne(fetch = FetchType.EAGER,optional = false,cascade = CascadeType.ALL)
	@MapsId
	@JoinColumn(name = "idAluno")
	private Cadastro cadastro;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<AulaPresencaAluno> presencaAlunos = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Aluno() {}
	
	
	
	public Aluno(String nroMatricula, LocalDate dtMatricula, String ra, Optional<Turma> turma, String nome, 
			String cpf, String rg, LocalDate dtNascimento, Sexo sexo, String nomeMae, String nomePai ) {
		this.nroMatricula = nroMatricula;
		this.dtMatricula = dtMatricula;
		this.ra = ra;
		turma.ifPresent(t -> this.turma = t);
		this.cadastro = new Cadastro(nome, cpf, rg, dtNascimento, sexo, nomeMae, nomePai, TipoCadastro.ALUNO);
	}
	

	@Override
	public Cadastro getDadosCadastrais() {
		return this.cadastro;
	}
	
	@Override
	public void adicionarEndereco(Endereco endereco) {
		this.cadastro.adicionarEndereco(endereco);	
	}
	
	@Override
	public void adicionarTelefone(Telefone telefone) {
		this.cadastro.adicionarTelefone(telefone);
	}

	public Integer getIdAluno() {
		return idAluno;
	}

	public String getNroMatricula() {
		return nroMatricula;
	}

	public String getRa() {
		return ra;
	}

	public LocalDate getDtMatricula() {
		return dtMatricula;
	}
	
}
