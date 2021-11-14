package br.univesp.diarioclassedigital.constantes;

public enum TipoEndereco implements IEnumParseavel {
	
	RESIDENCIAL("R"),COMERCIAL("C");
	
    private String value;

    TipoEndereco(String value) { this.value = value; }    
    
    public static TipoEndereco parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, TipoEndereco.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "sexo"; }
}
