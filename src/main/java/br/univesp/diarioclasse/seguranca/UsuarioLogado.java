package br.univesp.diarioclasse.seguranca;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import br.univesp.diarioclasse.entidades.Login;
import br.univesp.diarioclasse.enums.TipoCadastro;

//dados que ficam em memória do usuário logado durante a requisição
@Component
@RequestScope
public class UsuarioLogado {
	private Integer idCadastro;
	private TipoCadastro tipoCadastro;
	public UsuarioLogado () {} //para o spring criar o objeto
	public void carregarUsuarioLogado(Login login) {
		this.idCadastro = login.getCadastro().getIdCadastro();
		this.tipoCadastro = login.getCadastro().getTipoCadastro();
	}
	public Integer getIdCadastro() {
		return idCadastro;
	}
	public TipoCadastro getTipoCadastro() {
		return tipoCadastro;
	}
	
	
	
}
