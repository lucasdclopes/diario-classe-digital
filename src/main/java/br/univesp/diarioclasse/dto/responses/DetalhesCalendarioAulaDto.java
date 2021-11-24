package br.univesp.diarioclasse.dto.responses;

import java.time.LocalTime;

import br.univesp.diarioclasse.enums.DiaDaSemana;

public record DetalhesCalendarioAulaDto(
		Integer idCalendarioAula,
		DiaDaSemana diaSemana,
		LocalTime hrInicio,
		LocalTime hrFim,
		ListaMateriasDto materia,
		CadastroDadosBasicosDto professor,
		ListaTurmasDto turma
		) {}

