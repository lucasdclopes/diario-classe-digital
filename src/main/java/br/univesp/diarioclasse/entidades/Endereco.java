package br.univesp.diarioclasse.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.IEnumParseavel;
import br.univesp.diarioclasse.enums.TipoEndereco;

@Entity
@Table(name = "cadastro_enderecos")
public class Endereco implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idEndereco;
	@NotBlank @Length(max = 300)
	private String logradouro;
	private String numeroEndereco;
	private String complementoEndereco;
	@NotBlank @Length(max = 8)
	private String cep;
	private String bairro;
	@NotBlank
	private String cidade;
	@NotBlank @Length(max = 2)
	private String uf;
	@NotNull
	private String tpEndereco;
	
	@ManyToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "idCadastro")
	private Cadastro cadastro;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Endereco() {}

	public Endereco(String logradouro, String numeroEndereco, String complementoEndereco, String cep, String bairro,
			String cidade, String uf, TipoEndereco tpEndereco, Cadastro cadastro) {
		this.logradouro = logradouro;
		this.numeroEndereco = numeroEndereco;
		this.complementoEndereco = complementoEndereco;
		this.cep = cep;
		this.bairro = bairro;
		this.cidade = cidade;
		this.uf = uf;
		this.cadastro = cadastro;
		cadastro.adicionarEndereco(this);
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public String getCep() {
		return cep;
	}

	public String getBairro() {
		return bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public String getUf() {
		return uf;
	}

	public TipoEndereco getTpEndereco() {
		return IEnumParseavel.parse(tpEndereco,TipoEndereco.class);
	}

	@JsonIgnore
	public Cadastro getCadastro() {
		return cadastro;
	}
	
	
}
