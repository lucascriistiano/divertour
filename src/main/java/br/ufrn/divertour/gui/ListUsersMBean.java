package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.UserService;

@ManagedBean(name = "listUsersMBean")
@ViewScoped
public class ListUsersMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<User> users;
	private UserService userService;
	
	public ListUsersMBean() {
		this.userService = UserService.getInstance();
		this.users = userService.listAll();
	}

	public String editUser(String id) {
		System.out.println("Edit user with id = " + id);
		return "";
	}
	
	public String removeUser(String id) {
		this.userService.remove(id);
		return "";
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
}
