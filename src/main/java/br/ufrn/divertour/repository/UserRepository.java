package br.ufrn.divertour.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.model.User;

@Component
public class UserRepository implements IUserRepository {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private MongoUserRepositoryCRUD mongoUserRepositoryCRUD;

	@Override
	public void save(User user) {
		mongoUserRepositoryCRUD.save(user);
	}

	@Override
	public void delete(String id) {
		mongoUserRepositoryCRUD.delete(id);
	}
	
	@Override
	public User findById(String id) {
		return mongoUserRepositoryCRUD.findById(id);
	}

	@Override
	public User findByUsername(String username) {
		return mongoUserRepositoryCRUD.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return mongoUserRepositoryCRUD.findByEmail(email);
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		return mongoUserRepositoryCRUD.findByUsernameAndPassword(username, password);
	}

	@Override
	public List<User> findAll() {
		return mongoUserRepositoryCRUD.findAll();
	}
	
}
