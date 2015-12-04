package br.ufrn.divertour.repository;

import java.util.List;

import br.ufrn.divertour.model.User;

public interface IUserRepository {
	
	public void save(User user);
	public void delete(String id);
	public User findById(String id);
	public User findByUsername(String username);
	public User findByEmail(String email);
	public User findByUsernameAndPassword(String username, String password);
	public List<User> findAll();
	
}
