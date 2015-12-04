package br.ufrn.divertour.repository;

import java.util.List;

import br.ufrn.divertour.model.LatLng;
import br.ufrn.divertour.model.Place;

public interface IPlaceRepository {

	public void save(Place place);
	public void delete(String id);
	public Place findById(String id);
	public Place findByName(String name);
	public List<Place> listAll();
	public List<Place> findByNameSimilarity(String name);
	public List<Place> findByCityAndState(String city, String state);
	public List<Place> findByType(String type);
	public List<Place> findByCategory(String category);
	public List<Place> findOnArea(LatLng upperBound, LatLng lowerBound);

}
