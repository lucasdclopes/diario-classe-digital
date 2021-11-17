package br.univesp.diarioclasse.constantes;

public enum StatusAula implements IEnumParseavel {
	
	AGENDADA("A"),INICIADA("I"),FINALIZADA("F");
	
    private String value;

    StatusAula(String value) { this.value = value; }    
    
    public static StatusAula parse(String id) throws ConstanteInvalidaException {
    	return EnumHelper.getEnumDeString(id, StatusAula.class);
    }

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Status da aula"; }
}
