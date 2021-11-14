package br.univesp.diarioclasse.validadores;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorCpfNumerico implements ConstraintValidator<CpfNumerico, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.equals("00000000000") || value.equals("11111111111") || value.equals("22222222222") || value.equals("33333333333") ||
        		value.equals("44444444444") || value.equals("55555555555") || value.equals("66666666666") || value.equals("77777777777") ||
        		value.equals("88888888888") || value.equals("99999999999") || (value.length() != 11))
		return false;
        
        Integer calculo1oDigito = 0;
        Integer calculo2oDigito = 0;
        char[] arrCpf = value.toCharArray();
        Integer indexCpf = 0;
      
    	try {
	        for (int i = 10; i > 1; i--) {	
	        	calculo1oDigito += Integer.parseInt(String.valueOf(arrCpf[indexCpf])) * i ;		
	        	calculo2oDigito += Integer.parseInt(String.valueOf(arrCpf[indexCpf])) * (i + 1) ;
	        	
	        	indexCpf++;
			}
    	} catch (NumberFormatException e) {
    		return false;
    	}
        
		char digito1 = calculaDigito(calculo1oDigito);
		calculo2oDigito += Integer.parseInt(String.valueOf(digito1)) * 2;
		char digito2 = calculaDigito(calculo2oDigito);
		
		return (value.charAt(9) == digito1 && value.charAt(10) == digito2);

	}
	
	private char calculaDigito(Integer digitosCalculados) {
		digitosCalculados = 11 - (digitosCalculados % 11);
		return digitosCalculados > 9 ? '0' : Character.forDigit(digitosCalculados,10);
	}

}
