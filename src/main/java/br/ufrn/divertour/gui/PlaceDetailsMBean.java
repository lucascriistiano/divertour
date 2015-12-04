package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Comment;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.security.AuthenticationBean;
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
	private Comment comment;
	
	public PlaceDetailsMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
		this.userService = (UserService) context.getBean(UserService.class);
		
		this.comment = new Comment();
	}

	public void loadData() {
		//Load selected place info
		this.selectedPlace = placeService.findById(placeId);
		
		//Load comments and info about users that has commented
		this.users = new HashMap<>();
		List<Comment> comments = selectedPlace.getComments();
		if(comments != null) {
			for(Comment comment : comments) {
				String userId = comment.getUserId();
				if(userId != null) {
					User foundUser = this.userService.findById(userId);
					if(foundUser != null) {
						this.users.put(userId, foundUser);
					}
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
	
	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public void createComment() {
		User loggedUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AuthenticationBean.AUTH_KEY);
		if(loggedUser != null) {
			this.comment.setDate(new Date());
			this.comment.setUserId(loggedUser.getId());
			this.placeService.createComment(selectedPlace.getId(), comment);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro", "É necessário estar logado para registrar um comentário"));
			System.out.println("Usuário não logado tentando comentar");
		}
		
		this.comment = new Comment();
	}
	
}
