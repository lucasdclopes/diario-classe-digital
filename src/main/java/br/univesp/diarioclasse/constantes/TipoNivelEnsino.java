package br.univesp.diarioclasse.constantes;

import javax.persistence.Converter;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoNivelEnsino implements IEnumParseavel {
	
	INFANTIL("IN","Ensino infantil"),
	FUNDAMENTAL_I("F1","Ensino fundamental I"),
	FUNDAMENTAL_II("F2","Ensino fundamental II"),
	MEDIO("ME","Ensino Médio");
	
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
