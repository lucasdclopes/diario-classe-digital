package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

public enum StatusAula implements IEnumParseavel {
	
	AGENDADA("A"),INICIADA("I"),FINALIZADA("F");
	
    private String value;

    StatusAula(String value) { this.value = value; }    

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Status da aula"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<StatusAula> {
        public ConverterJpa() {
            super(StatusAula.class);
        }
    }
}
