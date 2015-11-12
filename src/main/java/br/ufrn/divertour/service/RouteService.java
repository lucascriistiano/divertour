package br.ufrn.divertour.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.model.Route;
import br.ufrn.divertour.repository.RouteRepository;
import br.ufrn.divertour.service.exception.ValidationException;

public class RouteService {

	private static final int MIN_ROUTE_PLACES = 2;
	
	private RouteRepository routeRepository;
	private static RouteService routeService;

	private RouteService() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
        this.routeRepository = context.getBean(RouteRepository.class);
	}

	public static RouteService getInstance() {
		if (routeService == null) {
			routeService = new RouteService();
		}

		return routeService;
	}

	private void validate(Route route) throws ValidationException {
		if(route.getPlaces().isEmpty()) {
			throw new ValidationException("A lista de lugares da rota não pode ser vazia");
		}
		
		if(route.getPlaces().size() < MIN_ROUTE_PLACES) {
			throw new ValidationException("A rota deve ter pelo menos dois lugares");
		}
	}

	public void register(Route route) throws ValidationException {
		validate(route);
		
		// Erase comments before save route				
		for (Place place : route.getPlaces()) {		
			place.setComments(null);
		}
		route.setCreationDate(new Date());
		
		routeRepository.save(route);
		System.out.println("Persisted route");
	}

	public void remove(String id) {
		routeRepository.delete(id);
	}

	public List<Route> listAll() {
		return routeRepository.findAll();
	}
	
	public static List<String> getCategoriesOfRoute() {
		return Arrays.asList("Para relaxar", "Curta", "Adrenalina");
	}

}
