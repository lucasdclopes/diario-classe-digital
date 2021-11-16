package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, Integer> {

}
