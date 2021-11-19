package br.univesp.diarioclasse.constantes;

public enum TipoTelefone implements IEnumParseavel {
	
	FIXO("FI"),CELULAR("CE"),CELULAR_RESPONSAVEL("CR");
	
    private String value;

    TipoTelefone(String value) { this.value = value; }    

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Tipo de Telefone"; }
}
