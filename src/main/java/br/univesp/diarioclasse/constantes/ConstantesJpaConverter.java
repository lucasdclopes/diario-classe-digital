package br.univesp.diarioclasse.constantes;

import javax.persistence.AttributeConverter;

public abstract class ConstantesJpaConverter <T extends Enum<T> & IEnumParseavel> implements AttributeConverter<T, String> {

	 private final Class<T> clazz;
	 
	 public ConstantesJpaConverter(Class<T> clazz) {
	        this.clazz = clazz;    
	 } 
	
	@Override
	public String convertToDatabaseColumn(T attribute) {
       if (attribute == null) {
           return null;
       }
       return attribute.getCodigo();
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return IEnumParseavel.parse(dbData, clazz);
	}
}