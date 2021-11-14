package br.univesp.diarioclassedigital.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cadastros")
public class Cadastro {

	@Id @GeneratedValue
	private Integer idCadastro;
	private String nome;
	private String CPF;
	private String RG;
	private LocalDate dtNascimento;
	private String sexo;
	private boolean isAtivo;
}
