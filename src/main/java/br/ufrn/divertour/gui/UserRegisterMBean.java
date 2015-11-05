package br.ufrn.divertour.gui;

import java.util.List;

import javax.faces.bean.ManagedBean;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.UserService;
import br.ufrn.divertour.util.CityUtil;

@ManagedBean(name = "userRegisterMBean")
public class UserRegisterMBean {

	private List<String> cities;
	private List<String> categoriesOfPlace;
	
	private User user;
	private UserService userService = new UserService();
	
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
