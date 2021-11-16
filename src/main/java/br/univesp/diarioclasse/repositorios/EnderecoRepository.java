package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {

}
