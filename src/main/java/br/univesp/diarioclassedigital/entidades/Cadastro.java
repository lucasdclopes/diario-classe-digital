package br.univesp.diarioclassedigital.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import br.univesp.diarioclassedigital.constantes.ConstanteInvalidaException;
import br.univesp.diarioclassedigital.constantes.TipoCadastro;

@Entity
@Table(name = "cadastros")
public class Cadastro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCadastro;
	private String nome;
	@NotBlank
	private String cpf;
	private String rg;
	private LocalDate dtNascimento;
	private String sexo;
	private String nomeMae;
	private String nomePai; 
	private String tipoCadastro;
	private boolean isAtivo;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cadastro")
	private List<Enderecos> enderecos = new ArrayList<>();
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "cadastro")
	private List<Telefone> telefones = new ArrayList<>();
	
	public Cadastro() {}
	
	public Cadastro(String nome, String cpf, String rg, LocalDate dtNascimento, String sexo, String nomeMae,
			String nomePai, TipoCadastro tipoCadastro) {
		this.nome = nome;
		this.cpf = cpf;
		this.rg = rg;
		this.dtNascimento = dtNascimento;
		this.sexo = sexo;
		this.nomeMae = nomeMae;
		this.nomePai = nomePai;
		this.tipoCadastro = tipoCadastro.getCodigo();
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
	public String getSexo() {
		return sexo;
	}
	public String getNomeMae() {
		return nomeMae;
	}
	public String getNomePai() {
		return nomePai;
	}
	public TipoCadastro getTipoCadastro() throws ConstanteInvalidaException {
		return TipoCadastro.parse(this.tipoCadastro);
	}
	public boolean isAtivo() {
		return isAtivo;
	}
	public List<Enderecos> getEnderecos() {
		return Collections.unmodifiableList(enderecos);
	}
	public List<Telefone> getTelefones() {
		return Collections.unmodifiableList(telefones);
	}
}
