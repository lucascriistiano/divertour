package br.ufrn.divertour.gui;

import java.util.List;

import javax.faces.bean.ManagedBean;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.PlaceService;

@ManagedBean(name = "placeRegisterMBean")
public class PlaceRegisterMBean {

	private Place place;
	private PlaceService placeService = new PlaceService();

	private List<String> typesOfPlace;
	private List<String> categoriesOfPlace;
	
	public PlaceRegisterMBean() {
		this.place = new Place();
		this.typesOfPlace = PlaceService.getTypesOfPlace();
		this.categoriesOfPlace = PlaceService.getCategoriesOfPlace();
	}
	
	public String register() {
		//TODO Implement try/catch business exception
		this.placeService.register(this.place);
		
		//TODO Change return
		return "";
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public List<String> getTypesOfPlace() {
		return typesOfPlace;
	}

	public List<String> getCategoriesOfPlace() {
		return categoriesOfPlace;
	}
	
}
