package br.ufrn.divertour.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.ufrn.divertour.model.Guide;

public interface MongoGuideRepositoryCRUD extends MongoRepository<Guide, String> {

	Guide findById(String id);
	Guide findByNameAndPeriod(String name, Integer period);

}
