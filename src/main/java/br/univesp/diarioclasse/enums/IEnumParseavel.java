package br.univesp.diarioclasse.enums;

import br.univesp.diarioclasse.exceptions.ConstanteInvalidaException;

public interface IEnumParseavel {

	/**
	 * Codigo da constante, para utilizar em jsons e no bd
	 */
	String getCodigo();
	/**
	 * Descrição das constante, pode ser usando em mensagens da validação
	 * @return
	 */
	String getDescricaoCampo(); 
	
	/**
	 * Carrega um enum utilizando uma string que representa o seu código interno
	 * @throws ConstanteInvalidaException se nenhuma constante é representada pela string enviada
	 */
	public static <E extends Enum<E> & IEnumParseavel> E parse(String value, Class<E> clazz) throws ConstanteInvalidaException {
		if (value == null)
			return null;
		E[] enums = clazz.getEnumConstants();  
		for (E e : enums) {          
			if (e.getCodigo().equals(value)) {        
				return e; 
			}
		}      
		throw new ConstanteInvalidaException("Valor inválido para " + enums[0].getDescricaoCampo() ,value);
	}
}
