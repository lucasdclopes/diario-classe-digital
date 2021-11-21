package br.univesp.diarioclasse.enums;

public enum StatusAula implements IEnumParseavel {
	
	AGENDADA("A"),INICIADA("I"),FINALIZADA("F");
	
    private String value;

    StatusAula(String value) { this.value = value; }    

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Status da aula"; }
}
