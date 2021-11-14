package br.univesp.diarioclassedigital.constantes;

public enum TipoCadastro implements IEnumParseavel {
	
	ALUNO("A"),PROFESSOR("P");
	
    private String value;

    TipoCadastro(String value) { this.value = value; }    
    
    public static TipoCadastro parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, TipoCadastro.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "sexo"; }
}
