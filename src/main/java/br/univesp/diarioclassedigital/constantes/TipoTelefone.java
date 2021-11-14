package br.univesp.diarioclassedigital.constantes;

public enum TipoTelefone implements IEnumParseavel {
	
	FIXO("FI"),CELULAR("CE");
	
    private String value;

    TipoTelefone(String value) { this.value = value; }    
    
    public static TipoTelefone parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, TipoTelefone.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "sexo"; }
}
