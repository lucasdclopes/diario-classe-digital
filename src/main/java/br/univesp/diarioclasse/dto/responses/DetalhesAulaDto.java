package br.univesp.diarioclasse.dto.responses;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.entidades.AulaPresencaAluno;
import br.univesp.diarioclasse.enums.StatusAula;
import br.univesp.diarioclasse.helpers.DateHelper;

public record DetalhesAulaDto(
		Integer idAula,
		@JsonFormat(pattern=DateHelper.patternDataPtBr) LocalDate dtAula,
		@JsonFormat(pattern=DateHelper.patternDataHoraPtBr) LocalDateTime dtHrIniciada,
		@JsonFormat(pattern=DateHelper.patternDataHoraPtBr) LocalDateTime dtHrFinalizada,
		StatusAula statusAula,
		ListaMateriasDto materia,
		CadastroDadosBasicosDto professor,
		ListaTurmasDto turma,
		List<PresencaAlunoAulaDto> presencaAlunos
		) {
	
		public static class PresencaAlunoAulaDto {
			private CadastroDadosBasicosDto aluno;
			private boolean isPresente;
			private boolean hasAtestado;
			
			public PresencaAlunoAulaDto(AulaPresencaAluno presencaAluno) {
				this.aluno = new CadastroDadosBasicosDto(
						presencaAluno.getAluno().getIdAluno(), presencaAluno.getAluno().getNome());
				this.isPresente = presencaAluno.isPresente();
				this.hasAtestado = presencaAluno.hasAtestado();
			}

			public CadastroDadosBasicosDto getAluno() {	return aluno; }
			public boolean isPresente() { return isPresente; }
			public boolean isHasAtestado() { return hasAtestado; }
				
		}
	
}

