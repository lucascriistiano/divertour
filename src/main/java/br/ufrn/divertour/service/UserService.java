package br.ufrn.divertour.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.ufrn.divertour.model.User;
import br.ufrn.divertour.repository.UserRepository;
import br.ufrn.divertour.service.exception.ValidationException;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
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
	}
	
	public void remove(String id) {
		userRepository.delete(id);
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}

}