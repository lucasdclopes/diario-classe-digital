package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Integer> {

}
