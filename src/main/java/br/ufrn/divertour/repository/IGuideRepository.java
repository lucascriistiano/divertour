package br.ufrn.divertour.repository;

import java.util.List;

import br.ufrn.divertour.model.Guide;
import br.ufrn.divertour.model.User;

public interface IGuideRepository {

	public void save(Guide guide);
	public void delete(String id);
	public Guide findById(String id);
	public List<Guide> listAll();
	public List<Guide> findByNameSimilarity(String name);
	public List<Guide> findByNumberOfPlaces(int numberOfPlaces);
	public List<Guide> findByPeriod(int period);
	public List<Guide> findByCategory(String category);
	public List<Guide> findByCityAndState(String cityName, String cityState);
	public List<Guide> findByUser(User loggedUser);
	public List<Integer> getPeriodsOfGuide();
	public List<Integer> getNumberOfPlaces();

}
