package br.ufrn.divertour.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.PlaceRepository;
import br.ufrn.divertour.service.exception.ValidationException;

public class PlaceService {

	private PlaceRepository placeRepository;
	private static PlaceService placeService;
	
	private MongoOperations mongoOperation;
	
	private PlaceService() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		this.mongoOperation = (MongoOperations) context.getBean("mongoTemplate");
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
	
	public Place findById(String id) {
		return placeRepository.findById(id);
	}

	//TODO remover para Daos
	public List<Place> listAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoOperation.find(query, Place.class);
	}
	
	//TODO remover para Daos	
	public List<Place> findByNameSimilarity(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(name, "i"));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoOperation.find(query, Place.class);
	}

	//TODO remover para Daos
	public List<Place> findByCityAndState(String city, String state) {
		Query query = new Query();
		query.addCriteria(Criteria.where("city.name").is(city).and("city.state").is(state));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoOperation.find(query, Place.class);
	}

	//TODO remover para Daos
	public List<Place> findByType(String type) {
		Query query = new Query();
		query.addCriteria(Criteria.where("type").is(type));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoOperation.find(query, Place.class);
	}
	
	//TODO remover para Daos
	public List<Place> findByCategory(String category) {
		Query query = new Query();
		query.addCriteria(Criteria.where("categories").in(category));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoOperation.find(query, Place.class);
	}		
	
	public static List<String> getTypesOfPlace() {
		return Arrays.asList("Hotel", "Restaurante", "Bar", "Ponto Turístico", "Comércio", "Parque", "Outro");
	}
	
	public static List<String> getCategoriesOfPlace() {
		return Arrays.asList("Aventura", "Ecológico", "Histórico", "Religioso", "Esportivo");
	}

}