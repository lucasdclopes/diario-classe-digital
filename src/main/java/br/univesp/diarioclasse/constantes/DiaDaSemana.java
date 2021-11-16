package br.univesp.diarioclasse.constantes;

public enum DiaDaSemana implements IEnumParseavel {
	
	DOMINGO("1"),SEGUNDA("2"),TERCA("3"),QUARTA("4"),QUINTA("5"),SEXTA("6"),SABADO("7");
	
    private String value;

    DiaDaSemana(String value) { this.value = value; }    
    
    public static DiaDaSemana parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, DiaDaSemana.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "dia da semana"; }
}
