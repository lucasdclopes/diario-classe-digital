package br.univesp.diarioclasse.helpers;

import org.apache.commons.codec.digest.DigestUtils;

public class Cifrador {

	public String hashearSenha(String cifrar) {
		return DigestUtils.sha256Hex(cifrar);
	}
}
