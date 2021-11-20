package br.univesp.diarioclasse.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaProfessorDto;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.ProfessorExistente;

public interface ProfessorRepository extends JpaRepository<Professor, Integer>, ProfessorExistente {

	//o :param is null or field = :param é para tornar o parametro opcional. Se ele for null, o filtro é desconsiderado
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaProfessorDto 
			(prof.idCadastro, prof.dtAdmissao, prof.nome,
			mat.descMateria, mat.tpNivelEnsino) 
			FROM Professor prof 
			LEFT JOIN prof.materia mat 
			WHERE 
			 (:cpf is null or prof.cpf = :cpf) AND 
			 (:nome is null or prof.nome LIKE :nome%)
			""")
	Page<ListaProfessorDto> paginar(String cpf, String nome, Pageable paginacao);
	
}
