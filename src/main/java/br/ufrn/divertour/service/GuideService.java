package br.ufrn.divertour.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.IGuideRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class GuideService {

	private static final int MIN_GUIDE_PLACES = 2;
	
	@Autowired
	private IGuideRepository guideRepository;
	
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
			place.setComments(new ArrayList<>()); // Erase comments before save guide
		}
		
		guide.setComments(new ArrayList<>());  //Initialize comments with a empty list
		guide.setCreationDate(new Date());
		guideRepository.save(guide);
	}

	public void remove(String id) {
		guideRepository.delete(id);
	}
	
	public Guide findById(String id) {
		return guideRepository.findById(id);
	}

	public List<Guide> listAll() {
		return guideRepository.listAll();
	}

	public List<Guide> findByNameSimilarity(String name) {
		return guideRepository.findByNameSimilarity(name);
	}

	public List<Guide> findByNumberOfPlaces(int numberOfPlaces) {
		return guideRepository.findByNumberOfPlaces(numberOfPlaces);
	}

	public List<Guide> findByPeriod(int period) {
		return guideRepository.findByPeriod(period);
	}

	public List<Guide> findByCategory(String category) {
		return guideRepository.findByCategory(category);
	}
	
	public List<Guide> findByCityAndState(String cityName, String cityState) {
		return guideRepository.findByCityAndState(cityName, cityState);
	}
	
	public List<Integer> getPeriodsOfGuide() {
		return guideRepository.getPeriodsOfGuide();
	}

	public List<Integer> getNumberOfPlaces() { 
		return guideRepository.getNumberOfPlaces();
	}

	public static List<String> getCategoriesOfGuide() {
		return Arrays.asList("Para relaxar", "Curta", "Adrenalina", "Cultural", "Histórico", "Gastronômico", "Natural", "Outro");
	}
	
}