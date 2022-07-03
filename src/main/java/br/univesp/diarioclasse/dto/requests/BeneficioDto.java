package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public class BeneficioDto {
	
	@NotNull
	private Integer idAluno;
	@JsonFormat(pattern=DateHelper.patternDataPtBr) @NotNull
	private LocalDate dtRecebimento;
	@NotBlank @Length(max = 200)
	private String responsavelRecebimento;
	@NotBlank @Length(max = 500)
	private String descBeneficio;
	
	public BeneficioDto() {}

	public Integer getIdAluno() {
		return idAluno;
	}

	public LocalDate getDtRecebimento() {
		return dtRecebimento;
	}

	public String getResponsavelRecebimento() {
		return responsavelRecebimento;
	}

	public String getDescBeneficio() {
		return descBeneficio;
	}

}
