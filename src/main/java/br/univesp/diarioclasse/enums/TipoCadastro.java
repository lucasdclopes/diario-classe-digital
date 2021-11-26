package br.univesp.diarioclasse.enums;

import javax.persistence.Converter;

public enum TipoCadastro implements IEnumParseavel {
	
	ALUNO("ALU"),PROFESSOR("PRO"),ADMINISTRATIVO("ADM");
	
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
