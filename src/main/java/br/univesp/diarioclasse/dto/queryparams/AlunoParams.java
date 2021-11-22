package br.univesp.diarioclasse.dto.queryparams;

import java.time.LocalDate;

public record AlunoParams(String nroMatricula, LocalDate dtMatricula, String ra) {}
