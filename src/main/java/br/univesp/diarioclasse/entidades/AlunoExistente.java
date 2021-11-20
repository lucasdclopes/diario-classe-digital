package br.univesp.diarioclasse.entidades;

public interface AlunoExistente {

	boolean existsByCpfOrRaOrNroMatricula(String cpf, String ra, String nroMatricula);
}
