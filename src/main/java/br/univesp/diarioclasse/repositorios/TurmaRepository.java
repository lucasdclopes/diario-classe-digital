package br.univesp.diarioclasse.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaTurmasDto;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.entidades.TurmaUnica;
import br.univesp.diarioclasse.enums.PeridoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public interface TurmaRepository extends JpaRepository<Turma, Integer>, TurmaUnica {

	@Override
	boolean existsByDescTurmaAndTpNivelEnsino(String descTurma, TipoNivelEnsino tpNivelEnsino);

	//o :param is null or field = :param é para tornar o parametro opcional. Se ele for null, o filtro é desconsiderado
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaTurmasDto (idTurma, descTurma, tpPeriodo, tpNivelEnsino) 
			FROM Turma tu
			WHERE
			 (:descTurma is null or tu.descTurma = :descTurma) AND (:tpPeriodo is null or tu.tpPeriodo = :tpPeriodo) AND
			 (:tpNivelEnsino is null or tu.tpNivelEnsino = :tpNivelEnsino)
			""")
	Page<ListaTurmasDto> paginar(String descTurma, TipoNivelEnsino tpNivelEnsino, PeridoEstudo tpPeriodo,
			Pageable paginacao);
	
}
