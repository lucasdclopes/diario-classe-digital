package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Professor;

public interface ProfessorRepositorio extends JpaRepository<Professor, Integer> {
	
}
