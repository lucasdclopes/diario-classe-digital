package br.univesp.diarioclasse.enums;

import java.time.DayOfWeek;

import javax.persistence.Converter;

public enum DiaDaSemana implements IEnumParseavel {
	
	//Não estou usando diretamente a java.time.DayOfWeek para evitar problemas nas tabelas do bd caso a oracle mude alguma coisa na api
	DOMINGO("1"),SEGUNDA("2"),TERCA("3"),QUARTA("4"),QUINTA("5"),SEXTA("6"),SABADO("7");
	
    private String value;

    DiaDaSemana(String value) { this.value = value; }    

	@Override
	public String getCodigo() { return value; }

	@Override
	public String getDescricaoCampo() { return "dia da semana"; }
	
	@Converter(autoApply = true)
    public static class ConverterJpa extends ConstantesJpaConverter<DiaDaSemana> {
        public ConverterJpa() {
            super(DiaDaSemana.class);
        }
    }
	
	public DayOfWeek toDayOfWeek() {
		return switch (this) { // arrow -> dispensa o break
		case DOMINGO -> DayOfWeek.SUNDAY;
		case SEGUNDA -> DayOfWeek.MONDAY;
		case TERCA -> DayOfWeek.TUESDAY;
		case QUARTA -> DayOfWeek.WEDNESDAY;
		case QUINTA ->  DayOfWeek.THURSDAY;
		case SEXTA -> DayOfWeek.FRIDAY;
		case SABADO -> DayOfWeek.SATURDAY;
		default -> throw new IllegalArgumentException("constante inesperada para o DayOfWeek: " + this);//improvável de acontecer, mas trata
		};
	}
}
