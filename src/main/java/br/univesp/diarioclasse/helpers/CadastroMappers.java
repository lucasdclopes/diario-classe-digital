package br.univesp.diarioclasse.helpers;

import java.util.List;

import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.dto.requests.CadastroDto;
import br.univesp.diarioclasse.dto.requests.EnderecoDto;
import br.univesp.diarioclasse.dto.requests.TelefoneDto;
import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.CadastroExistente;
import br.univesp.diarioclasse.entidades.Endereco;
import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;
import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@Component
public class CadastroMappers {

	public List<Endereco> novoEnderecoDtoParaEndereco (List<EnderecoDto> enderecos, Cadastro cadastro) {
		return enderecos.stream().map(end -> (
				new Endereco(end.logradouro(), end.numeroEndereco(), end.complementoEndereco(), end.cep(), 
						end.bairro(), end.cidade(), end.uf(), end.tpEndereco(), cadastro))
				)
				.toList();
	}
	
	public List<Telefone> novoTelefoneDtoParaTelefone (List<TelefoneDto> telefones, Cadastro cadastro) {		
		return telefones.stream().map(tel -> (		
				new Telefone(tel.tpTelefone(), tel.ddd(), tel.numeroTelefone(), cadastro)))	
				.toList();
	}
	
	public void atualizarCadastroDeDto(CadastroDto cadastroDto, Cadastro cadastro, CadastroExistente cadastroExistente) throws EntidadeJaExisteException, DadosInvalidosException {
		if (cadastroDto.getNome()!= null)
			cadastro.atualizarNome(cadastroDto.getNome());
		if (cadastroDto.getNomeMae()!= null)
			cadastro.atualizarNomeMae(cadastroDto.getNomeMae());
		if (cadastroDto.getNomePai()!= null)
			cadastro.atualizarNomePai(cadastroDto.getNomePai());
		if (cadastroDto.getCpf()!= null)
			cadastro.atualizarCpf(cadastroDto.getCpf(),cadastroExistente);
		if (cadastroDto.getRg()!= null)
			cadastro.atualizarRg(cadastroDto.getRg());
		if (cadastroDto.getDtNascimento()!= null)
			cadastro.atualizarDtNascimento(cadastroDto.getDtNascimento());
		if (cadastroDto.getSexo()!= null)
			cadastro.atualizarSexo(cadastroDto.getSexo());
		
	}
}
