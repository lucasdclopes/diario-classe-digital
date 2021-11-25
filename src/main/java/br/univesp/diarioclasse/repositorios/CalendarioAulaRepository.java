package br.univesp.diarioclasse.repositorios;

import java.time.LocalTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaCalendarioAulaDto;
import br.univesp.diarioclasse.entidades.CalendarioAula;
import br.univesp.diarioclasse.entidades.CalendarioAulaExistente;
import br.univesp.diarioclasse.enums.DiaDaSemana;

public interface CalendarioAulaRepository extends JpaRepository<CalendarioAula, Integer>, CalendarioAulaExistente {
	
	
	/**
	 * Verifica se a aula começa ou termina entre os horários de outra aula existente
	 * isto para a mesma turma ou para o mesmo professor
	 */
	@Override
	@Query(value = 
	"""  
		SELECT CASE WHEN COUNT(cal) > 0 THEN true ELSE false END 
		FROM CalendarioAula cal 
		JOIN cal.professor prof
		JOIN cal.turma tur
		WHERE
		cal.hrInicio BETWEEN :hrInicio AND :hrFim 
		AND cal.diaSemana = :diaSemana
		AND (prof.idCadastro = :idProfessor OR tur.idTurma = :idTurma )
	""")
	boolean verificaConflitoHorarios(LocalTime hrInicio, LocalTime hrFim, DiaDaSemana diaSemana, Integer idTurma, Integer idProfessor);
	
	
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaCalendarioAulaDto 
			(cal.idCalendarioAula, cal.diaSemana, cal.hrInicio, cal.hrFim, mat.descMateria, mat.tpNivelEnsino, prof.nome, tur.descTurma) 
			FROM CalendarioAula cal 
			JOIN cal.professor prof
			JOIN cal.turma tur
			JOIN cal.materia mat
			WHERE
			 (:diaSemana is null or cal.diaSemana = :diaSemana) AND 
			 (:idCadastro is null or prof.idCadastro = :idCadastro) AND
			 (:idTurma is null or tur.idTurma = :idTurma) AND
			 (:idMateria is null or mat.idMateria = :idMateria) AND
			 (:hrAula is null or :hrAula BETWEEN cal.hrInicio AND cal.hrFim)
			""")
	Page<ListaCalendarioAulaDto> paginar(DiaDaSemana diaSemana, Integer idTurma, Integer idMateria, Integer idCadastro, LocalTime hrAula, Pageable paginacao);
}
