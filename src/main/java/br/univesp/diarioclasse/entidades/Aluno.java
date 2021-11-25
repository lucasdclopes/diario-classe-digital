package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.helpers.DateHelper;

@Entity
@Table(name = "cadastro_alunos")
@PrimaryKeyJoinColumn(name="idAluno")
public class Aluno extends Cadastro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(unique = true)
	private String nroMatricula;
	@JsonFormat(pattern=DateHelper.patternDataPtBr) 
	private LocalDate dtMatricula;
	@NotNull @Column(unique = true)
	private String ra;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@JsonIgnore
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
		this.nroMatricula = nroMatricula.strip();
		this.dtMatricula = dtMatricula;
		this.ra = ra.strip();
		turma.ifPresent(t -> this.turma = t);
	}
	
	public void validarSeAlunoJaExiste(AlunoExistente alunoExistente, CadastroExistente cadastroExistente) throws EntidadeJaExisteException {
		super.validarSeJaExiste(cadastroExistente);
		validarSeJaExisteMatricula(alunoExistente);
		validarSeJaExisteRa(alunoExistente);
	}
	
	private void validarSeJaExisteMatricula(AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		if(alunoExistente.existsByNroMatricula(this.nroMatricula))
			throw new EntidadeJaExisteException("Já existe um cadastro com este número de matrícula","nroMatricula");
	}
	
	private void validarSeJaExisteRa(AlunoExistente alunoExistente) throws EntidadeJaExisteException {	
		if(alunoExistente.existsByRa(this.ra))
			throw new EntidadeJaExisteException("Já existe um aluno com este ra","ra");
	}
	
	public void atualizarNroMatricula(String nroMatricula, AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		nroMatricula.strip();
		if (!this.nroMatricula.equalsIgnoreCase(nroMatricula)) {//só é necessário se o nroMatricula realmente mudou. Caso contrário vai dar erro que o nroMatricula já existe (no caso, o nroMatricula do próprio cadastro)
			this.nroMatricula = nroMatricula;
			validarSeJaExisteMatricula(alunoExistente);
		}
	}
	
	public void atualizarRa(String ra, AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		ra = ra.strip();
		if (!this.ra.equalsIgnoreCase(ra)) {//só é necessário se o ra realmente mudou. Caso contrário vai dar erro que o ra já existe (no caso, o ra do próprio cadastro)
			this.ra = ra;
			validarSeJaExisteRa(alunoExistente);
		}
	}
	public void atualizarDtMatricula(LocalDate dtMatricula) {
		this.dtMatricula = dtMatricula;
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
	
	public Turma getTurma() {
		return turma;
	}

	public List<AulaPresencaAluno> getPresencaAlunos() {
		return Collections.unmodifiableList(presencaAlunos);
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(ra);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		return Objects.equals(ra, other.ra);
	}
	
	
	
	
	
}
