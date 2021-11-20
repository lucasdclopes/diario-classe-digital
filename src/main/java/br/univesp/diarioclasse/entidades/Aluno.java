package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.constantes.Sexo;
import br.univesp.diarioclasse.constantes.TipoCadastro;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@Entity
@Table(name = "cadastro_alunos")
@PrimaryKeyJoinColumn(name="idAluno")
public class Aluno extends Cadastro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(unique = true)
	private String nroMatricula;
	private LocalDate dtMatricula;
	@NotNull @Column(unique = true)
	private String ra;
	
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
			String cpf, String rg, LocalDate dtNascimento, Sexo sexo, String nomeMae, String nomePai ) throws DadosInvalidosException {
		super(nome, cpf, rg, dtNascimento, sexo, nomeMae, nomePai, TipoCadastro.ALUNO);
		this.nroMatricula = nroMatricula;
		this.dtMatricula = dtMatricula;
		this.ra = ra;
		turma.ifPresent(t -> this.turma = t);
	}
	
	public void validarSeJaExiste(AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		if(alunoExistente.existsByCpfOrRaOrNroMatricula(super.getCpf(), this.ra, this.nroMatricula))
			throw new EntidadeJaExisteException("Já existe um cadastro com estes dados","cpf,ra,nroMatricula");
	}

	@Override
	public void adicionarEndereco(Endereco endereco) {
		super.adicionarEndereco(endereco);	
	}
	
	@Override
	public void adicionarTelefone(Telefone telefone) {
		super.adicionarTelefone(telefone);
	}

	public Integer getIdAluno() {
		return super.getIdCadastro();
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
