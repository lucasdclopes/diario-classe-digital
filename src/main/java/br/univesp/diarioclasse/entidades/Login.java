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

import org.hibernate.annotations.NaturalId;

import br.univesp.diarioclasse.exceptions.EntidadeJaExisteException;
import br.univesp.diarioclasse.seguranca.Cifrador;

@Entity
@Table(name =  "cadastro_logins")
public class Login {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idLogin;
	@NotNull @Column(unique = true)
	@NaturalId
	private String emailLogin;
	@NotNull
	private String senha;
	private String tokenAcesso; //token utilizado depois de validar user / senha
	private LocalDateTime dtUltimoAcesso; //se passar de X minutos depois do último acesso, invalida o token
	private LocalDateTime dtCriacaoTokenAtual; //quando o token foi criado. 
	
	@OneToOne(fetch = FetchType.EAGER,optional = false)
	@JoinColumn(name = "idCadastro")
	private Cadastro cadastro;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Login() {}
	
	public Login(String emailLogin, String senha, Cadastro cadastro, Cifrador cifrador) {
		this.emailLogin = emailLogin.strip();
		this.senha = cifrador.hashearSenha(senha.strip());
		this.cadastro = cadastro;
	}
	
	public void validarSeJaExiste(LoginUnico loginUnico) throws EntidadeJaExisteException {
		if(loginUnico.existsBy(this.emailLogin))
			throw new EntidadeJaExisteException("Já existe um login com este email","emailLogin");
	}
	
	/**
	 * Testa se a senha enviada é igual a senha armazenada (sempre hasheado)
	 */
	public boolean isSenhaValida(String senhaTentiva, Cifrador cifrador) {
		return (this.senha.equals(cifrador.hashearSenha(senhaTentiva.strip())));
	}
	
	/**
	 * Gera e guarda um novo token de acesso
	 */
	public void definirTokenAcesso(String token) {
		this.tokenAcesso = token;
		this.dtCriacaoTokenAtual = LocalDateTime.now();
		this.dtUltimoAcesso = LocalDateTime.now();
	}
	
	public void atualizarUltimoAcesso() {
		this.dtUltimoAcesso = LocalDateTime.now();
	}
	
	public Cadastro getCadastro() {
		return cadastro;
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
	public LocalDateTime getDtCriacaoTokenAtual() {
		return dtCriacaoTokenAtual;
	}	
	

}
