package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoNivelEnsino implements IEnumParseavel {
	
	INFANTIL("IN","Infantil"),
	FUNDAMENTAL_I("F1","Fundamental I"),
	FUNDAMENTAL_II("F2","Fundamental II"),
	MEDIO("ME","Médio");
	
	private String value;
	private String valueAmigavel;

	TipoNivelEnsino(String value, String valueAmigavel) { this.value = value;
	this.valueAmigavel = valueAmigavel; }

	@Override
	public String getCodigo() { return this.value; }

	@Override
	public String getDescricaoCampo() { return "Nível de ensino"; } 	
	
	@JsonValue
	public String getDescricaoAmigavel() { return valueAmigavel; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<TipoNivelEnsino> {
        public ConverterJpa() {
            super(TipoNivelEnsino.class);
        }
    }

}
