package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import br.univesp.diarioclasse.helpers.DateHelper;

public record AlunoParams(
		String nroMatricula, @DateTimeFormat(pattern = DateHelper.patternDataPtBr) LocalDate dtMatricula, String nis
		) {}
