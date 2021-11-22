package br.univesp.diarioclasse.enums;

import java.util.Arrays;

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
		StringBuilder constantes = new StringBuilder().append("");
		Arrays.asList(enums).forEach(s -> constantes.append("["+s+"]") );
		throw new ConstanteInvalidaException(String.format("Valor inválido %s para %s. Os valores possíveis são: %s",value,enums[0].getDescricaoCampo(),constantes));
	}
	
	/**
	 * Parseia pelo nome da constante. Igual ao valueOf, mas joga uma exceção específica do sistema. Para não precisar lidar em cada caso com o IllegalArgument
	 * @throws ConstanteInvalidaException se não existir a constante
	 */
	public static <E extends Enum<E> & IEnumParseavel> E valueOfTratado(String value, Class<E> clazz) throws ConstanteInvalidaException {
		if (value == null)
			return null;
		E[] enums = clazz.getEnumConstants();  
		try {
			return Enum.valueOf(clazz, value);
		} catch (IllegalArgumentException e) {
			StringBuilder constantes = new StringBuilder().append("");
			Arrays.asList(enums).forEach(s -> constantes.append("["+s+"]") );
			throw new ConstanteInvalidaException(String.format("Valor inválido %s para %s. Os valores possíveis são: %s",value,enums[0].getDescricaoCampo(),constantes));
		}
	}
}
