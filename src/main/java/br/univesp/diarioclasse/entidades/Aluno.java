package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.AutorizacaoException;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.helpers.DateHelper;
import br.univesp.diarioclasse.seguranca.UsuarioLogado;

@Entity
@Table(name = "cadastro_alunos")
@PrimaryKeyJoinColumn(name="idAluno")
@DiscriminatorValue("ALU")
public class Aluno extends Cadastro implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotNull @Column(unique = true)
	private String nroMatricula;
	@JsonFormat(pattern=DateHelper.patternDataPtBr) 
	private LocalDate dtMatricula;
	@NotNull @Column(unique = true)
	private String NIS;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idTurma")
	private Turma turma;
	
	@JsonIgnore
	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<AulaPresencaAluno> presencaAlunos = new ArrayList<>();
	
	/*
	@JsonIgnore
	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<Beneficio> beneficios = new ArrayList<>();
	*/
	
	@NotNull
	private String transportador;

	@NotNull
	@Embedded 
	 @AttributeOverrides({
		    @AttributeOverride(name="telDDD",column=@Column(name="telDDDTransportador")),
		    @AttributeOverride(name="telNro",column=@Column(name="telNroTransportador"))
	 })	
	private Telefone telTransportador;
	
	@NotNull
	private String unidadeEscolar;
	@NotNull
	private String ubsRef;
	
	@Transient //n??o ?? gravado no banco. Campo calculado
	private Long totalFaltas;
	
	/**
	 * Construtor padr??o da JPA. N??o utilizar.
	 */
	@Deprecated
	public Aluno() {}
	
	
	
	public Aluno(String nroMatricula, LocalDate dtMatricula, String NIS, Optional<Turma> turma, String nome, 
			String cpf, String rg, LocalDate dtNascimento, Sexo sexo, DadosParente mae, DadosParente pai, String emailContato, 
			Endereco endResidencial, Endereco endComercial, Telefone telCelular, Telefone telFixo, String transportador, Telefone telTransportador, String unidadeEscolar, String UBSRef) throws DadosInvalidosException {
		super(nome, cpf, rg, dtNascimento, sexo, mae, pai, TipoCadastro.ALUNO, emailContato, endResidencial, endComercial, telCelular, telFixo);
		this.nroMatricula = nroMatricula.strip();
		this.dtMatricula = dtMatricula;
		this.NIS = NIS.strip();
		this.transportador = transportador;
		this.unidadeEscolar = unidadeEscolar;
		this.ubsRef = UBSRef;
		this.telTransportador = telTransportador;
		turma.ifPresent(t -> this.turma = t);
	}
	
	public void validarSeAlunoJaExiste(AlunoExistente alunoExistente, CadastroExistente cadastroExistente) throws EntidadeJaExisteException {
		super.validarSeJaExiste(cadastroExistente);
		validarSeJaExisteMatricula(alunoExistente);
		validarSeJaExisteRa(alunoExistente);
	}
	
	public void calcularTotalFaltas(CalculadoraFaltasAluno calc) {
		this.totalFaltas = calc.calcularTotalFaltas(this.getIdAluno());
	}
	
	private void validarSeJaExisteMatricula(AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		if(alunoExistente.existsByNroMatricula(this.nroMatricula))
			throw new EntidadeJaExisteException("J?? existe um cadastro com este n??mero de matr??cula","nroMatricula");
	}
	
	private void validarSeJaExisteRa(AlunoExistente alunoExistente) throws EntidadeJaExisteException {	
		if(alunoExistente.existsByNIS(this.NIS))
			throw new EntidadeJaExisteException("J?? existe um aluno com este NIS","NIS");
	}
	
	public void atualizarNroMatricula(String nroMatricula, AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		nroMatricula.strip();
		if (!this.nroMatricula.equalsIgnoreCase(nroMatricula)) {//s?? ?? necess??rio se o nroMatricula realmente mudou. Caso contr??rio vai dar erro que o nroMatricula j?? existe (no caso, o nroMatricula do pr??prio cadastro)
			this.nroMatricula = nroMatricula;
			validarSeJaExisteMatricula(alunoExistente);
		}
	}
	
	public void atualizarNIS(String NIS, AlunoExistente alunoExistente) throws EntidadeJaExisteException {
		NIS = NIS.strip();
		if (!this.NIS.equalsIgnoreCase(NIS)) {//s?? ?? necess??rio se o ra realmente mudou. Caso contr??rio vai dar erro que o ra j?? existe (no caso, o ra do pr??prio cadastro)
			this.NIS = NIS;
			validarSeJaExisteRa(alunoExistente);
		}
	}
	
	public void atualizarTurma(Turma turma) {
		this.turma = turma;
		turma.addAluno(this);
	}
	
	public void checkSeAlunoEmTurma() throws EstadoObjetoInvalidoExcpetion {
		if (this.turma != null) {
			throw new EstadoObjetoInvalidoExcpetion(
					"Este aluno j?? est?? em uma turma. Se voc?? prosseguir ele ser?? removido da turma atual e colocado na nova turma selecionada. Quer mesmo continuar?"
					); //gera a mensagem pro front
		}
	}
	
	
	public void removerTurmaAtual() {
		this.turma = null;
	}
	
	public void atualizarDtMatricula(LocalDate dtMatricula) {
		this.dtMatricula = dtMatricula;
	}

	public void atualizarTransportador(String transportador) {
		this.transportador = transportador;
	}

	public void atualizarTelTransportador(Telefone telTransportador) {
		this.telTransportador = telTransportador;
	}

	public void atualizarUnidadeEscolar(String unidadeEscolar) {
		this.unidadeEscolar = unidadeEscolar;
	}

	public void atualizarUbsRef(String ubsRef) {
		this.ubsRef = ubsRef;
	}
	
	public Integer getIdAluno() {
		return super.getIdCadastro();
	}

	public String getNroMatricula() {
		return nroMatricula;
	}

	public String getNIS() {
		return NIS;
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
	
	public Long getTotalFaltas() {
		return totalFaltas;
	}
	
	public String getTransportador() {
		return transportador;
	}

	public Telefone getTelTransportador() {
		return telTransportador;
	}

	public String getUnidadeEscolar() {
		return unidadeEscolar;
	}

	public String getUbsRef() {
		return ubsRef;
	}
/*
	public List<Beneficio> getBeneficios() {
		return beneficios;
	}
	*/



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(NIS);
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
		return Objects.equals(NIS, other.NIS);
	}



	public void validarDelecao(UsuarioLogado usuarioLogado) {
		if (usuarioLogado.getTipoCadastro() != TipoCadastro.ADMINISTRATIVO )
			throw new AutorizacaoException("Somente um administrador pode deletar um aluno");
	}



	@Override
	public TipoCadastro getTipoCadastro() {
		return TipoCadastro.ALUNO;
	}

		
}
