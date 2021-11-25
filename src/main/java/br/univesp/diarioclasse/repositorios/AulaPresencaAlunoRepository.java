package br.univesp.diarioclasse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import br.univesp.diarioclasse.entidades.AulaPresencaAluno;

public interface AulaPresencaAlunoRepository extends JpaRepository<AulaPresencaAluno, String> {

}
