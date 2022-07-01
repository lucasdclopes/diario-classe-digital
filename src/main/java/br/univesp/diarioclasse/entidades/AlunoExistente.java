package br.univesp.diarioclasse.entidades;

//Ao invés de enviar o repositório inteiro para quem verifica a unicidade, envia só a interface, para que as implemetações possíveis sejam restritas
public interface AlunoExistente {

	boolean existsByNIS(String NIS);
	boolean existsByNroMatricula(String nroMatricula);
}
