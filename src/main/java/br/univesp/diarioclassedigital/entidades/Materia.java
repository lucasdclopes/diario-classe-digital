package br.univesp.diarioclassedigital.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materias")
public class Materia {

	@Id @GeneratedValue
	private Integer idMateria;
	private String descMateria;
}
