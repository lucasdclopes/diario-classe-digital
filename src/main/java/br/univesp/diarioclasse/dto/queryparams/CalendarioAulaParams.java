package br.univesp.diarioclasse.dto.queryparams;

public record CalendarioAulaParams(
		String diaSemana,
		Integer idTurma,
		Integer idMateria,
		Integer idProfessor) {

}
