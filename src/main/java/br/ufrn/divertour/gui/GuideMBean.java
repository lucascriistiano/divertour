package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.Searchable;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;
import br.ufrn.divertour.service.exception.ValidationException;

@ManagedBean(name = "guideMBean")
@ViewScoped
public class GuideMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private final PlaceService placeService;
	private final GuideService guideService;

	private Guide guide;
	private List<Place> selectedPlaces;
	
	// For search
	private Searchable selectedItem;
	
	// For markers add and remove
	private String selectedPlaceJSON;
	
	public GuideMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BeanFactory factory = context;
		this.placeService = (PlaceService) factory.getBean(PlaceService.class);
		this.guideService = (GuideService) factory.getBean(GuideService.class);
		
		this.guide = new Guide();
		this.selectedPlaces = new ArrayList<>();
	}
	
    public void addPlace(SelectEvent event) {
    	Place selectedPlace = (Place) event.getObject();
    	
    	if(selectedPlace != null) {
    		this.selectedPlaces.add(selectedPlace);
    		System.out.println("Added place: " + selectedPlace.getName());
    		
    		this.selectedPlaceJSON = new Gson().toJson(selectedPlace).toString();
    	}
    }

	public String register() {
		try {
			guide.setPlaces(selectedPlaces);
			this.guideService.register(guide);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", "Cadastro realizado com sucesso"));
			return "/pages/restricted/homepage";
		} catch (ValidationException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return "";
	}
	
	public String edit(String id) {
		System.out.println("Edit guide with id = " + id);
		return "";
	}
	
	public String remove(String id) {
		this.guideService.remove(id);
		return "";
	}
	
	public List<Place> getPlaces() {
		return placeService.listAll();
	}
	
	public List<Place> getSelectedPlaces() {
		return selectedPlaces;
	}

	public Guide getGuide() {
		return guide;
	}

	public void setGuide(Guide guide) {
		this.guide = guide;
	}
    
	public List<Guide> getGuides() {
		return guideService.listAll();
	}
	
	public List<String> getCategoriesOfGuide() {
		return GuideService.getCategoriesOfGuide();
	}
	
	// For search in route creation
	public Searchable getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Searchable selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public List<Searchable> findResults(String search) {
		List<Searchable> foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByNameSimilarity(search));
		
		return foundResults;
	}
	
	public String getSelectedPlaceJSON() {
		return selectedPlaceJSON;
	}

	public void setSelectedPlaceJSON(String selectedPlaceJSON) {
		this.selectedPlaceJSON = selectedPlaceJSON;
	}
	
}
