package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

public enum TipoEndereco implements IEnumParseavel {
	
	RESIDENCIAL("R"),COMERCIAL("C");
	
    private String value;

    TipoEndereco(String value) { this.value = value; }    
 
	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Tipo de Endereço"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<TipoEndereco> {
        public ConverterJpa() {
            super(TipoEndereco.class);
        }
    }
}
