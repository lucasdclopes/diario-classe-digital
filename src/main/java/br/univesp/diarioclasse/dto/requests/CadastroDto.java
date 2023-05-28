package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.entidades.DadosParente;
import br.univesp.diarioclasse.entidades.Endereco;
import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.enums.Sexo;
import br.univesp.diarioclasse.helpers.DateHelper;
import br.univesp.diarioclasse.validadores.CpfNumerico;
import br.univesp.diarioclasse.validadores.SomenteDigitos;

public class CadastroDto {
		@NotBlank @Length(max = 200) 
		private String nome;
		@CpfNumerico 
		private String cpf;
		@Length(max = 10) @SomenteDigitos 
		private String rg;
		@JsonFormat(pattern=DateHelper.patternDataPtBr) 
		private LocalDate dtNascimento;
		private Sexo sexo;
		@Email @NotBlank @Length(max = 200)
		private String emailContato;

		@Valid 
		private Telefone telCelular;
		@Valid 
		private Telefone telFixo;

		@Valid 
		private Endereco endResidencial;
		@Valid 
		private Endereco endComercial;
		
		@Valid //não valida o pai, não é obrigatório
		private DadosParente mae;
		
		private DadosParente pai;
		
		private Boolean isMaeSolo;
		private Boolean isFilhoUnico;
		private Boolean isAbaixoPeso;
		private Boolean recebePensaoAlimenticia;
		private Boolean conviveDoente;
		
		
		public CadastroDto() {}
		public String getNome() {
			return nome;
		}
		public String getCpf() {
			return cpf;
		}
		public String getRg() {
			return rg;
		}
		public LocalDate getDtNascimento() {
			return dtNascimento;
		}
		public Sexo getSexo() {
			return sexo;
		}
		
		public String getEmailContato() {
			return emailContato;
		}
		public Telefone getTelCelular() {
			return telCelular;
		}
		public Telefone getTelFixo() {
			return telFixo;
		}
		public Endereco getEndResidencial() {
			return endResidencial;
		}
		public Endereco getEndComercial() {
			return endComercial;
		}
		public DadosParente getMae() {
			return mae;
		}
		public DadosParente getPai() {
			return pai;
		}
		public Boolean getIsMaeSolo() {
			return isMaeSolo;
		}
		public Boolean getIsFilhoUnico() {
			return isFilhoUnico;
		}
		public Boolean getIsAbaixoPeso() {
			return isAbaixoPeso;
		}
		public Boolean getRecebePensaoAlimenticia() {
			return recebePensaoAlimenticia;
		}
		public Boolean getConviveDoente() {
			return conviveDoente;
		}
		
}
