package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.helpers.DateHelper;

@Entity
@Table(name = "cadastros")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cadastro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCadastro;
	
	@NotNull @NotBlank
	private String nome;
	
	@NotNull @NotBlank @Column(unique = true) 
	@NaturalId(mutable = true)
	private String cpf;
	@NotBlank
	private String rg;
	@NotNull @JsonFormat(pattern=DateHelper.patternDataPtBr) 
	private LocalDate dtNascimento;
	@NotNull
	private Sexo sexo;
	@NotBlank
	private String nomeMae;
	private String nomePai; 
	@NotNull
	private TipoCadastro tipoCadastro;
	private Boolean isAtivo;
	@NotNull
	private String emailContato;
	
	 @Embedded 
	 @AttributeOverrides({
		    @AttributeOverride(name="telDDD",column=@Column(name="celDDD")),
		    @AttributeOverride(name="telNro",column=@Column(name="celNro"))
	 })	
	 private Telefone telCelular;
	 
	 @Embedded 
	 @AttributeOverrides({
		    @AttributeOverride(name="telDDD",column=@Column(name="telFixoDDD")),
		    @AttributeOverride(name="telNro",column=@Column(name="telFixoNro"))
	 })
	 private Telefone telFixo;
	
	 @Embedded 
	 @AttributeOverrides({
		 @AttributeOverride(name="endLogradouro",column=@Column(name="endResLogradouro")),
		 @AttributeOverride(name="endNumero",column=@Column(name="endResNumero")),
		 @AttributeOverride(name="endComplemento",column=@Column(name="endResComplemento")),
		 @AttributeOverride(name="endCEP",column=@Column(name="endResCEP")),
		 @AttributeOverride(name="endBairro",column=@Column(name="endResBairro")),
		 @AttributeOverride(name="endCidade",column=@Column(name="endResCidade")),
		 @AttributeOverride(name="endUF",column=@Column(name="endResUF")),
	 })
	 private Endereco endResidencial;
	 
	 @Embedded 
	 @AttributeOverrides({
		 @AttributeOverride(name="endLogradouro",column=@Column(name="endComLogradouro")),
		 @AttributeOverride(name="endNumero",column=@Column(name="endComNumero")),
		 @AttributeOverride(name="endComplemento",column=@Column(name="endComComplemento")),
		 @AttributeOverride(name="endCEP",column=@Column(name="endComCEP")),
		 @AttributeOverride(name="endBairro",column=@Column(name="endComBairro")),
		 @AttributeOverride(name="endCidade",column=@Column(name="endComCidade")),
		 @AttributeOverride(name="endUF",column=@Column(name="endComUF")),
	 })
	 private Endereco endComercial;
	
	@OneToOne(mappedBy = "cadastro")
	private Login login;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Cadastro() {}
	
	public Cadastro(String nome, String cpf, String rg, LocalDate dtNascimento, Sexo sexo, String nomeMae,
			String nomePai, TipoCadastro tipoCadastro, String emailContato, Endereco endResidencial, Endereco endComercial, 
			Telefone telCelular, Telefone telFixo) throws DadosInvalidosException {
		atualizarNome(nome);
		this.cpf = cpf.strip();
		atualizarRg(rg);
		definirDtNascimento(dtNascimento);
		atualizarSexo(sexo);
		atualizarNomeMae(nomeMae);
		atualizarNomePai(nomePai);
		atualizarEmailContato(emailContato);
		this.tipoCadastro = tipoCadastro;
		this.endResidencial = endResidencial;
		this.endComercial = endComercial;
		this.telCelular = telCelular;
		this.telFixo = telFixo;
		this.isAtivo = true;
		
	} 
	
	protected void definirDtNascimento(LocalDate dtNascimento) throws DadosInvalidosException{
		if (dtNascimento.isAfter(LocalDate.now()))
			throw new DadosInvalidosException("A data de nascimento deve ser anterior a data atual", "dtNascimento");
		this.dtNascimento = dtNascimento;
	}
	
	protected void validarNome(String nome) throws DadosInvalidosException {
		if (nome.length() < 3 || !nome.contains(" "))
			throw new DadosInvalidosException("Por favor preencha o nome completo", "nome");
	}
	
	public void atualizarNome(String nome) throws DadosInvalidosException {
		validarNome(nome);
		this.nome = nome.strip();
	}
	public void atualizarNomeMae(String nomeMae) throws DadosInvalidosException {
		validarNome(nome);
		this.nomeMae = nomeMae.strip();
	}
	public void atualizarNomePai(String nomePai) throws DadosInvalidosException {
		validarNome(nome);
		this.nomePai = nomePai != null? nomePai.strip():null; //não é obrigatório
	}
	public void atualizarCpf(String cpf, CadastroExistente cadastroExistente) throws EntidadeJaExisteException {
		if (!this.cpf.equalsIgnoreCase(cpf)) {//só é necessário se o cpf realmente mudou. Caso contrário vai dar erro que o cpf já existe (no caso, o cpf do próprio cadastro)
			this.cpf = cpf.strip();
			validarSeJaExiste(cadastroExistente);
		}
	}
	public void atualizarRg(String rg) {
		this.rg = rg.strip();
	}
	public void atualizarDtNascimento(LocalDate dtNascimento) throws DadosInvalidosException {
		definirDtNascimento(dtNascimento);
	}
	public void validarSeJaExiste(CadastroExistente cadastroExistente) throws EntidadeJaExisteException {
		if(cadastroExistente.existsByCpf(this.getCpf()))
			throw new EntidadeJaExisteException("Já existe um cadastro com este CPF","cpf");
	}
	public void atualizarSexo(Sexo sexo) {
		this.sexo = sexo;
	}
	public void atualizarEmailContato(String emailContato) {
		this.emailContato = emailContato.strip();
	}
	public void darBaixa() throws EstadoObjetoInvalidoExcpetion {
		if (this.isAtivo())
			this.isAtivo = false;
		else
			throw new EstadoObjetoInvalidoExcpetion("Não é possível dar baixa em um cadastro já inativo");
	}
	public void reativarCadastro() throws EstadoObjetoInvalidoExcpetion {
		if (this.isAtivo())
			throw new EstadoObjetoInvalidoExcpetion("Não é possível ativar um cadastro que já está ativo");
		else
			this.isAtivo = true;
	}
	
	public void alterarEnderecoResidencial(Endereco endereco) {
		this.endResidencial = endereco;
	}
	
	public void alterarEnderecoComercial(Endereco endereco) {
		this.endComercial = endereco;
	}
	
	public void alteararTelefoneFixo(Telefone telefone) {
		this.telFixo = telefone;
	}
	
	public void alteararTelefoneCelular(Telefone telefone) {
		this.telCelular = telefone;
	}

	public Integer getIdCadastro() {
		return idCadastro;
	}
	public String getNome() {
		return nome;
	}
	public String getCpf() {
		return cpf;
	}
	public String getRg() {
		return rg;
	}
	public LocalDate getDtNascimento() {
		return dtNascimento;
	}
	public Sexo getSexo() {
		return sexo;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public String getNomePai() {
		return nomePai;	
	}
	public TipoCadastro getTipoCadastro() {
		return tipoCadastro;
	}
	public boolean isAtivo() {
		return isAtivo;
	}
	public String getEmailContato() {
		return emailContato;
	}
	
	public Telefone getTelCelular() {
		return telCelular;
	}

	public Telefone getTelFixo() {
		return telFixo;
	}

	public Endereco getEndResidencial() {
		return endResidencial;
	}

	public Endereco getEndComercial() {
		return endComercial;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cadastro other = (Cadastro) obj;
		return Objects.equals(cpf, other.cpf);
	}

}
