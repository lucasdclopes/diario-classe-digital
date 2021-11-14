package br.univesp.diarioclasse.constantes;

public enum TipoTelefoneAluno implements IEnumParseavel {
	
	FIXO("FI"),CELULAR("CE"),CELULAR_RESPONSAVEL("CR");
	
    private String value;

    TipoTelefoneAluno(String value) { this.value = value; }    
    
    public static TipoTelefoneAluno parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, TipoTelefoneAluno.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "sexo"; }
}
