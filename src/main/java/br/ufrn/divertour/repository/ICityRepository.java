package br.ufrn.divertour.repository;

import java.util.List;

import br.ufrn.divertour.model.City;

public interface ICityRepository {

	public void save(City city);
	public void delete(String id);
	public List<City> listAll();
	public City findByNameAndState(String name, String state);
	
}
