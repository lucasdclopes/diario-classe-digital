package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.DadosInvalidosException;

//Usuário que gerencia tudo. Por enquanto sem nenhum dado especial além dos do cadastro
@Entity
@Table(name = "cadastro_administrador")
@PrimaryKeyJoinColumn(name="idAdminstrador")
@DiscriminatorValue("ADM")
public class Administrador extends Cadastro implements Serializable  {
	
	private static final long serialVersionUID = 1L;

	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Administrador() {}
	public Administrador(String nome, String cpf, String rg, LocalDate dtNascimento, Sexo sexo, 
			DadosParente mae, DadosParente pai, String emailContato, Endereco endResidencial, Endereco endComercial, 
			Telefone telCelular, Telefone telFixo ) throws DadosInvalidosException {
		super(nome, cpf, rg, dtNascimento, sexo, mae, pai, TipoCadastro.ADMINISTRATIVO, emailContato, endResidencial, endComercial, telCelular, telFixo
				, false, false, false, false, false);
	}

	@Override
	protected void definirDtNascimento(LocalDate dtNascimento) throws DadosInvalidosException {
		if (!dtNascimento.isBefore(LocalDate.now().minus(18,ChronoUnit.YEARS)))
				throw new DadosInvalidosException("O administrador precisa ser maior que 18 anos","dtNascimento");
		super.definirDtNascimento(dtNascimento);
		
	}
	@Override
	public TipoCadastro getTipoCadastro() {
		return TipoCadastro.ADMINISTRATIVO;
	}
}
