package br.univesp.diarioclasse.seguranca;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.univesp.diarioclasse.enums.TipoCadastro;

//dados que ficam em memória do usuário logado durante a requisição
//é muito simples e sem regra, por isso deixa os atributos abertos
@Component
@RequestScope
public class UsuarioLogado {
	public Integer idCadastro;
	public TipoCadastro tipoCadastro;
}
