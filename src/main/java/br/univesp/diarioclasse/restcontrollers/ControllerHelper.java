package br.univesp.diarioclasse.restcontrollers;

import java.net.URI;

import org.springframework.web.util.UriComponentsBuilder;

public class ControllerHelper {

	public static URI montarUriLocalResource(UriComponentsBuilder uriBuilder, String path, Object id) {
		return uriBuilder.path(path).buildAndExpand(id).toUri();
	}
}
