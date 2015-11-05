package br.ufrn.divertour.service;

import java.util.Arrays;
import java.util.List;

import br.ufrn.divertour.model.Place;

public class PlaceService {

	public static List<String> getTypesOfPlace() {
		return Arrays.asList("Hotel", "Restaurante", "Bar", "Ponto Turístico", "Loja");
	}
	
	public static List<String> getCategoriesOfPlace() {
		return Arrays.asList("Religioso", "Aventura", "Ecológico");
	}
	
	public void register(Place place) {
		// TODO Implement validation
		System.out.println(place.getName());
		System.out.println(place.getDescription());
		System.out.println(place.getLat());
		System.out.println(place.getLon());
		System.out.println(place.getType());
		System.out.println(place.getCategories());
		System.out.println(place.getWebsite());
		System.out.println(place.getContacts());
		
		
	}

}
