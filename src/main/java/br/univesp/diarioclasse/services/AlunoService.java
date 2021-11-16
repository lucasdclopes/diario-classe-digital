package br.univesp.diarioclasse.services;

import org.springframework.stereotype.Service;

import br.univesp.diarioclasse.dto.requests.NovoAlunoDto;
import br.univesp.diarioclasse.entidades.Aluno;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.helpers.CadastroMappers;
import br.univesp.diarioclasse.repositorios.AlunoRepository;

@Service
public class AlunoService {

	private AlunoRepository alunoDal;
	private CadastroMappers mappers;

	public AlunoService(AlunoRepository alunoDal, CadastroMappers mappers) {
		this.alunoDal = alunoDal;
		this.mappers = mappers;
	}

	public Integer criarNovoAluno(NovoAlunoDto dto) throws EntidadeJaExisteException {
		
		if (alunoDal.existeAlunoCadastrado(dto.cpf(), dto.ra(), dto.nroMatricula()))
			throw new EntidadeJaExisteException("Já existe um cadastro com estes dados","cpf,ra,nroMatricula");
		
		Aluno aluno = new Aluno(dto.nroMatricula(), dto.dtMatricula(), dto.ra(), dto.turma(), dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		dto.enderecos().ifPresent( lista -> mappers.novoEnderecoDtoParaEndereco(lista, aluno)
				.forEach(cadEnd -> aluno.adicionarEndereco(cadEnd))
		);
		
		dto.telefones().ifPresent(lista ->mappers.novoTelefoneDtoParaTelefone(lista, aluno)
				.forEach(cadTel -> aluno.adicionarTelefone(cadTel))
		);
			

		return alunoDal.save(aluno).getIdAluno();
	}
}
