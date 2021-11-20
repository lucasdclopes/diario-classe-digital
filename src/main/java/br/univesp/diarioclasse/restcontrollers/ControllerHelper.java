package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.web.util.UriComponentsBuilder;

public class ControllerHelper {

	public static URI montarUriLocalResource(UriComponentsBuilder uriBuilder, String path, Object id) {
		return uriBuilder.path(path).buildAndExpand(id).toUri();
	}
	
	public static HttpHeaders adicionarHeaderPaginacao(Integer totalPaginas, Boolean temProximaPagina) {
		HttpHeaders headers = new HttpHeaders();	
		headers.set("page-quantidade", totalPaginas.toString());
		headers.set("page-has-proxima", temProximaPagina.toString());
		return headers;
	}
}
