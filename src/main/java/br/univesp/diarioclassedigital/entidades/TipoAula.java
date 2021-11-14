package br.univesp.diarioclassedigital.entidades;

import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipos_aula")
public class TipoAula {

	@Id @GeneratedValue
	private Integer idAula;
	
	private Integer diaSemana;
	private LocalTime hrInicio;
	private LocalTime hrFim;
}
