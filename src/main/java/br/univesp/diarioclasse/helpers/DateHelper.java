package br.univesp.diarioclasse.helpers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateHelper {

	public final static String patternDataPtBr = "dd/MM/yyyy"; 
	public final static String patternDataHoraPtBr = "dd/MM/yyyy HH:mm:ss"; 
	
	private final static DateTimeFormatter formatadorPtBr = DateTimeFormatter.ofPattern(patternDataPtBr);
	public static String paraFormatoBr(LocalDate dataParaConverter) {
		return dataParaConverter.format(formatadorPtBr);
	}
	public static String paraFormatoBr(LocalDateTime dataParaConverter) {
		return dataParaConverter.format(formatadorPtBr);
	}
}
