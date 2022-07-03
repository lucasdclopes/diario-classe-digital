package br.univesp.diarioclasse.repositorios;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.dto.responses.ListaBeneficiosDto;
import br.univesp.diarioclasse.entidades.Beneficio;

public interface BeneficioRepository extends JpaRepository<Beneficio, Integer> {
	
	//o :param is null or field = :param é para tornar o parametro opcional. Se ele for null, o filtro é desconsiderado
	
	@Query(""" 
			SELECT new br.univesp.diarioclasse.dto.responses.ListaBeneficiosDto (
				bene.idBeneficio, al.idCadastro, al.nome, al.NIS, bene.dtRecebimento, bene.responsavelRecebimento 
			 ) 
			FROM Beneficio bene 
			JOIN bene.aluno al 
			WHERE 
			 (:idAluno is null or al.idCadastro = :idAluno) AND
			 (:dtRecebimentoInicio is null or bene.dtRecebimento > :dtRecebimentoInicio) AND
			 (:dtRecebimentoFim is null or bene.dtRecebimento < :dtRecebimentoFim) AND
			 (:responsavelRecebimento is null or bene.responsavelRecebimento LIKE :responsavelRecebimento%)
			""")
			
	Page<ListaBeneficiosDto> paginar(Integer idAluno, LocalDate dtRecebimentoInicio, LocalDate dtRecebimentoFim, String responsavelRecebimento, Pageable paginacao);
	
}
