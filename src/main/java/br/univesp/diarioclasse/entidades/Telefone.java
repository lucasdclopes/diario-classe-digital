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

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.univesp.diarioclasse.enums.IEnumParseavel;
import br.univesp.diarioclasse.enums.TipoTelefone;

@Entity
@Table(name = "cadastro_telefones")
public class Telefone implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTelefone;
	private String tpTelefone;
	private String ddd;
	private String numeroTelefone;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idCadastro")
	private Cadastro cadastro;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Telefone() {}

	public Telefone(TipoTelefone tpTelefone, String ddd, String numeroTelefone, Cadastro cadastro) {
		this.tpTelefone = tpTelefone.getCodigo();
		this.ddd = ddd;
		this.numeroTelefone = numeroTelefone;
		this.cadastro = cadastro;
		cadastro.adicionarTelefone(this);
	}

	public TipoTelefone getTpTelefone() {
		return IEnumParseavel.parse(this.tpTelefone, TipoTelefone.class);
	}

	public String getDdd() {
		return ddd;
	}

	public String getNumeroTelefone() {
		return numeroTelefone;
	}

	@JsonIgnore
	public Cadastro getCadastro() {
		return cadastro;
	}
}
