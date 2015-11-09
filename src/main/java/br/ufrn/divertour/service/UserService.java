package br.ufrn.divertour.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import br.ufrn.divertour.config.MongoConfig;
import br.ufrn.divertour.model.User;
import br.ufrn.divertour.repository.UserRepository;

@Component
public class UserService {
	
	@Autowired
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
	
	private boolean validate(User user) {
		// TODO Implement validation	
		return true;
	}
	
	public void register(User user) {
		if(validate(user)) {
			userRepository.save(user);
			System.out.println("Persisted user");
		}
	}
	
	public void remove(String id) {
		userRepository.delete(id);
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}

}
