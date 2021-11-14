package br.univesp.diarioclassedigital.entidades;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cadastro_professor")
public class CadastroProfessor {

	private LocalDate dtAdmissao;
}
