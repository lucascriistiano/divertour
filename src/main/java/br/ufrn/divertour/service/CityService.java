package br.ufrn.divertour.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.CityRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class CityService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CityRepository cityRepository;
	private MongoTemplate mongoTemplate;
	
	@Autowired
	public CityService(CityRepository cityRepository, MongoTemplate mongoTemplate) {
		this.cityRepository = cityRepository;
		this.mongoTemplate = mongoTemplate;
	}

	private void validate(City city) throws ValidationException {
		// TODO Finish validation	
		if(cityRepository.findByNameAndState(city.getName(), city.getState()) != null) {
			throw new ValidationException("A cidade já se encontra cadastrada");
		}
	}
	
	public void register(City city) throws ValidationException {
		validate(city);
		cityRepository.save(city);
	}
	
	public void remove(String id) {
		cityRepository.delete(id);
	}
	
	//TODO move to DAOs
	public List<City> listAll() {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.ASC, "name"));
		return mongoTemplate.find(query, City.class);
	}

	public static List<String> getStates() {
		return Arrays.asList("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
	}
	
}
