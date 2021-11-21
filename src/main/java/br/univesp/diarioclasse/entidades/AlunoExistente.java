package br.univesp.diarioclasse.entidades;

public interface AlunoExistente {

	boolean existsByRa(String ra);
	boolean existsByNroMatricula(String nroMatricula);
}
