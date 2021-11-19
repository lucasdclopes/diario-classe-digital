package br.univesp.diarioclasse.constantes;

public enum TipoNivelEnsino implements IEnumParseavel {
	
	INFANTIL("IN"),FUNDAMENTAL_I("F1"),FUNDAMENTAL_II("F2"),MEDIO("ME");
	
	private String value;

	TipoNivelEnsino(String value) { this.value = value; }

	@Override
	public String getCodigo() { return this.value; }

	@Override
	public String getDescricaoCampo() { return "Nível de ensino"; } 	

}
