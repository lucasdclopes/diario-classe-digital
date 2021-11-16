package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.entidades.Aluno;

public interface TurmaRepository extends JpaRepository<Aluno, Integer> {
	
	@Query(value = """  
			SELECT CASE WHEN COUNT(Cad) > 0 THEN true ELSE false END from Cadastro Cad right join Aluno Al
			on Al.idAluno = Cad.idCadastro
			WHERE Al.ra = :ra or Al.nroMatricula = :nroMatricula or Cad.cpf = :cpf
			""")
	boolean existeAlunoCadastrado(String cpf, String ra, String nroMatricula);
	
}
