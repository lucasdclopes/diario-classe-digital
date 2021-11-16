package br.univesp.diarioclasse.services;

import org.springframework.stereotype.Service;

import br.univesp.diarioclasse.dto.requests.NovoProfessorDto;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.helpers.CadastroMappers;
import br.univesp.diarioclasse.repositorios.CadastroRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;

@Service
public class ProfessorService {

	private ProfessorRepository professorDal;
	private CadastroMappers mappers;
	private CadastroRepository cadastroDal;

	public ProfessorService(ProfessorRepository professorDal, CadastroMappers mappers, CadastroRepository cadastroDal) {
		this.professorDal = professorDal;
		this.mappers = mappers;
		this.cadastroDal = cadastroDal;
	
	}
	
	public Integer criarNovoProfessor(NovoProfessorDto dto) throws EntidadeJaExisteException {
		Professor professor = new  Professor(dto.dtAdmissao(), dto.materia(), dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		if (cadastroDal.existsByCpf(dto.cpf()))
			throw new EntidadeJaExisteException("Já existe um cadastro com este cpf","cpf");
		
		dto.enderecos().ifPresent( lista -> mappers.novoEnderecoDtoParaEndereco(lista, professor)
				.forEach(cadEnd -> professor.adicionarEndereco(cadEnd))
		);
		
		dto.telefones().ifPresent(lista ->mappers.novoTelefoneDtoParaTelefone(lista, professor)
				.forEach(cadTel -> professor.adicionarTelefone(cadTel))
		);
			

		return professorDal.save(professor).getIdProfessor();
	}
}
