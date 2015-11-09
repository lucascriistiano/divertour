package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.UserService;
import br.ufrn.divertour.util.CityUtil;

@ManagedBean(name = "userRegisterMBean")
@ViewScoped
public class UserRegisterMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	private UserService userService = UserService.getInstance();
	
	private List<String> cities;
	private List<String> categoriesOfPlace;
	
	public UserRegisterMBean() {
		this.user = new User();
		this.cities = CityUtil.listCities();
	}
	
	public String register() {
		//TODO Implement try/catch business exception
		this.userService.register(this.user);
		
		//TODO Change return
		return "";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getCities() {
		return cities;
	}
	
	public List<String> getCategoriesOfPlace() {
		return categoriesOfPlace;
	}
	
}
