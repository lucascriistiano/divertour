package br.ufrn.divertour.repository;

import br.ufrn.divertour.model.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByUsername(String username);
	public User findByEmail(String email);
	public User findById(String id);
	public User findByUsernameAndPassword(String username, String password);
	
}