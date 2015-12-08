package br.ufrn.divertour.service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.City;
import br.ufrn.divertour.repository.ICityRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class CityService implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Autowired
	private ICityRepository cityRepository;
	
	private void validate(City city) throws ValidationException {
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
	
	public City findByNameAndState(String name, String state) {
		return cityRepository.findByNameAndState(name, state);
	}
	
	public List<City> listAll() {
		return cityRepository.listAll();
	}

	public static List<String> getStates() {
		return Arrays.asList("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
	}
	
}
