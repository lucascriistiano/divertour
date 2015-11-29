package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Comment;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.UserService;

@ViewScoped
@ManagedBean(name = "placeDetailsMBean")
public class PlaceDetailsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PlaceService placeService;
	private UserService userService;
	
	private Map<String, User> users;
	
	private String placeId;
	private Place selectedPlace;
	
	public PlaceDetailsMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
		this.userService = (UserService) context.getBean(UserService.class);
	}

	public void loadData() {
		this.selectedPlace = placeService.findById(placeId);
		
		this.users = new HashMap<>();
		for(Comment comment : selectedPlace.getComments()) {
			String userId = comment.getUserId();
			if(userId != null) {
				User foundUser = this.userService.findById(userId);
				if(foundUser != null) {
					this.users.put(userId, foundUser);
				}
			}
		}
	}
	
	public User getCommentUserById(String userId) {
		return this.users.get(userId);
	}
	
	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Place getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(Place selectedPlace) {
		this.selectedPlace = selectedPlace;
	}
	
}
