package br.univesp.diarioclasse.constantes;

public interface IEnumParseavel {

	/**
	 * Codigo da constante, para utilizar em jsons e no bd
	 */
	public String getCodigo();
	/**
	 * Descrição das constante, pode ser usando em mensagens da validação
	 * @return
	 */
	public String getDescricaoCampo(); 
}
