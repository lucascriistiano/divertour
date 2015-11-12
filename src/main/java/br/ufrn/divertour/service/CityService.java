package br.ufrn.divertour.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.City;
import br.ufrn.divertour.repository.CityRepository;
import br.ufrn.divertour.service.exception.ValidationException;

public class CityService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CityRepository cityRepository;
	private static CityService cityService;
	
	private CityService() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
        this.cityRepository = context.getBean(CityRepository.class);
	}
	
	public static CityService getInstance() {
		if(cityService == null) {
			cityService = new CityService();
		}
		
		return cityService;
	}

	private void validate(City city) throws ValidationException {
		// TODO Implement validation
		
	}
	
	public void register(City city) throws ValidationException {
		validate(city);
		cityRepository.save(city);
		System.out.println("Persisted city");
	}
	
	public void remove(String id) {
		cityRepository.delete(id);
	}
	
	public List<City> listAll() {
		return cityRepository.findAll();
	}

	public static List<String> getStates() {
		return Arrays.asList("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
	}
	
}
