package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.Cadastro;
import br.univesp.diarioclasse.entidades.CadastroExistente;

public interface CadastroRepository extends JpaRepository<Cadastro, Integer>, CadastroExistente {
	
}
