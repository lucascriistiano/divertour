package br.ufrn.divertour.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.PlaceRepository;

@Component
public class PlaceService {

	@Autowired
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
	
	private boolean validate(Place place) {
		// TODO Implement validation	
		return true;
	}
	
	public void register(Place place) {
		if(validate(place)) {
			placeRepository.save(place);
			System.out.println("Persisted place");
		}
	}
	
	public static List<String> getTypesOfPlace() {
		return Arrays.asList("Hotel", "Restaurante", "Bar", "Ponto Turístico", "Loja");
	}
	
	public static List<String> getCategoriesOfPlace() {
		return Arrays.asList("Religioso", "Aventura", "Ecológico");
	}

}
