package br.univesp.diarioclasse.helpers;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.digest.DigestUtils;

import br.univesp.diarioclasse.entidades.Login;

public class GeradorToken {

	/**
	 * Gera um token com dados do login
	 * @param login
	 */
	public String gerarTokenAcesso(Login login) {
		SecureRandom random;
		try {
			random = SecureRandom.getInstance("SHA1PRNG");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Não é possível gerar um token pois o algorítimo é inválido",e); //joga como erro de runtime
		}
		byte[] randomBytes = new byte[128]; //128 bytes aleatórios
		random.nextBytes(randomBytes);

		return DigestUtils.sha256Hex(login.getIdLogin() + (DigestUtils.sha256Hex(randomBytes))); //hasheia o id do login concatenado com um segundo hash de dados aleatórios
		//Como o código é isolado botamos futuramente trocar para algo mais robusto como um JWT
	}
}
