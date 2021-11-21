package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.entidades.Turma;
import br.univesp.diarioclasse.helpers.DateHelper;

public class AlunoDto extends CadastroDto {
		@NotBlank @Length(max = 50) 
		private String nroMatricula;
		@JsonFormat(pattern=DateHelper.patternDataPtBr) 
		private LocalDate dtMatricula;
		@NotBlank @Length(max = 50) 
		private String ra;
		private Turma turma;
		public AlunoDto() {}
		
		public String getNroMatricula() {
			return nroMatricula;
		}
		public LocalDate getDtMatricula() {
			return dtMatricula;
		}
		public String getRa() {
			return ra;
		}
		public Turma getTurma() {
			return turma;
		}	
		
}
