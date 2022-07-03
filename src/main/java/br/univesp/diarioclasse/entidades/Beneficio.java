package br.univesp.diarioclasse.entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.univesp.diarioclasse.enums.TipoCadastro;
import br.univesp.diarioclasse.exceptions.AutorizacaoException;
import br.univesp.diarioclasse.exceptions.EstadoObjetoInvalidoExcpetion;
import br.univesp.diarioclasse.seguranca.UsuarioLogado;

@Entity
@Table(name = "alunos_beneficio")
public class Beneficio implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idBeneficio;
	private LocalDate dtRecebimento;
	private String responsavelRecebimento;
	private String descBeneficio;
	private LocalDateTime dtRegistrado;
	
	@ManyToOne(fetch = FetchType.EAGER, optional =  false)
	@JoinColumn(name = "idAluno")
	private Aluno aluno;
	
	/**
	 * Construtor padrão da JPA. Não utilizar.
	 */
	@Deprecated
	public Beneficio() {}

	public Beneficio(LocalDate dtRecebimento, String responsavelRecebimento, String descBeneficio,
			Aluno aluno) {
		this.dtRecebimento = dtRecebimento;
		this.responsavelRecebimento = responsavelRecebimento;
		this.descBeneficio = descBeneficio;
		this.aluno = aluno;
		this.dtRegistrado = LocalDateTime.now();
	}

	public Integer getIdBeneficio() {
		return idBeneficio;
	}

	public LocalDate getDtRecebimento() {
		return dtRecebimento;
	}

	public String getResponsavelRecebimento() {
		return responsavelRecebimento;
	}

	public String getDescBeneficio() {
		return descBeneficio;
	}

	public Aluno getAluno() {
		return aluno;
	}
	
	public LocalDateTime getDtRegistrado() {
		return dtRegistrado;
	}

	public void validarDelecao(UsuarioLogado usuarioLogado) {
		if (usuarioLogado.getTipoCadastro() != TipoCadastro.ADMINISTRATIVO )
			throw new AutorizacaoException("Somente um administrador pode deletar um benefício");
	}

	public void atualizarDtRecebimento(LocalDate dtRecebimento) {
		this.dtRecebimento = dtRecebimento;		
	}

	public void atualizarResponsavelRecebimento(String responsavelRecebimento) {
		this.responsavelRecebimento = responsavelRecebimento;		
	}

	public void atualizarDescBeneficio(String descBeneficio) {
		this.descBeneficio = descBeneficio;	
	}

	public void atualizarAluno(Aluno aluno) throws EstadoObjetoInvalidoExcpetion {
		if (!aluno.equals(this.aluno))
			throw new EstadoObjetoInvalidoExcpetion("Não é possível editar o aluno beneficiado");	
	}

}
