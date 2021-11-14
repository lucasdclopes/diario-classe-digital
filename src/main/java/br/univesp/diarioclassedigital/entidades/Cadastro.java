package br.univesp.diarioclassedigital.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "cadastros")
public class Cadastro implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue
	private Integer idCadastro;
	private String nome;
	private String CPF;
	private String RG;
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

}
