package br.ufrn.divertour.service;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.repository.UserRepository;
import br.ufrn.divertour.service.exception.ValidationException;

public class UserService {
	
	private UserRepository userRepository;
	private static UserService userService;
	
	private UserService() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
        this.userRepository = context.getBean(UserRepository.class);
	}
	
	public static UserService getInstance() {
		if(userService == null) {
			userService = new UserService();
		}
		
		return userService;
	}
	
	private void validate(User user) throws ValidationException {
		// TODO Finish validation	
		if(userRepository.findByEmail(user.getEmail()) != null) {
			throw new ValidationException("O e-mail já se encontra cadastrado");
		}
		
		if(userRepository.findByUsername(user.getUsername()) != null) {
			throw new ValidationException("O nome de usuário já se encontra em uso");
		}
		
	}
	
	public void register(User user) throws ValidationException {
		validate(user);
		userRepository.save(user);
		System.out.println("Persisted user");
	}
	
	public void remove(String id) {
		userRepository.delete(id);
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}

}