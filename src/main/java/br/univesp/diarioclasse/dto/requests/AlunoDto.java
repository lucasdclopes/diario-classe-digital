package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.entidades.Telefone;
import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.helpers.DateHelper;
import br.univesp.diarioclasse.validadores.SomenteDigitos;

public class AlunoDto extends CadastroDto {
		@NotBlank @Length(max = 50) 
		private String nroMatricula;
		@JsonFormat(pattern=DateHelper.patternDataPtBr) 
		private LocalDate dtMatricula;
		@NotBlank @Length(max = 11) @SomenteDigitos 
		private String nis;
		@NotBlank
		private String transportador;
		@NotNull @Valid
		private Telefone telTransportador;
		@NotBlank
		private String unidadeEscolar;
		@NotBlank
		private String ubsRef;
			
		private Turma turma;
		
		public AlunoDto() {}
		
		public String getNroMatricula() {
			return nroMatricula;
		}
		public LocalDate getDtMatricula() {
			return dtMatricula;
		}
		public String getNIS() {
			return nis;
		}
		public Turma getTurma() {
			return turma;
		}
		public String getTransportador() {
			return transportador;
		}
		public Telefone getTelTransportador() {
			return telTransportador;
		}
		public String getUnidadeEscolar() {
			return unidadeEscolar;
		}
		public String getUbsRef() {
			return ubsRef;
		}
		
}
