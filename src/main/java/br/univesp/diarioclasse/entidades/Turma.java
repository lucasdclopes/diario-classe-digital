package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.univesp.diarioclasse.constantes.ConstanteInvalidaException;
import br.univesp.diarioclasse.constantes.PeridoEstudo;

@Entity
@Table(name = "turmas")
public class Turma implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idTurma;
	private String descTurma;
	private String tpPeriodo;
	
	
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
	private List<Aluno> alunos = new ArrayList<>();
	
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
	private List<Aula> aulas = new ArrayList<>();
	
	@OneToMany(mappedBy = "turma", fetch = FetchType.LAZY)
	private List<CalendarioAula> tiposAulas = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Turma() {}

	public Turma(String descTurma, PeridoEstudo tpPeriodo) {
		this.descTurma = descTurma;
		this.tpPeriodo = tpPeriodo.getCodigo();
	}

	public String getDescTurma() {
		return descTurma;
	}

	public PeridoEstudo getTpPeriodo() throws ConstanteInvalidaException {
		return PeridoEstudo.parse(tpPeriodo);
	}	
	
}
