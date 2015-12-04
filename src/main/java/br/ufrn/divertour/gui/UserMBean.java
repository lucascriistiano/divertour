package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.CityService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.UserService;
import br.ufrn.divertour.service.exception.PhotoSavingException;
import br.ufrn.divertour.service.exception.ValidationException;

@ManagedBean(name = "userMBean")
@ViewScoped
public class UserMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private final UserService userService;
	private final CityService cityService;
	
	private UploadedFile selectedPhoto;
	
	private User user;
	
	public UserMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.userService = (UserService) context.getBean(UserService.class);
		this.cityService = (CityService) context.getBean(CityService.class);
		
		this.user = new User();
	}
	
	public String register() {
		try {
			this.userService.register(this.user);
			if(selectedPhoto != null) {
				this.userService.savePhoto(this.user, selectedPhoto);
			}
			
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			System.out.println("Aqui");
			return "/pages/common/login";
		} catch (ValidationException e) {
			System.out.println("Aqui2");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		} catch (PhotoSavingException e) {
			System.out.println("Aqui3");
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Aviso", e.getMessage()));
			return "/pages/common/login";
		} catch (Exception e) {
			System.out.println("Aqui4");
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
	
	public String changePermission(String id, boolean admin) {
		this.userService.changePermission(id, admin);
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
	
	public UploadedFile getSelectedPhoto() {
		return selectedPhoto;
	}

	public void setSelectedPhoto(UploadedFile selectedPhoto) {
		this.selectedPhoto = selectedPhoto;
	}
	
	public void cleanPhoto() {
		this.selectedPhoto = null;
	}

	public List<String> getTypesOfPlace() {
		return PlaceService.getTypesOfPlace();
	}
	
	public List<String> getCategoriesOfPlace() {
		return PlaceService.getCategoriesOfPlace();
	}
}
