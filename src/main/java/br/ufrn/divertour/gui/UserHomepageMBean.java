package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.security.AuthenticationBean;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;

@ViewScoped
@ManagedBean(name = "userHomepageMBean")
public class UserHomepageMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final GuideService guideService;
	private final PlaceService placeService;
	
	private List<Place> foundPlaces;
	private List<Guide> foundGuides;
	
	public UserHomepageMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		this.guideService = (GuideService) context.getBean(GuideService.class);
		this.placeService = (PlaceService) context.getBean(PlaceService.class);
	}

	public void loadData() {
		
		//get logged user
		User loggedUser = (User) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(AuthenticationBean.AUTH_KEY);
		if(loggedUser != null) {
			//Load places created by user
			this.foundPlaces = placeService.findByUser(loggedUser);
			
			//Load guides created by user
			this.foundGuides = guideService.findByUser(loggedUser);
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Erro", "É necessário estar logado para acessar a página de usuário"));
			System.out.println("Usuário não logado tentando ver homepage do user");
		}
	}

	public List<Place> getFoundPlaces() {
		return foundPlaces;
	}

	public List<Guide> getFoundGuides() {
		return foundGuides;
	}
	
}
