package br.ufrn.divertour.gui;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.PlaceService;

@ManagedBean(name = "listPlacesMBean")
@ViewScoped
public class ListPlacesMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Place> places;
	private PlaceService placeService;
	
	public ListPlacesMBean() {
		this.placeService = PlaceService.getInstance();
		this.places = placeService.listAll();
	}

	public String editPlace(String id) {
		System.out.println("Edit place with id = " + id);
		return "";
	}
	
	public String removePlace(String id) {
		System.out.println("Remove place with id = " + id);
		return "";
	}
	
	public List<Place> getPlaces() {
		return places;
	}

	public void setPlaces(List<Place> places) {
		this.places = places;
	}
	
}
