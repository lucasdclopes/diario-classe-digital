package br.univesp.diarioclassedigital.constantes;

public enum PeridoEstudo implements IEnumParseavel {

	MATUTINO("MA"),VESPERTINO("VE"),NOTURNO("NO");
	
    private String value;

    PeridoEstudo(String value) { this.value = value; }    
    
    public static PeridoEstudo parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, PeridoEstudo.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "período de estudo"; }
}
