package br.univesp.diarioclasse.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaMateriasDto;
import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.entidades.MateriaUnica;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public interface MateriaRepository extends JpaRepository<Materia, Integer>, MateriaUnica {

	@Override
	boolean existsByDescMateriaAndTpNivelEnsino(String descMateria, TipoNivelEnsino tpNivelEnsino);

	//o :param is null or field = :param é para tornar o parametro opcional. Se ele for null, o filtro é desconsiderado
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaMateriasDto (idMateria, descMateria, tpNivelEnsino) 
			FROM Materia ma
			WHERE
			 (:descMateria is null or ma.descMateria like :descMateria%) AND 
			 (:tpNivelEnsino is null or ma.tpNivelEnsino = :tpNivelEnsino)
			""")
	Page<ListaMateriasDto> paginar(String descMateria, TipoNivelEnsino tpNivelEnsino, Pageable paginacao);
}
