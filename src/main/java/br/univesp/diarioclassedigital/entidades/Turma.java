package br.univesp.diarioclassedigital.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "turmas")
public class Turma {

	@Id @GeneratedValue
	private Integer idTurma;
	private String descTurma;
	private String tpPeriodo;
	
}
