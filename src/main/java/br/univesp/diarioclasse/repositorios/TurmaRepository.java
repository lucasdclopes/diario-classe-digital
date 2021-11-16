package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Integer> {
	
}
