package br.univesp.diarioclassedigital.constantes;

public class EnumHelper {
	
	public static <E extends Enum<E> & IEnumParseavel> E getEnumDeString(String value, Class<E> clazz) throws ConstanteInvalidaException {
		
		E[] enums = clazz.getEnumConstants();  
		for (E e : enums) {          
			if (e.getCodigo().equals(value)) {        
				return e; 
			}
		}      
		throw new ConstanteInvalidaException("Valor inválido para " + enums[0].getDescricaoCampo() ,value);
	}
}
