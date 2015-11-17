package br.ufrn.divertour.gui;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.ufrn.divertour.model.Place;

@ViewScoped
@ManagedBean(name = "placeDetailsMBean")
public class PlaceDetailsMBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	 @ManagedProperty(value="#{searchMBean.selectedItem}")
	 private Place selectedPlace;
		
	public PlaceDetailsMBean() {}

	public Place getSelectedPlace() {
		return selectedPlace;
	}

	public void setSelectedPlace(Place selectedPlace) {
		this.selectedPlace = selectedPlace;
	}
	
}
