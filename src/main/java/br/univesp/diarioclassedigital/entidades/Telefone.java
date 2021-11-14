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
@Table(name = "cadastro_telefones")
public class Telefone implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Integer idTelefone;
	private String tpTelefone;
	private String DDD;
	private String Numero;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCadastro")
	private Cadastro cadastro;
}
