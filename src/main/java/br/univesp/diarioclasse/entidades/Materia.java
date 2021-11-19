package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.univesp.diarioclasse.constantes.IEnumParseavel;
import br.univesp.diarioclasse.constantes.TipoNivelEnsino;
import br.univesp.diarioclasse.exceptions.ConstanteInvalidaException;

@Entity
@Table(name = "materias")
public class Materia implements Serializable {
	
	//TODO: 
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idMateria;
	private String descMateria;
	@NotNull @Length(min = 2, max = 2)
	private String tpNivelEnsino;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "materia")
	private List<CalendarioAula> tipoAula;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "materia")
	private List<Professor> professor;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Materia() {}

	public Materia(String descMateria,TipoNivelEnsino tpNivelEnsino) {
		this.descMateria = descMateria;
		this.tpNivelEnsino = tpNivelEnsino.getCodigo();
	}
	public String getDescMateria() {
		return descMateria;
	}
	public TipoNivelEnsino getTpNivelEnsino() {
		return IEnumParseavel.parse(tpNivelEnsino, TipoNivelEnsino.class);
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
