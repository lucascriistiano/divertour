package br.ufrn.divertour.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.PlaceRepository;
import br.ufrn.divertour.service.exception.ValidationException;

public class PlaceService {

	private PlaceRepository placeRepository;
	private static PlaceService placeService;
	
	private PlaceService() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		this.placeRepository = context.getBean(PlaceRepository.class);
	}
	
	public static PlaceService getInstance() {
		if(placeService == null) {
			placeService = new PlaceService();
		}
		
		return placeService;
	}
	
	private void validate(Place place) throws ValidationException {
		// TODO Finish validation
		Place foundPlace = placeRepository.findByName(place.getName());
		if(foundPlace != null) {
			if(place.getName().equals(foundPlace.getName()) && place.getCity() == foundPlace.getCity()) {
				throw new ValidationException("Um lugar com esse nome já se foi cadastrado nessa cidade");
			}
		}
	}
	
	public void register(Place place) throws ValidationException {
		validate(place);
		placeRepository.save(place);
	}

	public void remove(String id) {
		placeRepository.delete(id);
	}
	
	public List<Place> listAll() {
		return placeRepository.findAll();
	}
	
	public static List<String> getTypesOfPlace() {
		return Arrays.asList("Hotel", "Restaurante", "Bar", "Ponto Turístico", "Loja");
	}
	
	public static List<String> getCategoriesOfPlace() {
		return Arrays.asList("Religioso", "Aventura", "Ecológico", "Histórico");
	}

}
