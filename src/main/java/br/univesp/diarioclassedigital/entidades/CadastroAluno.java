package br.univesp.diarioclassedigital.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "alunos")
public class CadastroAluno {
	
	@Id @GeneratedValue
	private Integer idAluno;
	
	private String nroMatricula;
	private LocalDate dtMatricula;
	private String RA;
	private String nomeMae;
	private String nomePai;

}
