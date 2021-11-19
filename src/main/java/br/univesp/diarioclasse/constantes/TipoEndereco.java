package br.univesp.diarioclasse.constantes;

public enum TipoEndereco implements IEnumParseavel {
	
	RESIDENCIAL("R"),COMERCIAL("C");
	
    private String value;

    TipoEndereco(String value) { this.value = value; }    
 
	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Tipo de Endereço"; }
}
