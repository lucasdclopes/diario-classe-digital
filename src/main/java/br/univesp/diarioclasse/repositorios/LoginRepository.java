package br.univesp.diarioclasse.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.univesp.diarioclasse.entidades.Login;
import br.univesp.diarioclasse.entidades.LoginUnico;

public interface LoginRepository extends JpaRepository<Login, Integer>, LoginUnico {
	
	@Override
	@Query(value = 
	"""  
		SELECT CASE WHEN COUNT(lg) > 0 THEN true ELSE false END from Login lg 
		WHERE lg.emailLogin = :emailLogin
	""")
	boolean existsBy(String emailLogin);
	
	//equivale a select * from dbo.login where email_login = :emailLogin
	Optional<Login> findByEmailLogin(String emailLogin);
	
	//equivale a select * from dbo.login where token_acesso = :tokenAcesso
	Optional<Login> findByTokenAcesso(String tokenAcesso);
	
}
