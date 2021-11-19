package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Aula;

public interface AulaRepository extends JpaRepository<Aula, Integer> {
	
}
