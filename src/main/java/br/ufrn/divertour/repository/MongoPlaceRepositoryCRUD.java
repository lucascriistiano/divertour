package br.ufrn.divertour.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufrn.divertour.model.Place;

public interface MongoPlaceRepositoryCRUD extends MongoRepository<Place, String> {

	public Place findById(String id);
	public Place findByName(String name);

}
