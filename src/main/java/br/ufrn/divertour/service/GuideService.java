package br.ufrn.divertour.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.GuideRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class GuideService {

	private static final int MIN_GUIDE_PLACES = 2;
	
	private GuideRepository guideRepository;
	private MongoTemplate mongoTemplate;
	
	@Autowired
	public GuideService(GuideRepository guideRepository, MongoTemplate mongoTemplate) {
		this.guideRepository = guideRepository;
		this.mongoTemplate = mongoTemplate;
	}

	private void validate(Guide guide) throws ValidationException {
		if(guide.getPlaces().isEmpty()) {
			throw new ValidationException("A lista de lugares do roteiro não pode ser vazia");
		}
		
		if(guide.getPlaces().size() < MIN_GUIDE_PLACES) {
			throw new ValidationException("O roteiro deve ter pelo menos dois lugares");
		}
	}

	public void register(Guide guide) throws ValidationException {
		validate(guide);
		
		for (Place place : guide.getPlaces()) {		
			place.setComments(null); // Erase comments before save guide
		}
		guide.setCreationDate(new Date());
		guideRepository.save(guide);
	}

	public void remove(String id) {
		guideRepository.delete(id);
	}
	
	public Guide findById(String id) {
		return guideRepository.findById(id);
	}

	//TODO move to dao
	public List<Guide> listAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	//TODO move to dao
	public List<Guide> findByNameSimilarity(String name) {
		Query query = new Query();
		query.addCriteria(Criteria.where("name").regex(name, "i"));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	//TODO move to dao
	public List<Guide> findByNumberOfPlaces(int numberOfPlaces) {
		Query query = new Query();
		query.addCriteria(Criteria.where("places").size(numberOfPlaces));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	//TODO move to dao
	public List<Guide> findByPeriod(int period) {
		Query query = new Query();
		query.addCriteria(Criteria.where("period").is(period));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}

	//TODO move to dao
	public List<Guide> findByCategory(String category) {
		Query query = new Query();
		query.addCriteria(Criteria.where("category").is(category));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}
	
	//TODO move to dao
	public List<Guide> findByCityAndState(String cityName, String cityState) {
		//TODO Check
		Query query = new Query();
		query.addCriteria(Criteria.where("places.city.name").in(cityName).and("places.city.state").in(cityState));
		query.with(new Sort(Sort.Direction.DESC, "rating"));
		return mongoTemplate.find(query, Guide.class);
	}
	
	//TODO move to dao
	public List<Integer> getPeriodsOfGuide() {
		@SuppressWarnings("unchecked")
		List<Integer> periods = mongoTemplate.getCollection("guide").distinct("period");
		return periods;
	}

	//TODO move to dao
	public List<Integer> getNumberOfPlaces() { 
		List<Guide> guides = guideRepository.findAll();
		Set<Integer> numPlacesSet = new HashSet<>();
		for (Guide guide : guides) {
			numPlacesSet.add(guide.getPlaces().size());
		}
		
		ArrayList<Integer> numPlaces = new ArrayList<>(numPlacesSet);
		Collections.sort(numPlaces);
		return numPlaces;
	}

	public static List<String> getCategoriesOfGuide() {
		return Arrays.asList("Para relaxar", "Curta", "Adrenalina", "Cultural", "Histórico", "Gastronômico", "Natural", "Outro");
	}
	
}