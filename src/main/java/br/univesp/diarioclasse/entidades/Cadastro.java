package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
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
@DiscriminatorColumn(name = "tipoCadastro")
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
	//@NotNull
	//private TipoCadastro tipoCadastro;
	private Boolean isAtivo;
	@NotNull
	private String emailContato;
	
	@NotNull private Boolean isMaeSolo;
	@NotNull private Boolean isFilhoUnico;
	@NotNull private Boolean isAbaixoPeso;
	@NotNull private Boolean recebePensaoAlimenticia;
	@NotNull private Boolean conviveDoente;
	
	@NotNull
	 @Embedded 
	 @AttributeOverrides({
		 @AttributeOverride(name="telContato.telDDD",column=@Column(name="telMaeDDD")),
		 @AttributeOverride(name="telContato.telNro",column=@Column(name="telMaeNro")),
		 @AttributeOverride(name="nome",column=@Column(name="nomeMae")),
		 @AttributeOverride(name="cpf",column=@Column(name="cpfMae")),
		 @AttributeOverride(name="isGestante",column=@Column(name="isGestanteMae"))
	 })
	private DadosParente mae;
	 
	 @Embedded 
	 @AttributeOverrides({
		    @AttributeOverride(name="telContato.telDDD",column=@Column(name="telpaiDDD")),
		    @AttributeOverride(name="telContato.telNro",column=@Column(name="telpaiNro")),
			@AttributeOverride(name="nome",column=@Column(name="nomePai")),
			@AttributeOverride(name="cpf",column=@Column(name="cpfPai")),
			@AttributeOverride(name="isGestante",column=@Column(name="isGestantePai"))
	 })
	private DadosParente pai;
	
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
	
	public Cadastro(String nome, String cpf, String rg, LocalDate dtNascimento, Sexo sexo, DadosParente mae,
			DadosParente pai, TipoCadastro tipoCadastro, String emailContato, Endereco endResidencial, Endereco endComercial, 
			Telefone telCelular, Telefone telFixo, Boolean isMaeSolo, Boolean isFilhoUnico, Boolean isAbaixoPeso,
			Boolean recebePensaoAlimenticia, Boolean conviveDoente) throws DadosInvalidosException {
		atualizarNome(nome);
		this.cpf = cpf.strip();
		atualizarRg(rg);
		definirDtNascimento(dtNascimento);
		atualizarSexo(sexo);
		this.isMaeSolo = isMaeSolo;
		atualizarMae(mae);
		if (!this.isMaeSolo)
			atualizarPai(pai);
		atualizarEmailContato(emailContato);
		//this.tipoCadastro = tipoCadastro;
		this.endResidencial = endResidencial;
		this.endComercial = endComercial;
		this.telCelular = telCelular;
		this.telFixo = telFixo;
		this.isAtivo = true;
		this.isFilhoUnico = isFilhoUnico;
		this.isAbaixoPeso = isAbaixoPeso;
		this.recebePensaoAlimenticia = recebePensaoAlimenticia;
		this.conviveDoente = conviveDoente;
		
	} 
	
	protected void definirDtNascimento(LocalDate dtNascimento) throws DadosInvalidosException{
		if (dtNascimento.isAfter(LocalDate.now()))
			throw new DadosInvalidosException("A data de nascimento deve ser anterior a data atual", "dtNascimento");
		this.dtNascimento = dtNascimento;
	}
	
	protected void validarNome(String nome, String descricao) throws DadosInvalidosException {
		if (nome == null || nome.length() < 3 || !nome.contains(" "))
			throw new DadosInvalidosException("Por favor preencha o nome completo", descricao);
	}
	
	public void atualizarNome(String nome) throws DadosInvalidosException {
		validarNome(nome,"nome");
		this.nome = nome.strip();
	}
	public void atualizarMae(DadosParente mae) throws DadosInvalidosException {
		validarNome(mae.getNome(),"nome da mãe");
		this.mae = mae;
	}
	public void atualizarPai(DadosParente pai) throws DadosInvalidosException {
		if (this.isMaeSolo) {
			this.pai = new DadosParente(null, null, null, null);
			return;
		}
		if (pai == null || pai.getNome() == null || pai.getNome().strip().equals(""))//nome do pai nunca é obrigatório
			return;
		validarNome(pai.getNome(),"nome do pai");
		this.pai = pai;
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
	
	public void alterarTelefoneFixo(Telefone telefone) {
		this.telFixo = telefone;
	}
	
	public void alterarTelefoneCelular(Telefone telefone) {
		this.telCelular = telefone;
	}
	
	public void alterarIsMaeSolo(Boolean isMaeSolo) {
	    this.isMaeSolo = isMaeSolo;
	} 
	public void alterarIsFilhoUnico(Boolean isFilhoUnico) {
	    this.isFilhoUnico = isFilhoUnico;
	} 
	public void alterarIsAbaixoPeso(Boolean isAbaixoPeso) {
	    this.isAbaixoPeso = isAbaixoPeso;
	} 
	public void alterarRecebePensaoAlimenticia(Boolean recebePensaoAlimenticia) {
	    this.recebePensaoAlimenticia = recebePensaoAlimenticia;
	} 
	public void alterarConviveDoente(Boolean conviveDoente) {
	    this.conviveDoente = conviveDoente;
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

	//public TipoCadastro getTipoCadastro() {
		//return tipoCadastro;
	//}
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
	
	public DadosParente getMae() {
		return mae;
	}

	public DadosParente getPai() {
		return pai;
	}

	public Boolean getConviveDoente() {
		return conviveDoente;
	}

	public void setConviveDoente(Boolean conviveDoente) {
		this.conviveDoente = conviveDoente;
	}

	public Boolean getIsMaeSolo() {
		DadosParente pai = this.getPai();
		return isMaeSolo || pai == null || 
				(pai != null && pai.getNome() == null && pai.getCpf() == null && pai.getTelContato() == null);
	}

	public Boolean getIsFilhoUnico() {
		return isFilhoUnico;
	}

	public Boolean getIsAbaixoPeso() {
		return isAbaixoPeso;
	}

	public Boolean getRecebePensaoAlimenticia() {
		return recebePensaoAlimenticia;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cpf);
	}
	
	public abstract TipoCadastro getTipoCadastro();

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
