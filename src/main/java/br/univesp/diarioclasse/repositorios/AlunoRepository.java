package br.univesp.diarioclasse.repositorios;

import java.util.List;

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
	
	List<ListaAlunosDto> findByCpfOrRaOrNroMatricula(String cpf, String ra, String nroMatricula);
	List<ListaAlunosDto> findByCpfOrRaOrNroMatriculaOrNomeStartingWith(String cpf, String ra, String nroMatricula, String nome);
	
	
}
