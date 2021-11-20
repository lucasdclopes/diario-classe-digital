package br.univesp.diarioclasse.repositorios;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaAlunosDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.AlunoExistente;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>, AlunoExistente {
	
	/*
	@Query(value = """  
			SELECT CASE WHEN COUNT(Cad) > 0 THEN true ELSE false END from Aluno Al 
			WHERE Al.ra = :ra or Al.nroMatricula = :nroMatricula or Al.cpf = :cpf
			""")
	boolean existeAlunoCadastrado(String cpf, String ra, String nroMatricula);
	*/
	
	@Override
	boolean existsByCpfOrRaOrNroMatricula(String cpf, String ra, String nroMatricula);
	
	Page<ListaAlunosDto> findAllBy(Pageable paginacao);
	List<ListaAlunosDto> findByCpfOrRaOrNroMatricula(String cpf, String ra, String nroMatricula);
	List<ListaAlunosDto> findByCpfOrRaOrNroMatriculaOrNomeStartingWith(String cpf, String ra, String nroMatricula, String nome);
	
	//o :param is null or field = :param é para tornar o parametro opcional. Se ele for null, o filtro é desconsiderado
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaAlunosDto (idCadastro, nroMatricula, dtMatricula, ra, nome) 
			FROM Aluno al 
			WHERE
			 (:cpf is null or al.cpf = :cpf) AND (:ra is null or al.ra = :ra) AND
			 (:nroMatricula is null or al.nroMatricula = :nroMatricula) AND 
			 (:nome is null or al.nome LIKE :nome%)
			""")
	Page<ListaAlunosDto> paginar(String cpf, String ra, String nroMatricula, String nome,Pageable paginacao);
	
	
}
