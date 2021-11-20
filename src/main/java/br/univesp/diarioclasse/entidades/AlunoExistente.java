package br.univesp.diarioclasse.entidades;

public interface AlunoExistente {

	boolean existeAlunoCadastrado(String cpf, String ra, String nroMatricula);
}
