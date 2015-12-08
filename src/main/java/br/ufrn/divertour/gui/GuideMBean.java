package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.gson.Gson;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.LatLng;
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
	private Place selectedItem;
	
	// For markers add and remove
	private String searchedPlaceJSON;
	private String clickedPlaceJSON;
	
	// For places loading
	private String upperBoundJSON;
	private String lowerBoundJSON;
//	private String placesOnAreaJSON;
	
	
	public GuideMBean() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		BeanFactory factory = context;
		this.placeService = (PlaceService) factory.getBean(PlaceService.class);
		this.guideService = (GuideService) factory.getBean(GuideService.class);
		
		this.guide = new Guide();
		this.selectedPlaces = new ArrayList<>();
	}
	
    public void addSearchedPlace(SelectEvent event) {
    	Place searchedPlace = (Place) event.getObject();
    	
    	if(searchedPlace != null) {
    		System.out.println("Adicionado pela busca");
    		this.selectedPlaces.add(searchedPlace);
    		this.searchedPlaceJSON = new Gson().toJson(searchedPlace).toString();
    		this.selectedItem = null;
    	} else {
    		//TODO add message
    	}
    	
    	for (Place place : selectedPlaces) {
			System.out.println(place.getId() + " = " + place.getName());
		}
    }
    
    public void addClickedPlace() {
    	Place clickedPlace = new Gson().fromJson(this.clickedPlaceJSON, Place.class);
    	
    	if(clickedPlace != null) {
    		System.out.println("Adicionado pelo clique");
    		this.selectedPlaces.add(clickedPlace);
    	} else {
    		//TODO add message
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

	public void setSelectedPlaces(List<Place> selectedPlaces) {
		this.selectedPlaces = selectedPlaces;
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

	public void setSelectedItem(Place selectedItem) {
		this.selectedItem = selectedItem;
	}
	
	public List<Place> findResults(String search) {
		return placeService.findByNameSimilarity(search);
	}
	
	public String getSearchedPlaceJSON() {
		return searchedPlaceJSON;
	}

	public void setSearchedPlaceJSON(String searchedPlaceJSON) {
		this.searchedPlaceJSON = searchedPlaceJSON;
	}

	public String getClickedPlaceJSON() {
		return clickedPlaceJSON;
	}

	public void setClickedPlaceJSON(String clickedPlaceJSON) {
		this.clickedPlaceJSON = clickedPlaceJSON;
	}
	
	public String getUpperBoundJSON() {
		return upperBoundJSON;
	}

	public void setUpperBoundJSON(String upperBoundJSON) {
		this.upperBoundJSON = upperBoundJSON;
	}

	public String getLowerBoundJSON() {
		return lowerBoundJSON;
	}

	public void setLowerBoundJSON(String lowerBoundJSON) {
		this.lowerBoundJSON = lowerBoundJSON;
	}

//	public String getPlacesOnAreaJSON() {
//		return placesOnAreaJSON;
//	}
//
//	public void setPlacesOnAreaJSON(String placesOnAreaJSON) {
//		this.placesOnAreaJSON = placesOnAreaJSON;
//	}

//	private String listPlacesToJSON(List<Place> places) {
//		List<Place> list = new ArrayList<>(places);
//		return new Gson().toJson(list);
//	}
	
	public void loadPlacesOnArea() {
		Gson gson = new Gson();
		LatLng upperBound = gson.fromJson(this.upperBoundJSON, LatLng.class);
		LatLng lowerBound = gson.fromJson(this.lowerBoundJSON, LatLng.class);
		
		List<Place> foundPlaces = this.placeService.findOnArea(upperBound, lowerBound);
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.addCallbackParam("places", gson.toJson(foundPlaces));
	}
	
//	public void onSelect(SelectEvent event) {
//		System.out.println("Selecionado");
//    }
//     
//    public void onUnselect(UnselectEvent event) {
//    	System.out.println("Deselecionado");
//    }
	
	public void updateGuideOrder() {
		for (Place place : selectedPlaces) {
			System.out.println(place.getId() + " = " + place.getName());
		}
		
		RequestContext requestContext = RequestContext.getCurrentInstance();
		requestContext.addCallbackParam("places", new Gson().toJson(new ArrayList<>(this.selectedPlaces)));
	}
	
}
