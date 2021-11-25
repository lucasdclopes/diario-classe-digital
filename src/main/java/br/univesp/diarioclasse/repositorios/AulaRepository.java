package br.univesp.diarioclasse.repositorios;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.entidades.Aula;
import br.univesp.diarioclasse.enums.StatusAula;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
	
	
	@Query(""" 
			SELECT DISTINCT aula
			FROM Aula aula
			JOIN aula.professor prof
			JOIN aula.turma tur
			JOIN aula.materia mat
			WHERE
			(:dtAula is null or aula.dtAula = :dtAula) AND
			(:hrAula is null or :hrAula BETWEEN aula.dtHrIniciada AND aula.dtHrFinalizada) AND
			(:statusAula is null or aula.statusAula = :statusAula) AND
			(:idProfessor is null or prof.idCadastro = :idProfessor) AND
			(:idTurma is null or tur.idTurma = :idTurma) AND
			(:idMateria is null or mat.idMateria = :idMateria)
			""")
	Page<Aula> paginar(LocalDate dtAula, LocalDateTime hrAula, StatusAula statusAula, Integer idTurma, Integer idMateria, Integer idProfessor, Pageable paginacao);
}
