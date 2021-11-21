package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

public enum Sexo implements IEnumParseavel {

	//ISO/IEC 5218
	DESCONHECIDO("0"),MASCULINO("1"),FEMININO("2");
	
    private String value;

    Sexo(String value) { this.value = value; }    

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "sexo"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<Sexo> {
        public ConverterJpa() {
            super(Sexo.class);
        }
    }
}
