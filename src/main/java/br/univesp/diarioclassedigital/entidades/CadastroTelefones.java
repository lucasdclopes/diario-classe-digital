package br.univesp.diarioclassedigital.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_telefones")
public class CadastroTelefones {

	@Id @GeneratedValue
	private Integer idTelefone;
	private String tpTelefone;
	private String DDD;
	private String Numero;
}
