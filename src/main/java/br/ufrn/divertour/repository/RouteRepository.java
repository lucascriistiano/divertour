package br.ufrn.divertour.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufrn.divertour.model.Route;

public interface RouteRepository extends MongoRepository<Route, String> {

}
