package br.univesp.diarioclassedigital.entidades;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_enderecos")
public class Enderecos implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Integer idEndereco;
	private String logradouro;
	private String numeroEndereco;
	private String complementoEndereco;
	private String cep;
	private String bairro;
	private String cidade;
	private String uf;
	private String tpEndereco;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCadastro")
	private Cadastro cadastro;
	
}
