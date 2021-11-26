package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.helpers.DateHelper;

@Entity
@Table(name = "cadastro_professor")
@PrimaryKeyJoinColumn(name="idProfessor")
public class Professor extends Cadastro implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	@JsonFormat(pattern=DateHelper.patternDataPtBr) 
	private LocalDate dtAdmissao;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idMateria")
	private Materia materia;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "professor")
	private List<Aula> aulas = new ArrayList<>();
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "professor")
	private List<CalendarioAula> calendarioAula = new ArrayList<>();

	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Professor() {}
	public Professor(LocalDate dtAdmissao, Optional<Materia> materia, String nome, String cpf, String rg, LocalDate dtNascimento, Sexo sexo, 
			String nomeMae, String nomePai, String emailContato ) throws DadosInvalidosException {
		super(nome, cpf, rg, dtNascimento, sexo, nomeMae, nomePai, TipoCadastro.PROFESSOR, emailContato);
		this.dtAdmissao = dtAdmissao;
		materia.ifPresent(m -> this.materia = m);
	}

	@Override
	protected void definirDtNascimento(LocalDate dtNascimento) throws DadosInvalidosException {
		if (!dtNascimento.isBefore(LocalDate.now().minus(18,ChronoUnit.YEARS)))
				throw new DadosInvalidosException("O professor precisa ser maior que 18 anos","dtNascimento");
		super.definirDtNascimento(dtNascimento);
		
	}
	
	public void atualizarDtAdmissao(LocalDate dtAdmissao) {
		this.dtAdmissao = dtAdmissao;
	}
	
	@Override
	public void adicionarEndereco(Endereco endereco) {
		super.adicionarEndereco(endereco);	
	}
	@Override
	public void adicionarTelefone(Telefone telefone) {
		super.adicionarTelefone(telefone);
	}
	
	public List<Aula> getAulas() {
		return Collections.unmodifiableList(aulas);
	}
	public List<CalendarioAula> getCalendarioAula() {
		return Collections.unmodifiableList(calendarioAula);
	}
	public Integer getIdProfessor() {
		return super.getIdCadastro();
	}
	public LocalDate getDtAdmissao() {
		return dtAdmissao;
	}
	public Materia getMateria() {
		return materia;
	}
	
}
