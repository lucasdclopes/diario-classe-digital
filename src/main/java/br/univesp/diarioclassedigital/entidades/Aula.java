package br.univesp.diarioclassedigital.entidades;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aulas")
public class Aula {
	
	@Id @GeneratedValue
	private Integer idAula;
	private LocalDate dtAula;
	private LocalDateTime dtHrIniciada;
	private LocalDateTime dtHrFinalizada;

}
