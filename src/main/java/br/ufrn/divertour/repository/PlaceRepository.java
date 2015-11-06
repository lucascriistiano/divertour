package br.ufrn.divertour.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufrn.divertour.model.Place;

public interface PlaceRepository extends MongoRepository<Place, String> {

}
