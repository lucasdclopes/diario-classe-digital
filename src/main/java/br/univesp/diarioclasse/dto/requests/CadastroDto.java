package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

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
		@NotBlank @Length(max = 200) 
		private String nomeMae;
		@Length(max = 200) 
		private String nomePai;
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
		public String getNomeMae() {
			return nomeMae;
		}
		public String getNomePai() {
			return nomePai;
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
}
