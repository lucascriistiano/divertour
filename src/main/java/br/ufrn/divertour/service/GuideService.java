package br.ufrn.divertour.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.repository.GuideRepository;
import br.ufrn.divertour.service.exception.ValidationException;

public class GuideService {

	private static final int MIN_GUIDE_PLACES = 2;
	
	private GuideRepository guideRepository;
	private static GuideService guideService;

	private GuideService() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
        this.guideRepository = context.getBean(GuideRepository.class);
	}

	public static GuideService getInstance() {
		if (guideService == null) {
			guideService = new GuideService();
		}

		return guideService;
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
		
		// Erase comments before save guide				
		for (Place place : guide.getPlaces()) {		
			place.setComments(null);
		}
		guide.setCreationDate(new Date());
		
		guideRepository.save(guide);
	}

	public void remove(String id) {
		guideRepository.delete(id);
	}

	public List<Guide> listAll() {
		return guideRepository.findAll();
	}
	
	public static List<String> getCategoriesOfGuide() {
		return Arrays.asList("Para relaxar", "Curta", "Adrenalina");
	}

}
