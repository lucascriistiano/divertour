package br.ufrn.divertour.repository;

import br.ufrn.divertour.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByLogin(String login);
	public User findByEmail(String email);
	
}
