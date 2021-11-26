package br.univesp.diarioclasse.repositorios;

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
	
}
