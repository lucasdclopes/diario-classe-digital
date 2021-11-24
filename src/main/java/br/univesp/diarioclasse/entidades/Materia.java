package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.enums.TipoNivelEnsino;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;

@Entity
@Table(name = "materias")
public class Materia implements Serializable {
	
	//TODO: 
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMateria;
	@NotNull @NotBlank @Length(max = 20)
	private String descMateria;
	@NotNull
	private TipoNivelEnsino tpNivelEnsino;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "materia")
	private List<CalendarioAula> calendarioAula = new ArrayList<>();
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "materia")
	private List<Professor> professores = new ArrayList<>();
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Materia() {}

	public Materia(String descMateria,TipoNivelEnsino tpNivelEnsino) {
		this.descMateria = descMateria;
		this.tpNivelEnsino = tpNivelEnsino;
	}
	public String getDescMateria() {
		return descMateria;
	}
	public TipoNivelEnsino getTpNivelEnsino() {
		return tpNivelEnsino;
	}
	
	public void validar (MateriaUnica materiaUnica) throws EntidadeJaExisteException {
		if (materiaUnica.existsByDescMateriaAndTpNivelEnsino(this.descMateria,this.tpNivelEnsino))
			throw new EntidadeJaExisteException("Já existe outra matéria com este nome no " + this.tpNivelEnsino.getDescricaoAmigavel() , "descTurma");
	}
	
	public void atualizarDescMateria(String descMateria, MateriaUnica materiaUnica) throws EntidadeJaExisteException {
		if (!this.descMateria.equalsIgnoreCase(descMateria)) {
			this.descMateria = descMateria;
			validar(materiaUnica);
		} 
	}
	public void atualizarTpNivelEnsino(TipoNivelEnsino tpNivelEnsino) throws EstadoObjetoInvalidoExcpetion {
		if (this.tpNivelEnsino != tpNivelEnsino)
			throw new EstadoObjetoInvalidoExcpetion("Não é possível alterar o nível de ensino da matéria");
	}
	
	

	public Integer getIdMateria() {
		return idMateria;
	}

	public List<CalendarioAula> getCalendarioAula() {
		return Collections.unmodifiableList(calendarioAula);
	}

	public List<Professor> getProfessores() {
		return Collections.unmodifiableList(professores);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idMateria);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Materia other = (Materia) obj;
		return Objects.equals(idMateria, other.idMateria);
	}
}
