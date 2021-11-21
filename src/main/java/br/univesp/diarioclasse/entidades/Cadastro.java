package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.exceptions.RelacaoEntidadesIlegalException;

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
	private String rg;
	@NotNull
	private LocalDate dtNascimento;
	@NotNull
	private Sexo sexo;
	private String nomeMae;
	private String nomePai; 
	@NotNull
	private TipoCadastro tipoCadastro;
	private Boolean isAtivo;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cadastro", cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Endereco> enderecos = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cadastro", cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
	private List<Telefone> telefones = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Cadastro() {}
	
	public Cadastro(String nome, String cpf, String rg, LocalDate dtNascimento, Sexo sexo, String nomeMae,
			String nomePai, TipoCadastro tipoCadastro) throws DadosInvalidosException {
		atualizarNome(nome);
		this.cpf = cpf.strip();
		atualizarRg(rg);
		definirDtNascimento(dtNascimento);
		atualizarSexo(sexo);
		atualizarNomeMae(nomeMae);
		atualizarNomePai(nomePai);
		this.tipoCadastro = tipoCadastro;
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
		this.nomePai = nomePai.strip();
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
	
	public void adicionarEndereco(Endereco endereco) {
		if (!endereco.getCadastro().equals(this))
			throw new RelacaoEntidadesIlegalException("Não é possível adicionar um endereço com referencia de cadastro vazia ou diferente deste cadastro.");
		this.enderecos.add(endereco);
	}
	
	public void adicionarTelefone(Telefone telefone) {
		if (!telefone.getCadastro().equals(this))
			throw new RelacaoEntidadesIlegalException("Não é possível adicionar um telefone com referencia de cadastro vazia ou diferente deste cadastro.");
		this.telefones.add(telefone);
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
	public List<Endereco> getEnderecos() {
		return Collections.unmodifiableList(enderecos);
	}
	public List<Telefone> getTelefones() {
		return Collections.unmodifiableList(telefones);
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
