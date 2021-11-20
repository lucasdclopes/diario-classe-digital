package br.univesp.diarioclasse.constantes;

import javax.persistence.Converter;

public enum TipoCadastro implements IEnumParseavel {
	
	ALUNO("A"),PROFESSOR("P");
	
    private String value;

    TipoCadastro(String value) { this.value = value; }    

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "Tipo de Cadastro"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<TipoCadastro> {
        public ConverterJpa() {
            super(TipoCadastro.class);
        }
    }
}
