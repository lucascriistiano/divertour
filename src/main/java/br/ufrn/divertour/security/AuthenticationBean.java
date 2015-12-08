package br.ufrn.divertour.security;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.UserService;

@ManagedBean(name="authenticationBean", eager=true)
@SessionScoped
public class AuthenticationBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String AUTH_KEY = "app.user";
	
	private UserService userService;

	private String username;
	private String password;
	private User loggedUser;
	
	public AuthenticationBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.userService = (UserService) context.getBean(UserService.class);
	}
	
	public User getLoggedUser() {
		return loggedUser;
	}
	
	public void setLoggedUser(User loggedUser) {
		this.loggedUser = loggedUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLoggedIn() {
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AUTH_KEY);
		return user != null;
	}

	public boolean isLoggedInAdmin() {
		User user = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AUTH_KEY);
		return (user == null) ? false : user.isAdmin();
	}
	
	public String login() {
		User foundUser = this.userService.checkLogin(this.username, this.password);
		
		this.username = "";
		this.password = "";
		
		FacesContext context = FacesContext.getCurrentInstance();
		if(foundUser != null) {
			this.loggedUser = foundUser;
			context.getExternalContext().getSessionMap().put(AUTH_KEY, foundUser);
			return foundUser.isAdmin() ? "pretty:homepage_admin" : "pretty:homepage_user";
		} else {
			this.loggedUser = null;
			context.getExternalContext().getSessionMap().remove(AUTH_KEY);
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Login inválido. Verifique o nome de usuário e senha inseridos."));
			return "";
		}
	}
	
	public String logout() {
		this.loggedUser = null;
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(AUTH_KEY);
		return "pretty:index";
	}
	
}
