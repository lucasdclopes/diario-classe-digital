package br.univesp.diarioclasse.helpers;

import java.util.List;

import org.springframework.stereotype.Component;

import br.univesp.diarioclasse.dto.requests.NovoEnderecoDto;
import br.univesp.diarioclasse.dto.requests.NovoTelefoneDto;
import br.univesp.diarioclasse.entidades.Endereco;
import br.univesp.diarioclasse.entidades.ICadastravel;
import br.univesp.diarioclasse.entidades.Telefone;

@Component
public class CadastroMappers {

	public List<Endereco> novoEnderecoDtoParaEndereco (List<NovoEnderecoDto> enderecos, ICadastravel cadastravel) {
		return enderecos.stream().map(end -> (
				new Endereco(end.logradouro(), end.numeroEndereco(), end.complementoEndereco(), end.cep(), 
						end.bairro(), end.cidade(), end.uf(), end.tpEndereco(), cadastravel))
				)
				.toList();
	}
	
	public List<Telefone> novoTelefoneDtoParaTelefone (List<NovoTelefoneDto> telefones, ICadastravel cadastravel) {		
		return telefones.stream().map(tel -> (		
				new Telefone(tel.tpTelefone(), tel.ddd(), tel.numeroTelefone(), cadastravel)))	
				.toList();
	}
}
