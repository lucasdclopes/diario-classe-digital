package br.univesp.diarioclasse.repositorios;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaAlunosDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.entidades.AlunoExistente;
import br.univesp.diarioclasse.entidades.CadastroExistente;

public interface AlunoRepository extends JpaRepository<Aluno, Integer>, AlunoExistente, CadastroExistente {
	
	/*
	@Query(value = """  
			SELECT CASE WHEN COUNT(Cad) > 0 THEN true ELSE false END from Aluno Al 
			WHERE Al.ra = :ra or Al.nroMatricula = :nroMatricula or Al.cpf = :cpf
			""")
	boolean existeAlunoCadastrado(String cpf, String ra, String nroMatricula);
	*/
	
	
	//Não deixa o spring fazer automático, se não ele vai dar join na tabela de alunos. Com isto ele não vai encontrar outro tipo de cadastro com o mesmo CPF
	//Uma outra solução seria um right join de aluno com o cadastro
	@Override
	@Query(value = 
	"""  
		SELECT CASE WHEN COUNT(cad) > 0 THEN true ELSE false END from Cadastro cad 
		WHERE cad.cpf = :cpf
	""")
	boolean existsByCpf(String cpf);
	
	@Override
	boolean existsByRa(String ra);
	@Override
	boolean existsByNroMatricula(String nroMatricula);
	
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
	
	@Query(value =  """ 
			SELECT  count(*) as totalFaltas
			FROM cadastro_alunos al
			JOIN aula_presenca_alunos pres ON al.id_aluno = pres.id_aluno
			WHERE al.id_aluno = :idAluno 
			AND pres.is_presente = 'false'
			""", nativeQuery = true) //nativeQuery, roda SQL nativo direto no sql server, ao invés de utilizar o JPA
	public Long calcularTotalFaltas(Integer idAluno);
	
	
}
