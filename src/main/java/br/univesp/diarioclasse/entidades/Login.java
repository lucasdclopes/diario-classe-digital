package br.univesp.diarioclasse.entidades;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;

@Entity
@Table(name =  "cadastro_logins")
public class Login {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLogin;
	@NotNull @Column(unique = true)
	private String emailLogin;
	@NotNull
	private String senha;
	private String tokenAcesso;
	private LocalDateTime dtUltimoAcesso;
	
	@OneToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "idCadastro")
	private Cadastro cadastro;
	
	public Login(String emailLogin, String senha, Cadastro cadastro) {
		this.emailLogin = emailLogin;
		this.senha = senha;
		this.cadastro = cadastro;
	}
	
	public void validarSeJaExiste(LoginUnico loginUnico) throws EntidadeJaExisteException {
		if(loginUnico.existsBy(this.emailLogin))
			throw new EntidadeJaExisteException("Já existe um login com este email","emailLogin");
	}
	
	
	public Integer getIdLogin() {
		return idLogin;
	}
	public String getEmailLogin() {
		return emailLogin;
	}
	public String getSenha() {
		return senha;
	}
	public String getTokenAcesso() {
		return tokenAcesso;
	}
	public LocalDateTime getDtUltimoAcesso() {
		return dtUltimoAcesso;
	}	

}
