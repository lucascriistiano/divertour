package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.Searchable;
import br.ufrn.divertour.service.GuideService;
import br.ufrn.divertour.service.PlaceService;

@SessionScoped
@ManagedBean(name = "searchPlaceMBean")

public class SearchPlaceMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private PlaceService placeService = PlaceService.getInstance();
	
	private String city;
	
	private String type;
	
	private String category;
	
	List<Place> foundResults;	
	
	public SearchPlaceMBean() {}
	

	public List<Place> getFoundResults() {
		return foundResults;
	}


	public void setFoundResults(List<Place> foundResults) {
		this.foundResults = foundResults;
	}


	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public List<Place> findResultsByCity(String city) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByCity(city));
		
		return foundResults;
	}
	
	public List<Place> findResultsByType(String type) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByType(type));
		
		return foundResults;
	}
	
	public List<Place> findResultsByCategory(String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByType(category));
		
		return foundResults;
	}
	
	
	public List<Place> findResultsByCityAndType(String city, String type) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByCityAndType(city, type));
		
		return foundResults;
	}
	
	public List<Place> findResultsByCityAndCategory(String city, String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByCityAndCategory(city, category));
		
		return foundResults;
	}
	
	public List<Place> findResultsByTypeAndCategory(String type, String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByTypeAndCategory(type, category));
		
		return foundResults;
	}
	
	public List<Place> findResults(String city, String type, String category) {
		foundResults = new ArrayList<>();
		foundResults.addAll(placeService.findByCityAndTypeAndCategory(city, type, category));
		
		return foundResults;
	}
	
	
//	public String showDetails() {
//		if(this.selectedItem == null) {
//			//TODO
//			System.out.println("Error. Selected null value");
//			return "";
//		}
//		
//		return this.selectedItem.getDetailsPage();
//	}
	
}
