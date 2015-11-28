package br.ufrn.divertour.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufrn.divertour.model.City;

public interface CityRepository extends MongoRepository<City, String> {

	public City findByNameAndState(String name, String state);

}
