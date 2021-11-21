package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.entidades.Materia;
import br.univesp.diarioclasse.helpers.DateHelper;

public class ProfessorDto extends CadastroDto {
	
		@JsonFormat(pattern=DateHelper.patternDataPtBr) 
		private LocalDate dtAdmissao;
		private Materia materia;
		public ProfessorDto() {}
		
		public LocalDate getDtAdmissao() {
			return dtAdmissao;
		}
		public Materia getMateria() {
			return materia;
		}	
}
