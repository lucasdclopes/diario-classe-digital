package br.univesp.diarioclasse.dto.requests;

import java.time.LocalDate;

public record AlunoParamFiltro(String nroMatricula, LocalDate dtMatricula, String ra) {}
