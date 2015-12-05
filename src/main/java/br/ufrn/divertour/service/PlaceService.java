package br.ufrn.divertour.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.Comment;
import br.ufrn.divertour.model.LatLng;
import br.ufrn.divertour.model.Place;
import br.ufrn.divertour.repository.IPlaceRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class PlaceService {
	
	@Autowired
	private IPlaceRepository placeRepository;
	
	private void validate(Place place) throws ValidationException {
		Place foundPlace = placeRepository.findByName(place.getName());
		if(foundPlace != null) {
			if(place.equals(foundPlace)) { //Check if name and city of place are equal
				throw new ValidationException("Um lugar com o nome inserido já foi cadastrado nessa cidade");
			}
		}
	}
	
	public void register(Place place) throws ValidationException {
		validate(place);
		
		place.setComments(new ArrayList<>());  //Initialize comments with a empty list
		placeRepository.save(place);
	}

	public void remove(String id) {
		placeRepository.delete(id);
	}
	
	public Place findById(String id) {
		return placeRepository.findById(id);
	}

	public List<Place> listAll() {
		return placeRepository.listAll();
	}
	
	public List<Place> findByNameSimilarity(String name) {
		return placeRepository.findByNameSimilarity(name);
	}

	public List<Place> findByCityAndState(String city, String state) {
		return placeRepository.findByCityAndState(city, state);
	}

	public List<Place> findByType(String type) {
		return placeRepository.findByType(type);
	}
	
	public List<Place> findByCategory(String category) {
		return placeRepository.findByCategory(category);
	}		
	
	public List<Place> findOnArea(LatLng upperBound, LatLng lowerBound) {
		return placeRepository.findOnArea(upperBound, lowerBound);
	}
	
	public static List<String> getTypesOfPlace() {
		return Arrays.asList("Hotel", "Restaurante", "Bar", "Ponto Turístico", "Comércio", "Parque", "Outro");
	}
	
	public static List<String> getCategoriesOfPlace() {
		return Arrays.asList("Aventura", "Ecológico", "Histórico", "Religioso", "Esportivo", "Outra");
	}

	private int calculateRating(List<Comment> comments) {
		int totalSum = 0;
		for (Comment comment : comments) {
			totalSum += comment.getRating();
		}
		
		return comments.isEmpty() ? 0 : Math.round(totalSum / comments.size());
	}
	
	public void createComment(String id, Comment comment) {
		Place foundPlace = placeRepository.findById(id);
		List<Comment> comments = foundPlace.getComments();
		comments.add(0, comment);
		foundPlace.setComments(comments);
		
		int rating = calculateRating(comments);
		foundPlace.setRating(rating);
		
		placeRepository.save(foundPlace);
	}

}