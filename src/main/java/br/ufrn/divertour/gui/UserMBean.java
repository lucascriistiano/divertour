package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.UserService;
import br.ufrn.divertour.service.exception.ValidationException;

@ManagedBean(name = "userMBean")
@ViewScoped
public class UserMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private UserService userService = UserService.getInstance();
	private CityService cityService = CityService.getInstance();
	
	private User user;
	
	public UserMBean() {
		this.user = new User();
	}
	
	public String register() {
		try {
			this.userService.register(this.user);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			return "/pages/common/login";
		} catch (ValidationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "";
	}

	public String edit(String id) {
		System.out.println("Edit user with id = " + id);
		return "";
	}
	
	public String remove(String id) {
		this.userService.remove(id);
		return "";
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<City> getCities() {
		return cityService.listAll();
	}
		
	public List<User> getUsers() {
		return userService.listAll();
	}
	
	public List<String> getTypesOfPlace() {
		return PlaceService.getTypesOfPlace();
	}
	
	public List<String> getCategoriesOfPlace() {
		return PlaceService.getCategoriesOfPlace();
	}
}
