package br.univesp.diarioclasse.repositorios;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaAlunosDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.AlunoExistente;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>, AlunoExistente {
	
	@Override
	@Query(value = """  
			SELECT CASE WHEN COUNT(Cad) > 0 THEN true ELSE false END from Cadastro Cad right join Aluno Al
			on Al.idAluno = Cad.idCadastro
			WHERE Al.ra = :ra or Al.nroMatricula = :nroMatricula or Cad.cpf = :cpf
			""")
	boolean existeAlunoCadastrado(String cpf, String ra, String nroMatricula);
	
	List<ListaAlunosDto> findByCadastro_cpfOrRaOrNroMatriculaOrCadastro_NomeStartingWith(String cpf, String ra, String nroMatricula, String nome);
	
	
}
