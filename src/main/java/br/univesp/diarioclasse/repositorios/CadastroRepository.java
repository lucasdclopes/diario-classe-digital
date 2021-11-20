package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Cadastro;

public interface CadastroRepository extends JpaRepository<Cadastro, Integer> {
	
}
