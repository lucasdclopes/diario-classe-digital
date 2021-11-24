package br.univesp.diarioclasse.dto.responses;

import java.util.List;

import br.univesp.diarioclasse.dto.responses.DetalhesMateriaDto.ProfessorMateria;
import br.univesp.diarioclasse.entidades.Professor;
import br.univesp.diarioclasse.enums.TipoNivelEnsino;

public record DetalhesMateriaDto(
		Integer idMateria,
		String descMateria,
		TipoNivelEnsino tpNivelEnsino,
		List<ProfessorMateria> professores) {
	public static class ProfessorMateria {
		private Integer idCadastro; private String nome;
		public ProfessorMateria(Professor professor) {
			this.idCadastro = professor.getIdCadastro();
			this.nome = professor.getNome();
		}
		public Integer getIdCadastro() {
			return idCadastro;
		}
		public String getNome() {
			return nome;
		}	
	}
}

