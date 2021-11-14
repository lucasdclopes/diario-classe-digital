package br.univesp.diarioclasse.constantes;

public enum Sexo implements IEnumParseavel {

	//ISO/IEC 5218
	DESCONHECIDO("0"),MASCULINO("1"),FEMININO("2");
	
    private String value;

    Sexo(String value) { this.value = value; }    
    
    public static Sexo parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, Sexo.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "sexo"; }
}
