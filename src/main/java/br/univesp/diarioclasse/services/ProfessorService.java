package br.univesp.diarioclasse.services;

import org.springframework.stereotype.Service;

import br.univesp.diarioclasse.dto.requests.NovoProfessorDto;
import br.univesp.diarioclasse.entidades.Endereco;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.repositorios.EnderecoRepository;
import br.univesp.diarioclasse.repositorios.ProfessorRepository;
import br.univesp.diarioclasse.repositorios.TelefoneRepository;

@Service
public class ProfessorService {

	private ProfessorRepository professorDal;
	private EnderecoRepository enderecoDal;
	private TelefoneRepository telefoneDal;

	public ProfessorService(ProfessorRepository professorDal,EnderecoRepository enderecoDal, TelefoneRepository telefoneDal) {
		this.professorDal = professorDal;
		this.enderecoDal = enderecoDal;
		this.telefoneDal = telefoneDal;
	
	}
	
	public Integer criarNovoProfessor(NovoProfessorDto dto) throws EntidadeJaExisteException {
		Professor professor = new  Professor(dto.dtAdmissao(), null, dto.nome(), dto.cpf(), dto.rg(), 
				dto.dtNascimento(), dto.sexo(), dto.nomeMae(), dto.nomePai());
		
		if (professorDal.existsByCadastroCpf(dto.cpf()))
			throw new EntidadeJaExisteException("Já existe um cadastro com este cpf","cpf");
		
		if (dto.enderecos() != null)
			dto.enderecos().stream().map(end -> (new Endereco(end.logradouro(), end.numeroEndereco(), end.complementoEndereco(), end.cep(), 
					end.bairro(), end.cidade(), end.uf(), end.tpEndereco(), professor))
					)
			.toList().forEach(cadEnd -> professor.adicionarEndereco(cadEnd));
		
		if (dto.telefones() != null)
			dto.telefones().stream().map(tel -> (new Telefone(tel.tpTelefone(), tel.ddd(), tel.numeroTelefone(), professor)))
			.toList().forEach(cadTel -> professor.adicionarTelefone(cadTel));
			

		return professorDal.save(professor).getIdProfessor();
	}
}
