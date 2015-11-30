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
		
//		String profileImage = user.getProfileImage();
//		if(profileImage != null) {
//			String profilePhotoFormat = profileImage.split(".")[1];
//			
//			File file = new File("profileImage.");
//			
//			
//		}
	}
	
	public void update(User user) throws ValidationException {
		validate(user);
		userRepository.save(user);
	}
	
	public void remove(String id) {
		userRepository.delete(id);
	}
	
	public void changePermission(String id, boolean admin) {
		User user = userRepository.findById(id);
		user.setAdmin(admin);
		userRepository.save(user);
	}

	public User findById(String id) {
		return userRepository.findById(id);
	}
	
	public List<User> listAll() {
		return userRepository.findAll();
	}

	public User checkLogin(String username, String password) {
		return userRepository.findByUsernameAndPassword(username, password);
	}

}