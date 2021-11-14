package br.univesp.diarioclassedigital.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_enderecos")
public class CadastroEnderecos {

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
	
}
