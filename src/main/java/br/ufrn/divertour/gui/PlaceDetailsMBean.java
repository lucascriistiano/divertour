package br.ufrn.divertour.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.service.PlaceService;

@ViewScoped
@ManagedBean(name = "placeDetailsMBean")
public class PlaceDetailsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private PlaceService placeService = PlaceService.getInstance();
	
	private String placeId;
	private Place selectedPlace;

	public void loadData() {
		selectedPlace = placeService.findById(placeId);
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
