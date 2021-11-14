package br.univesp.diarioclassedigital.entidades;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "aulaPresencaAlunos")
public class AulaPresencaAluno {

	private boolean isPresente;
	private boolean hasAtestado;
}
