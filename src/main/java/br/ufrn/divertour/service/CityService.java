package br.ufrn.divertour.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.repository.CityRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class CityService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private CityRepository cityRepository;
	
	@Autowired
	public CityService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
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
	
	public List<City> listAll() {
		return cityRepository.findAll();
	}

	public static List<String> getStates() {
		return Arrays.asList("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
	}
	
}
