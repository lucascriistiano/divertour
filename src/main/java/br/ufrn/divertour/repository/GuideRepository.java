package br.ufrn.divertour.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufrn.divertour.model.Guide;

public interface GuideRepository extends MongoRepository<Guide, String> {

}