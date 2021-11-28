package br.univesp.diarioclasse.repositorios;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaTurmasDto;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.entidades.TurmaUnica;
import br.univesp.diarioclasse.enums.PeriodoEstudo;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public interface TurmaRepository extends JpaRepository<Turma, Integer>, TurmaUnica {

	@Override
	boolean existsByDescTurmaAndTpNivelEnsino(String descTurma, TipoNivelEnsino tpNivelEnsino);
	
	@Query("""
			SELECT distinct tu FROM Turma tu
			JOIN FETCH tu.alunos
			WHERE tu.idTurma = :idTurma
			""")
	public Optional<Turma> getTurmaComAlunos(Integer idTurma);

	//o :param is null or field = :param é para tornar o parametro opcional. Se ele for null, o filtro é desconsiderado
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaTurmasDto (idTurma, descTurma, tpPeriodo, tpNivelEnsino) 
			FROM Turma tu
			WHERE
			 (:descTurma is null or tu.descTurma like :descTurma%) AND (:tpPeriodo is null or tu.tpPeriodo = :tpPeriodo) AND
			 (:tpNivelEnsino is null or tu.tpNivelEnsino = :tpNivelEnsino)
			""")
	Page<ListaTurmasDto> paginar(String descTurma, TipoNivelEnsino tpNivelEnsino, PeriodoEstudo tpPeriodo,
			Pageable paginacao);
	
	@Query(value =  """ 
			SELECT count(*) as totalFaltas
			FROM turmas tur
			JOIN cadastro_alunos al ON tur.id_turma = al.id_turma
			JOIN aula_presenca_alunos pres ON al.id_aluno = pres.id_aluno
			WHERE pres.is_presente = 'false'
			""", nativeQuery = true) //nativeQuery, roda SQL nativo direto no sql server, ao invés de utilizar o JPA
	public Long calcularTotalFaltasTurma(Integer idTurma);
	
}
