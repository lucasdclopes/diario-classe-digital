package br.univesp.diarioclassedigital.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclassedigital.entidades.Professor;

public interface ProfessorRepositorio extends JpaRepository<Professor, Integer> {

}
