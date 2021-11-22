package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

public enum PeriodoEstudo implements IEnumParseavel {

	MATUTINO("MA"),VESPERTINO("VE"),NOTURNO("NO"),INTEGRAL("IN");
	
    private String value;

    PeriodoEstudo(String value) { this.value = value; }    
    
	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "período de estudo"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<PeriodoEstudo> {
        public ConverterJpa() {
            super(PeriodoEstudo.class);
        }
    }
}
