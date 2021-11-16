package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.CalendarioAula;

public interface CalendarioAulaRepository extends JpaRepository<CalendarioAula, String> {

}
