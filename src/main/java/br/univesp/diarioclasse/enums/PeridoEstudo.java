package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

public enum PeridoEstudo implements IEnumParseavel {

	MATUTINO("MA"),VESPERTINO("VE"),NOTURNO("NO"),INTEGRAL("IN");
	
    private String value;

    PeridoEstudo(String value) { this.value = value; }    
    
	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "período de estudo"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<PeridoEstudo> {
        public ConverterJpa() {
            super(PeridoEstudo.class);
        }
    }
}
