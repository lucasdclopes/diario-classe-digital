package br.univesp.diarioclasse.seguranca;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.univesp.diarioclasse.dto.responses.ErroSimplesDto;
import br.univesp.diarioclasse.entidades.Login;
import br.univesp.diarioclasse.exceptions.AutenticacaoException;
import br.univesp.diarioclasse.repositorios.LoginRepository;
import br.univesp.diarioclasse.restcontrollers.HandlerErros;

@Component
public class FiltroSeguranca extends OncePerRequestFilter {

	
	private static final Logger LOGGER = LoggerFactory.getLogger(FiltroSeguranca.class);
	
	@Autowired private LoginRepository loginDao;
	@Autowired private UsuarioLogado logado;
	
	@Autowired private HandlerErros handlerPadrao; //não cai automático no handler padrão quando estamos em um filtro
	
	//roda antes de todas as requisições (exceto na /Login) para verificar se o token é válido
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    		throws ServletException, IOException {
    	
    	//recupera o token dos cabeçalhos e procura por ele no banco. Carrega os dados do usuário para esta requisição
    	String token = request.getHeader("token");
    	
    	try {
	    	if (token == null || token.isBlank())
	    		throw new AutenticacaoException("header com o token não foi encontrado");
	    	
	    	Login login = loginDao.findByTokenAcesso(token.strip()).orElseThrow(() -> new AutenticacaoException("O acesso é inválido. Realize login novamente"));
	    	//verifica se faz mais de meia hora que o user fez algo. Se sim, invalida o acesso
	    	if (LocalDateTime.now().isAfter(login.getDtUltimoAcesso().plus(30, ChronoUnit.MINUTES)))
	    		throw new AutenticacaoException("Seu acesso expirou devido a um longo tempo de inatividade");
	    	else
	    		login.atualizarUltimoAcesso();
	    	
	    	logado.idCadastro = login.getCadastro().getIdCadastro();
	    	logado.tipoCadastro = login.getCadastro().getTipoCadastro();
	    	
    	} catch (AutenticacaoException e) {
    		//monta o response http "na mão"
    		response.setStatus(HttpStatus.UNAUTHORIZED.value());	
    		response.setContentType("application/json");
    		response.setCharacterEncoding("UTF8");
    		ErroSimplesDto erro = handlerPadrao.handle(e);
    		//Transforma o objeto em Json
            String jsonResponse = new ObjectMapper().writeValueAsString(erro);		
    		response.getWriter().write(jsonResponse);
    		return;
    	}
  
    	
        filterChain.doFilter(request, response);
    }
    
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) //não roda o filtro para o /Login. 
    		throws ServletException {
        String path = request.getRequestURI();
        return "/logar".equals(path);
    }
}
