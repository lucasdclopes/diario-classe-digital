package br.univesp.diarioclasse.entidades;

import java.time.LocalTime;

import br.univesp.diarioclasse.enums.DiaDaSemana;

//Ao invés de enviar o repositório inteiro para quem verifica a unicidade, envia só a interface, para que as implemetações possíveis sejam restritas
public interface CalendarioAulaExistente {

	boolean verificaConflitoHorarios(LocalTime hrInicio, LocalTime hrFim, DiaDaSemana diaSemana, Integer idTurma, Integer idProfessor);
}
