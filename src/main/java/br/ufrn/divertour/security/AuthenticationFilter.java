package br.ufrn.divertour.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.ufrn.divertour.model.User;

public class AuthenticationFilter implements Filter {

	private FilterConfig config;
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) req;
		HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
		
		String reqURL = httpServletRequest.getRequestURL().toString();
		HttpSession session = httpServletRequest.getSession();

		User loggedUser = (User) session.getAttribute(AuthenticationBean.AUTH_KEY);
		if(loggedUser == null) {
			if(reqURL.contains("/pages/admin") || reqURL.contains("/pages/restricted")) {
				httpServletResponse.sendRedirect("/divertour/login");
			} else {
				chain.doFilter(req, resp);
			}
		} else {
			if(!loggedUser.isAdmin() && reqURL.contains("/pages/admin")) {
				httpServletResponse.sendRedirect("/divertour/minhapagina"); 
			} if(loggedUser.isAdmin() && !reqURL.contains("/pages/admin")) {
				httpServletResponse.sendRedirect("/divertour/admin");
			} else {
				chain.doFilter(req, resp);
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
	
	@Override
	public void destroy() {
		config = null;
	}

}
